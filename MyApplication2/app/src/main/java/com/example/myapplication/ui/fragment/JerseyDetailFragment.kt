package com.example.myapplication.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.local.CartItemEntity
import com.example.myapplication.databinding.FragmentJerseyDetailBinding
import com.example.myapplication.repository.CartRepository
import kotlinx.coroutines.launch

class JerseyDetailFragment : Fragment(R.layout.fragment_jersey_detail) {

    private var _binding: FragmentJerseyDetailBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentJerseyDetailBinding.bind(view)

        // Recuperar argumentos
        val args        = requireArguments()
        val jerseyId    = args.getInt("id")               // ahora lo llamamos jerseyId
        val team        = args.getString("team").orEmpty()
        val price       = args.getFloat("price")
        val description = args.getString("description").orEmpty()
        val imageUrl    = args.getString("imageUrl").orEmpty()

        // Rellenar campos
        binding.tvTeamDetail.text        = team
        binding.tvPriceDetail.text       = "€%.2f".format(price)
        binding.tvDescriptionDetail.text = description

        // Cargar imagen con Glide
        Glide.with(this)
            .load(imageUrl)
            .centerCrop()
            .into(binding.ivJerseyDetail)

        // Inicializar repositorio de carrito
        val cartRepo = CartRepository(requireContext())

        // Botón "Añadir al carrito"
        binding.btnAddToCart.setOnClickListener {
            // Creamos la entidad con jerseyId, sin tocar cartItemId (autogen)
            val item = CartItemEntity(
                jerseyId = jerseyId,       // la referencia a la camiseta
                team     = team,
                price    = price.toDouble(),
                imageUrl = imageUrl
            )
            lifecycleScope.launch {
                cartRepo.add(item)
                Toast.makeText(
                    requireContext(),
                    "Añadido al carrito",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // Botón "Volver"
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
