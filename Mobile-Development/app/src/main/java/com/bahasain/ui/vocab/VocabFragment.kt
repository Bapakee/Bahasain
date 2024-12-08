package com.bahasain.ui.vocab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bahasain.data.Result
import com.bahasain.ui.ViewModelFactory
import com.dicoding.bahasain.databinding.FragmentVocabBinding

class VocabFragment : Fragment() {

    private  var _binding: FragmentVocabBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: CategoryAdapter

    private val viewModel: VocabViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    private val categories = listOf(
        CategoryModel("Nouns", "noun"),
        CategoryModel("Verbs", "verb"),
        CategoryModel("Adjectives", "adjective"),
        CategoryModel("Adverb", "adverb"),
        CategoryModel("Preposition", "preposition"),
        CategoryModel("Conjunction", "conjunction")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVocabBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CategoryAdapter()

        binding.rvCategory.layoutManager = GridLayoutManager(requireContext(), 3)

        binding.rvCategory.adapter = adapter

        adapter.submitList(categories)

        binding.btnTranslate.setOnClickListener{ translate() }
    }

    private fun translate(){
        val word = binding.textInputTranslate.text.toString()
        viewModel.translate(word).observe(viewLifecycleOwner){ result ->
            if (result != null){
                when(result){
                    is Result.Loading -> {

                    }

                    is Result.Success -> {
                        binding.tvResultTranslate.text = result.data?.translate

                        val pos = result.data?.pos?.joinToString("/ ")
                        binding.tvResultPos.text = pos
                    }

                    is Result.Error -> {
                        Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

}