package com.bahasain.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bahasain.data.Result
import com.bahasain.ui.ViewModelFactory
import com.bahasain.ui.auth.login.LoginActivity
import com.bahasain.ui.setLevel
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

        binding.btnLogout.setOnClickListener{
            viewModel.logout()
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
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