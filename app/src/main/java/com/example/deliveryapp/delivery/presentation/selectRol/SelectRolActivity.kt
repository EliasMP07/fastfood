package com.example.deliveryapp.delivery.presentation.selectRol

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deliveryapp.R
import com.example.deliveryapp.databinding.ActivitySelectRolBinding
import com.example.deliveryapp.delivery.presentation.selectRol.adapter.RolAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SelectRolActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectRolBinding
    private val viewModel: SelectRolViewModel by viewModels()

    companion object {
        fun create(context: Context): Intent =
            Intent(context, SelectRolActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectRolBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUi()
    }

    private fun initUi() {
        initList()
        initUiState()
    }

    private fun initUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect{
                    (binding.rvRoles.adapter as RolAdapter).submitList(it.roles)
                    binding.tvTitle.text = getString(R.string.welcome, it.user.name)
                }
            }
        }
    }

    private fun initList() {
        binding.rvRoles.apply {
            layoutManager = GridLayoutManager(this@SelectRolActivity, 2)
            adapter = RolAdapter(onRolSelected = {
                Toast.makeText(this@SelectRolActivity, it.name, Toast.LENGTH_LONG).show()
            })
        }
    }
}