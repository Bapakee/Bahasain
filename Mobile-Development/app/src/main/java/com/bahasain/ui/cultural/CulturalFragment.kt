package com.bahasain.ui.cultural

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
import com.dicoding.bahasain.databinding.FragmentCulturalBinding

class CulturalFragment : Fragment() {

    private var _binding: FragmentCulturalBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapterHistorical: HistoricalAdapter
    private lateinit var adapterFolklore: FolkloreAdapter

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

        binding.rvHistorical.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvFolklore.layoutManager = GridLayoutManager(requireContext(), 3)

        binding.rvHistorical.adapter = adapterHistorical
        binding.rvFolklore.adapter = adapterFolklore

        getHistorical()
        getFolklore()
    }

    private fun getHistorical(){
        viewModel.getHistorical().observe(viewLifecycleOwner){ result ->
            if (result != null){
                when(result){
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

    private fun getFolklore(){
        viewModel.getFolklore().observe(viewLifecycleOwner){ result ->
            if (result != null ){
                when(result){
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}