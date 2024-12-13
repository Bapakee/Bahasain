package com.bahasain.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bahasain.data.Result
import com.bahasain.ui.ViewModelFactory
import com.bahasain.utils.AlarmManagerSetup
import com.bahasain.utils.setLevel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.bahasain.R
import com.dicoding.bahasain.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    private val binding get() = _binding!!

    private lateinit var adapter: CertificateAdapter

    private val viewModel: ProfileViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CertificateAdapter()

        binding.rvCertivicate.layoutManager = GridLayoutManager(requireContext(), 2)

        binding.rvCertivicate.adapter = adapter

        getProfile()
    }

    private fun getProfile(){
        viewModel.getProfile().observe(viewLifecycleOwner){ result ->
            if (result != null){
                when(result){
                    is Result.Loading -> {

                    }

                    is Result.Success -> {
                        Glide.with(requireContext())
                            .load(result.data?.data?.avatar ?: R.drawable.icon_profile)
                            .apply(RequestOptions.circleCropTransform())
                            .placeholder(R.drawable.icon_profile)
                            .into(binding.ivProfile)

                        val level =  result.data?.data?.level
                        val progress = getString(R.string.progress,result.data?.data?.percent.toString(), setLevel(level ?: -1))

                        binding.tvName.text = result.data?.data?.name
                        binding.tvLevel.text = setLevel(level ?: -1)

                        val certificates = result.data?.data?.certiLink ?: emptyList()
                        adapter.submitList(certificates)

                        binding.tvProgress.text = progress

                        val notificationType = result.data?.data?.notif ?: 0
                        AlarmManagerSetup.setDailyReminder(requireContext(), notificationType)
                    }

                    is Result.Error -> {

                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

}