package com.example.jobappmvvm.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.jobappmvvm.R
import com.example.jobappmvvm.databinding.FragmentSearchBinding

class SearchFragment : Fragment(R.layout.fragment_search) {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)

        initViews()
    }

    private fun initViews() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}