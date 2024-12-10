package com.bahasain.ui.cultural

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bahasain.data.Result
import com.bahasain.ui.ViewModelFactory
import com.bahasain.ui.cultural.folklore.FolkloreActivity
import com.bahasain.ui.cultural.historical.HistoricalActivity
import com.dicoding.bahasain.databinding.FragmentCulturalBinding

class CulturalFragment : Fragment() {

    private var _binding: FragmentCulturalBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapterHistorical: HistoricalAdapter
    private lateinit var adapterFolklore: FolkloreAdapter
    private lateinit var adapterRecipe: RecipeAdapter

    private val viewModel: CulturalViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCulturalBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterHistorical = HistoricalAdapter()
        adapterFolklore = FolkloreAdapter()
        adapterRecipe = RecipeAdapter()

        binding.rvHistorical.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvFolklore.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.rvRecipe.layoutManager = GridLayoutManager(requireContext(), 3)

        binding.rvHistorical.adapter = adapterHistorical
        binding.rvFolklore.adapter = adapterFolklore
        binding.rvRecipe.adapter = adapterRecipe

        getHistorical()
        getFolklore()
        getRecipe()

        binding.btnHistoricalShowAll.setOnClickListener {
            val intent = Intent(requireContext(), HistoricalActivity::class.java)
            startActivity(intent)
        }

        binding.btnFolkloreShowAll.setOnClickListener {
            val intent = Intent(requireContext(), FolkloreActivity::class.java)
            startActivity(intent)
        }

        binding.btnRecipeShowAll.setOnClickListener {

        }
    }

    private fun getHistorical() {
        viewModel.getHistorical().observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {

                    }

                    is Result.Success -> {
                        adapterHistorical.submitList(result.data)
                    }

                    is Result.Error -> {

                    }
                }
            }
        }
    }

    private fun getFolklore() {
        viewModel.getFolklore().observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {

                    }

                    is Result.Success -> {
                        adapterFolklore.submitList(result.data)
                    }

                    is Result.Error -> {

                    }
                }
            }
        }
    }

    private fun getRecipe() {
        viewModel.getRecipe().observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {

                    }

                    is Result.Success -> {
                        adapterRecipe.submitList(result.data)
                    }

                    is Result.Error -> {

                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}