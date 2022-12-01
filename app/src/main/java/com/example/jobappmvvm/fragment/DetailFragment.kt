package com.example.jobappmvvm.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebViewClient
import com.example.jobappmvvm.MainActivity
import com.example.jobappmvvm.R
import com.example.jobappmvvm.databinding.FragmentDetailBinding
import com.example.jobappmvvm.mapper.toJobToSave
import com.example.jobappmvvm.model.Job
import com.example.jobappmvvm.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class DetailFragment : Fragment(R.layout.fragment_detail) {
    private lateinit var viewModel: MainViewModel
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private var job: Job? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = arguments?.getParcelable("job") as? Job
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailBinding.bind(view)
        viewModel = (activity as MainActivity).mainViewModel
        job?.let { savedJob ->
            binding.webView.apply {
                webViewClient = WebViewClient()
                loadUrl(savedJob.url!!)
                settings.apply {
                    javaScriptEnabled = true
                    cacheMode = WebSettings.LOAD_DEFAULT
                    setSupportZoom(false)
                    builtInZoomControls = false
                    displayZoomControls = false
                    textZoom = 100
                    blockNetworkImage = false
                    loadsImagesAutomatically = true
                }
            }
            binding.fabAddFavorite.setOnClickListener {
                viewModel.saveFavoriteJob(savedJob.toJobToSave())
                Snackbar.make(it, "Job Saved", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}