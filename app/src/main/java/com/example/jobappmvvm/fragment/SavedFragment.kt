package com.example.jobappmvvm.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.jobappmvvm.R
import com.example.jobappmvvm.databinding.FragmentSavedBinding

class SavedFragment : Fragment(R.layout.fragment_saved) {
    private var _binding: FragmentSavedBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSavedBinding.bind(view)

        initViews()
    }

    private fun initViews() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}