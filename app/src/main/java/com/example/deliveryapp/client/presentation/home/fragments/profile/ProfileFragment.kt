package com.example.deliveryapp.client.presentation.home.fragments.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.deliveryapp.R
import com.example.deliveryapp.auth.presentation.login.LoginActivity
import com.example.deliveryapp.core.data.UserSerializable
import com.example.deliveryapp.core.user.data.mapper.toUser
import com.example.deliveryapp.core.user.data.mapper.toUserSerializable
import com.example.deliveryapp.core.user.domain.model.User
import com.example.deliveryapp.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<ProfileClientViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        initObservers()
        initListeners(onAction = {action ->
            when(action){
                ProfileClientAction.OnEditProfileClick -> goToEditProfile()
                ProfileClientAction.OnLogoutClick -> goToLogin()
            }
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
            ProfileFragmentDirections.actionProfileFragmentToUpdateProfileActivity(
                passObjectToString(viewModel.state.value.user.toUserSerializable())
            )
        )
    }


    private fun initListeners(onAction: (ProfileClientAction)-> Unit) {
        binding.btnEditProfile.setOnClickListener {
            onAction(ProfileClientAction.OnEditProfileClick)
        }
        binding.btnLogout.setOnClickListener {
            onAction(ProfileClientAction.OnLogoutClick)
        }
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

    private fun insertInfoUser(user: User){
        with(binding){
            tvNameUser.text = getString(R.string.fullName, user.name, user.lastname)
            tvEmailUser.text = user.email
            tvNumberPhoneUser.text = user.phone
            if (user.image.isNotEmpty()){
                Glide.with(requireContext()).load(user.image).diskCacheStrategy(DiskCacheStrategy.ALL).into(ivProfilePhoto)
            }
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}

inline fun <reified T> passObjectToString(value: T): String {
    return Json.encodeToString(value)
}

inline fun <reified T> convertStringToObject(value: String): T {
    return Json.decodeFromString(value)
}