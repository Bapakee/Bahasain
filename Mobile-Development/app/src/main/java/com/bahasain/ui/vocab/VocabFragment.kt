package com.bahasain.ui.vocab

import android.graphics.Color
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

        observeTranslate()

        val resultTranslate = binding.tvResultTranslate.text
        val resultPos = binding.tvResultPos.text
        updateVisibility(resultTranslate, resultPos)

        binding.btnTranslate.setOnClickListener{ translate() }
        binding.btnClear.setOnClickListener{ clearText() }
        binding.btnCopy.setOnClickListener{ copyResult() }
    }

<<<<<<< HEAD
=======

>>>>>>> b54a10630529fd8ac91f0351be8af0e26de39951
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

    private fun clearText(){
        binding.textInputTranslate.text?.clear()

        binding.tvResultTranslate.text = ""
        binding.tvResultPos.text = ""

        viewModel.translateResult.value = ""
        viewModel.posResult.value = ""

        updateVisibility(binding.tvResultTranslate.text, binding.tvResultPos.text)
    }

    private fun copyResult(){
        val textCopy = binding.tvResultTranslate.text.toString()

        if (textCopy.isNotEmpty()){
            val clipBoard = requireContext().getSystemService(android.content.Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
            val clip = android.content.ClipData.newPlainText("Copied Text", textCopy)
            clipBoard.setPrimaryClip(clip)
        }
    }

    private fun showLoadingTranslate(isLoading: Boolean) {
        binding.pbResultTranslate.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun observeTranslate(){
        viewModel.translateResult.observe(viewLifecycleOwner) { translate ->
            binding.tvResultTranslate.text = translate

            updateVisibility(binding.tvResultTranslate.text, binding.tvResultPos.text)
        }

        viewModel.posResult.observe(viewLifecycleOwner) { pos ->
            binding.tvResultPos.text = pos

            updateVisibility(binding.tvResultTranslate.text, binding.tvResultPos.text)
        }
    }

    private fun updateVisibility(resultTranslate: CharSequence, resultPos: CharSequence) {
        if (resultTranslate.isNullOrBlank() && resultPos.isNullOrBlank()) {
            binding.vResultTranslate.visibility = View.VISIBLE
            binding.vResultPos.visibility = View.VISIBLE
        } else {
            binding.vResultTranslate.visibility = View.INVISIBLE
            binding.vResultPos.visibility = View.INVISIBLE
        }
    }

}