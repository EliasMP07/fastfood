package com.example.deliveryapp.restaurant.presentation.home.fragments.profile

import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
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
import com.example.deliveryapp.core.user.data.mapper.toUserSerializable
import com.example.deliveryapp.core.user.domain.model.User
import com.example.deliveryapp.databinding.FragmentRestaurantProfileBinding
import com.example.deliveryapp.selectRol.SelectRolActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@AndroidEntryPoint
class RestaurantProfileFragment : Fragment() {

    private var _binding: FragmentRestaurantProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<RestaurantProfileViewModel>()

    override fun onResume() {
        super.onResume()
        viewModel.getUser()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        initObservers()
        initListeners(onAction = { action ->
            when (action) {
                RestaurantProfileAction.OnEditProfileClick -> goToEditProfile()
                RestaurantProfileAction.OnLogoutClick -> goToLogin()
                RestaurantProfileAction.OnSelectRolClick -> goToSelectRol()
            }
        })
    }

    private fun goToLogin() {
        viewModel.logout()
        startActivity(LoginActivity.create(requireContext()).apply {
            addFlags(FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK)
        })
    }

    private fun goToEditProfile() {
        findNavController().navigate(
            RestaurantProfileFragmentDirections.actionRestaurantProfileFragmentToUpdateProfileActivity(
                Json.encodeToString(viewModel.state.value.user.toUserSerializable())
            )
        )
    }

    private fun goToSelectRol() {
        startActivity(SelectRolActivity.create(requireContext()).apply {
            addFlags(FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK)
        })
    }

    private fun initListeners(onAction: (RestaurantProfileAction) -> Unit) {
        binding.btnEditProfile.setOnClickListener {
            onAction(RestaurantProfileAction.OnEditProfileClick)
        }
        binding.btnLogout.setOnClickListener {
            onAction(RestaurantProfileAction.OnLogoutClick)
        }
        binding.btnSelectRol.setOnClickListener {
            onAction(RestaurantProfileAction.OnSelectRolClick)
        }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    insertInfoUser(it.user)
                }
            }
        }
    }

    private fun insertInfoUser(user: User) {
        with(binding) {
            tvNameUser.text = getString(R.string.fullName, user.name, user.lastname)
            tvEmailUser.text = user.email
            tvNumberPhoneUser.text = user.phone
            Glide.with(requireContext()).load(user.image).diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivProfilePhoto)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRestaurantProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}