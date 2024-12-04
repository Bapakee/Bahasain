package com.bahasain.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bahasain.ui.onboarding.OnBoardingActivity
import com.bahasain.ui.survey.SurveyActivity
import com.dicoding.bahasain.R
import com.dicoding.bahasain.databinding.FragmentHomeBinding
import com.dicoding.bahasain.databinding.FragmentLearnBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       binding.btn.setOnClickListener{
           val intent = Intent(requireContext(), SurveyActivity::class.java)
           startActivity(intent)
       }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }


}