package com.bahasain.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bahasain.data.Result
import com.bahasain.ui.ViewModelFactory
import com.bahasain.ui.onboarding.OnBoardingActivity
import com.bahasain.ui.placement.PlacementActivity
import com.bahasain.ui.placement.PlacementIntroActivity
import com.bahasain.ui.survey.SurveyActivity
import com.bahasain.ui.vocab.VocabViewModel
import com.dicoding.bahasain.R
import com.dicoding.bahasain.databinding.FragmentHomeBinding
import com.dicoding.bahasain.databinding.FragmentLearnBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getTrivia()
        getWotd()

        binding.btnSurvey.setOnClickListener{
            val intent = Intent(requireContext(), SurveyActivity::class.java)
            startActivity(intent)
        }

        binding.btnPlacement.setOnClickListener{
            val intent = Intent(requireContext(), PlacementIntroActivity::class.java)
            startActivity(intent)
        }
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
                        binding.tvDate.text = getDateCalendar()
                        binding.tvTitleWord.text = result.data?.data?.word
                        val categories = result.data?.data?.categories

                        categories?.forEach { category ->
                            showLoadingWotd(false)
                            binding.tvWordType.text = category?.category
                            binding.resultTranslate.text = category?.translate
                            binding.tvExample.text = category?.example
                        }
                    }

                    is Result.Error -> {

                    }
                }
            }
        }
    }

    private fun getTrivia(){
        viewModel.getTrivia().observe(viewLifecycleOwner){ result ->
            if (result != null){
                when(result){
                    is Result.Loading -> {

                    }

                    is Result.Success -> {
                        binding.tvTitleTrivia.text = result.data?.data?.title
                        binding.tvDescTrivia.text = result.data?.data?.description
                    }

                    is Result.Error -> {

                    }
                }
            }
        }
    }

    private fun showLoadingWotd(isLoading: Boolean) {
        binding.pbWotdHome.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun getDateCalendar(): String {
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = SimpleDateFormat("MMMM", Locale("id", "ID")).format(calendar.time) // Nama bulan
        val year = calendar.get(Calendar.YEAR)
        return "$month $day, $year"
    }

}