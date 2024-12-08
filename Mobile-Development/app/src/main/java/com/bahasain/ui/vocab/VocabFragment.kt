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
        CategoryModel("Conjunction", "conjunction"),
        CategoryModel("DerivedNoun", "derivedNoun"),
        CategoryModel("CordConjunct", "coordConj"),
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

        getWotd()

        observeTranslate()

        binding.btnTranslate.setOnClickListener{ translate() }
    }

    private fun getWotd(){
        viewModel.getWotd().observe(viewLifecycleOwner){ result->
            if (result != null){
                when(result){
                    is Result.Loading -> {
                        showLoadingWotd(true)
                    }

                    is Result.Success -> {
                        showLoadingWotd(false)
                        binding.tvTitleWord.text = result.data?.data?.word
                        val categories = result.data?.data?.categories

                        categories?.forEach { category ->
                            binding.tvWordType.text = category?.category
                            binding.resulttranslateWotd.text = category?.translate
                        }
                    }

                    is Result.Error -> {
                        showLoadingWotd(false)

                    }
                }
            }
        }
    }

    private fun translate(){
        val word = binding.textInputTranslate.text.toString()
        viewModel.translate(word).observe(viewLifecycleOwner){ result ->
            if (result != null){
                when(result){
                    is Result.Loading -> {
                        showLoadingTranslate(true)
                    }

                    is Result.Success -> {
                        showLoadingTranslate(false)
                        val translate = result.data?.translate

                        val pos = result.data?.pos?.joinToString("/ ")
                        binding.tvResultPos.text = pos

                        viewModel.translateResult.value = translate
                        viewModel.posResult.value = pos
                    }

                    is Result.Error -> {
                        showLoadingTranslate(false)
                        Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun showLoadingTranslate(isLoading: Boolean) {
        binding.pbResultTranslate.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showLoadingWotd(isLoading: Boolean) {
        binding.pbWotd.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun observeTranslate(){
        viewModel.translateResult.observe(viewLifecycleOwner) { translate ->
            binding.tvResultTranslate.text = translate
        }

        viewModel.posResult.observe(viewLifecycleOwner) { pos ->
            binding.tvResultPos.text = pos
        }
    }

}