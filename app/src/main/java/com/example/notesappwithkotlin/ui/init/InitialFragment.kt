package com.example.notesappwithkotlin.ui.init

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.notesappwithkotlin.R
import com.example.notesappwithkotlin.databinding.FragmentInitialBinding

class InitialFragment : Fragment() {

    private var _binding: FragmentInitialBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInitialBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textLogin.setOnClickListener {
            findNavController().navigate(R.id.action_initialFragment_to_loginFragment)
        }

        binding.buttonRegister.setOnClickListener {
            findNavController().navigate(R.id.action_initialFragment_to_registerFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}