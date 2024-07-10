package com.example.deliveryapp.delivery.presentation.home.fragments.profileDelivery

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.deliveryapp.R
import com.example.deliveryapp.auth.presentation.login.LoginActivity
import com.example.deliveryapp.core.user.data.mapper.toUserSerializable
import com.example.deliveryapp.core.user.domain.model.User
import com.example.deliveryapp.databinding.FragmentProfileDeliveryBinding
import com.example.deliveryapp.selectRol.SelectRolActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@AndroidEntryPoint
class ProfileDeliveryFragment : Fragment() {

    private var _binding: FragmentProfileDeliveryBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<ProfileDeliveryViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        initObservers()
        initListernes(onAction = {action ->
            when(action){
                ProfileDeliveryAction.OnEditProfileClick -> goToEditProfile()
                ProfileDeliveryAction.OnLogoutClick -> goToLogin()
                ProfileDeliveryAction.OnSelectedRolClick -> goToSelectRol()
            }
        })
    }


    private fun goToSelectRol() {
        startActivity(SelectRolActivity.create(requireContext()).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        })
    }

    private fun goToLogin(){
        viewModel.logout()
        startActivity(LoginActivity.create(requireContext()).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        })
    }

    private fun goToEditProfile(){
        findNavController().navigate(
            ProfileDeliveryFragmentDirections.actionProfileDeliveryFragmentToUpdateProfileActivity(
                Json.encodeToString(viewModel.state.value.user.toUserSerializable())
            )
        )
    }



    private fun initListernes(onAction: (ProfileDeliveryAction) -> Unit) {
        binding.btnEditProfile.setOnClickListener {
            onAction(ProfileDeliveryAction.OnEditProfileClick)
        }
        binding.btnLogout.setOnClickListener {
            onAction(ProfileDeliveryAction.OnLogoutClick)
        }
        binding.btnSelectRol.setOnClickListener {
            onAction(ProfileDeliveryAction.OnSelectedRolClick)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getUser()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state.collect{
                    insertInfoUser(it.user)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileDeliveryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun insertInfoUser(user: User){
        with(binding){
            tvNameUser.text = getString(R.string.fullName, user.name, user.lastname)
            tvEmailUser.text = user.email
            tvNumberPhoneUser.text = user.phone
            Glide.with(requireContext()).load(user.image).diskCacheStrategy(DiskCacheStrategy.ALL).into(ivProfilePhoto)
        }
    }

}