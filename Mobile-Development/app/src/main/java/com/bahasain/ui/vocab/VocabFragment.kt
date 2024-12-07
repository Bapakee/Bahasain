package com.bahasain.ui.vocab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bahasain.ui.learn.LearnAdapter
import com.dicoding.bahasain.R
import com.dicoding.bahasain.databinding.FragmentVocabBinding

class VocabFragment : Fragment() {

    private  var _binding: FragmentVocabBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: CategoryAdapter

    val categories = listOf(
        CategoryModel("Nouns"),
        CategoryModel("Verbs"),
        CategoryModel("Adjectives"),
        CategoryModel("Numbers", ),
        CategoryModel("Common Phrases"),
        CategoryModel("Trivia")
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
    }

}