package com.bahasain.ui.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.auth0.android.jwt.JWT
import com.bahasain.ui.ViewModelFactory
import com.dicoding.bahasain.R
import com.dicoding.bahasain.databinding.FragmentLearnBinding
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
//            val userInfo = decodeToken(user.accessToken)

            val jwt = JWT(user.accessToken)

            val name = jwt.getClaim("name").asString()
            val level = jwt.getClaim("level").asInt()

            binding.tvName.text = name
            binding.tvLevel.text = level.toString()
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

//    private fun decodeToken(token: String): Map<String, String?> {
//        val jwt = JWT(token)
//
//        val id = jwt.getClaim("id").asString()
//        val name = jwt.getClaim("name").asString()
//        val isNew = jwt.getClaim("isNew").asBoolean()
//        val level = jwt.getClaim("level").asInt()
//
//        Log.d("DecodeToken", "id: $id, name: $name, isNew: $isNew, level: $level")
//
//        return mapOf(
//            "id" to id,
//            "name" to name,
//            "isNew" to isNew,
//            "level" to level
//        )
//    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

}