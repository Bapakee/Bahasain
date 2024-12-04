package com.bahasain.ui.learn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bahasain.data.Result
import com.bahasain.ui.ViewModelFactory
import com.dicoding.bahasain.databinding.FragmentLearnBinding

class LearnFragment : Fragment() {

    private var _binding: FragmentLearnBinding? = null

    private val binding get() = _binding!!

    private lateinit var adapter: LearnAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLearnBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = LearnAdapter()

        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: LearnViewModel by viewModels {
            factory
        }

        binding.recyclerView.adapter = adapter

        viewModel.getModule().observe(viewLifecycleOwner) { module ->
            when (module) {
                is Result.Loading -> {
                    showLoading(true)
                }

                is Result.Success -> {
                    showLoading(false)
                    adapter.submitList(module.data)
                }

                is Result.Error -> {
                    showLoading(false)
                    Toast.makeText(requireContext(), "Error ${module.error}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}