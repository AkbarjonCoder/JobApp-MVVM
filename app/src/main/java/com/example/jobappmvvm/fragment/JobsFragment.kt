package com.example.jobappmvvm.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jobappmvvm.MainActivity
import com.example.jobappmvvm.R
import com.example.jobappmvvm.adapter.JobAdapter
import com.example.jobappmvvm.databinding.FragmentJobsBinding
import com.example.jobappmvvm.utils.Resource
import com.example.jobappmvvm.viewmodel.MainViewModel

class JobsFragment : Fragment(R.layout.fragment_jobs) {
    private var _binding: FragmentJobsBinding? = null
    private val binding get() = _binding!!
    private val jobAdapter by lazy { JobAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentJobsBinding.bind(view)

        initViews()
    }

    private fun initViews() {
        val viewModel = (activity as MainActivity).mainViewModel
        binding.rvRemoteJobs.adapter = jobAdapter
        binding.rvRemoteJobs.layoutManager = LinearLayoutManager(requireContext())
        binding.swipeContainer.setOnRefreshListener {
            jobAdapter.submitList(emptyList())
            viewModel.getAllRemoteJobs()
        }
        setupViewModel(viewModel)
        jobAdapter.onClick = {
            val bundle = bundleOf("job" to it)
            findNavController().navigate(R.id.action_mainFragment_to_detailFragment, bundle)
        }
    }

    private fun setupViewModel(viewModel: MainViewModel) {
        viewModel.remoteJobs.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.swipeContainer.isRefreshing = true
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    binding.swipeContainer.isRefreshing = false
                    jobAdapter.submitList(it.data?.jobs)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
