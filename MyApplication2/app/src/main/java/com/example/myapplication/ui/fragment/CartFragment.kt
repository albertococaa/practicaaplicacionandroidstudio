package com.example.myapplication.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.data.local.CartItemEntity
import com.example.myapplication.databinding.FragmentCartBinding
import com.example.myapplication.repository.CartRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CartFragment : Fragment(R.layout.fragment_cart) {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var repo: CartRepository
    private val adapter = CartAdapter { item: CartItemEntity ->
        lifecycleScope.launch { repo.remove(item) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentCartBinding.bind(view)
        repo = CartRepository(requireContext())

        binding.rvCart.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCart.adapter = adapter

        // Observa cambios en la BBDD
        lifecycleScope.launch {
            repo.all().collectLatest { list ->
                adapter.submitList(list)
            }
        }

        // Botón Volver: regresa al fragment anterior (lista de camisetas)
        binding.btnBackFromCart.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


