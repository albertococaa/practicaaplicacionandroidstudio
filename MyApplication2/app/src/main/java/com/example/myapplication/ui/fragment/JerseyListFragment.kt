package com.example.myapplication.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentJerseyListBinding
import com.example.myapplication.model.Jersey
import com.example.myapplication.repository.JerseyRepository
import kotlinx.coroutines.launch

class JerseyListFragment : Fragment(R.layout.fragment_jersey_list) {

    private var _binding: FragmentJerseyListBinding? = null
    private val binding get() = _binding!!

    private val repo = JerseyRepository()
    private val adapter = JerseyAdapter { jersey -> navigateToDetail(jersey) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentJerseyListBinding.bind(view)
        binding.rvJerseys.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvJerseys.adapter = adapter
        loadJerseys()
    }

    private fun loadJerseys() {
        binding.progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            try {
                val list = repo.fetchAll()
                adapter.submitList(list)
            } catch (e: Exception) {
                // TODO: mostrar error al usuario
            } finally {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun navigateToDetail(jersey: Jersey) {
        // PASAMOS 'id' COMO STRING, igual que en nav_graph.xml
        val bundle = Bundle().apply {
            putString("id", jersey.id)
            putString("team", jersey.team)
            putFloat("price", jersey.price.toFloat())
            putString("description", jersey.description)
            putString("imageUrl", jersey.imageUrl)
        }
        findNavController().navigate(
            R.id.action_jerseyListFragment_to_jerseyDetailFragment,
            bundle
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

