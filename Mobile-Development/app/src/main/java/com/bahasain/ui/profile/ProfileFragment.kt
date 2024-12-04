package com.bahasain.ui.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.auth0.android.jwt.JWT
import com.bahasain.ui.ViewModelFactory
import com.bahasain.ui.auth.login.LoginActivity
import com.dicoding.bahasain.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: ProfileViewModel by viewModels {
            factory
        }

        viewModel.getSession().observe(viewLifecycleOwner){ user ->

            val name = user.userName
            val level = user.userLevel

            Log.d("Profile Info", "Name: $name, Level: $level")

            val currentLevel =
                when(level) {
                    1 -> "Basic"
                    2 -> "Intermediate"
                    3 -> "expert"
                    else -> "Unknown Level"
                }
            binding.tvName.text = name
            binding.tvLevel.text = currentLevel
        }

        binding.btnLogout.setOnClickListener{
            viewModel.logout()
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

}