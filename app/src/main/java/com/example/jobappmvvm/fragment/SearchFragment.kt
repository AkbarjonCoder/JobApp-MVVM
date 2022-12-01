package com.example.jobappmvvm.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jobappmvvm.MainActivity
import com.example.jobappmvvm.R
import com.example.jobappmvvm.adapter.JobAdapter
import com.example.jobappmvvm.databinding.FragmentSavedBinding
import com.example.jobappmvvm.databinding.FragmentSearchBinding
import com.example.jobappmvvm.utils.Resource
import com.example.jobappmvvm.viewmodel.MainViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment(R.layout.fragment_search) {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private val jobAdapter by lazy { JobAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)
        viewModel = (activity as MainActivity).mainViewModel
        initViews()
    }

    private fun initViews() {
        binding.rvSearchJobs.apply {
            adapter = jobAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        searchJob()
        jobAdapter.onClick = {
            val bundle = bundleOf("job" to it)
            findNavController().navigate(R.id.action_mainFragment_to_detailFragment, bundle)
        }
    }

    private fun searchJob() {
        var job: Job? = null
        binding.etSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                jobAdapter.submitList(emptyList())
                binding.progressBar2.isVisible = false
                delay(600L)
                editable?.let {
                    if (it.toString().isNotBlank()) {
                        viewModel.searchJob(it.toString())
                        viewModel.searchedJobs.observe(viewLifecycleOwner) { result ->
                            when (result) {
                                is Resource.Loading -> {
                                    binding.progressBar2.isVisible = true
                                    binding.notFound.isVisible = false
                                }
                                is Resource.Error -> {
                                    Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT)
                                        .show()
                                    binding.notFound.isVisible = true
                                }
                                is Resource.Success -> {
                                    binding.progressBar2.isVisible = false
                                    binding.notFound.isVisible = false
                                    jobAdapter.submitList(result.data?.jobs)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
