package com.example.jobappmvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.jobappmvvm.database.JobDatabase
import com.example.jobappmvvm.databinding.ActivityMainBinding
import com.example.jobappmvvm.network.RetroInstance
import com.example.jobappmvvm.repository.JobRepository
import com.example.jobappmvvm.viewmodel.MainViewModel
import com.example.jobappmvvm.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()

        val dao = JobDatabase.invoke(this).jobDao()
        val repository = JobRepository(RetroInstance.retroInstance(), dao)
        val viewModelFactory = MainViewModelFactory(application, repository)
        mainViewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

    }
}
