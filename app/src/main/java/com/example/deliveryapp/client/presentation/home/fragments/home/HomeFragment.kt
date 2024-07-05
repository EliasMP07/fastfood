package com.example.deliveryapp.client.presentation.home.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.deliveryapp.R
import com.example.deliveryapp.client.presentation.home.fragments.home.adapter.CategoryAdapter
import com.example.deliveryapp.client.presentation.home.fragments.profile.passObjectToString
import com.example.deliveryapp.client.domain.mapper.toCategorySerializable
import com.example.deliveryapp.client.presentation.cartShopping.ClientCartActivity
import com.example.deliveryapp.core.presentation.ui.getMealTime
import com.example.deliveryapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeClientViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        initShimmers()
        initUiState()
        initListeners(
            onAction = {action ->
                when(action){
                    HomeAction.OnCartClick -> goToCart()
                    else -> Unit
                }
                viewModel.onAction(action)
            }
        )
        initLists()
    }

    private fun initListeners(
        onAction: (HomeAction) -> Unit
    ) {
        binding.ivMyCart.setOnClickListener {
            onAction(HomeAction.OnCartClick)
        }
        binding.viewNoConnection.btnRetryAgain.setOnClickListener {
            onAction(HomeAction.OnRetryAgainClick)
        }
    }

    private fun initUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state.collect{
                    (binding.rvCategories.adapter as CategoryAdapter).submitList(it.listCategories)
                    binding.tvWelcomeUser.text = getString(R.string.welcomeUser, it.user.name)
                    binding.tvDescription2.text = getString(R.string.title_day_meal, getMealTime(requireContext()))
                    if (it.user.image.isNotEmpty()){
                        Glide.with(requireContext()).load(it.user.image).into(binding.ivProfilePhoto)
                    }
                    if (it.listCategories.isNotEmpty()){
                        binding.shimmerCategories.isVisible = false
                        binding.shimmerCategories.stopShimmer()
                    }
                    binding.viewNoConnection.root.isVisible = it.isError
                }
            }
        }
    }

    private fun goToCart(){
        startActivity(ClientCartActivity.create(requireContext()))
    }

    private fun initShimmers(){
        binding.shimmerCategories.startShimmer()
    }

    private fun initLists() {
        binding.rvCategories.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = CategoryAdapter(onCategorySelected = {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToClientProductListActivity(
                        passObjectToString(it.toCategorySerializable())
                    )
                )
            })
    }}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}