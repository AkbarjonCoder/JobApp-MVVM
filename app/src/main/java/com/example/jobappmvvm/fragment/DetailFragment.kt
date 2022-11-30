package com.example.jobappmvvm.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebViewClient
import com.example.jobappmvvm.R
import com.example.jobappmvvm.databinding.FragmentDetailBinding
import com.example.jobappmvvm.model.Job

class DetailFragment : Fragment(R.layout.fragment_detail) {
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
        job?.let {
            binding.webView.apply {
                webViewClient = WebViewClient()
                loadUrl(it.url!!)
                settings.apply {
                    javaScriptEnabled = true
                    setAppCacheEnabled(true)
                    cacheMode = WebSettings.LOAD_DEFAULT
                    setSupportZoom(false)
                    builtInZoomControls = false
                    displayZoomControls = false
                    textZoom = 100
                    blockNetworkImage = false
                    loadsImagesAutomatically = true
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}