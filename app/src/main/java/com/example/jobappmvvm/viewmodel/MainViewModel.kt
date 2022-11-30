package com.example.jobappmvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.jobappmvvm.model.RemoteJob
import com.example.jobappmvvm.repository.JobRepository
import com.example.jobappmvvm.utils.Resource
import kotlinx.coroutines.launch

class MainViewModel(
    app: Application,
    private val repository: JobRepository
) : AndroidViewModel(app) {
    private val _remoteJobs: MutableLiveData<Resource<RemoteJob>> = MutableLiveData()
    val remoteJobs: LiveData<Resource<RemoteJob>> get() = _remoteJobs

    init {
        getAllRemoteJobs()
    }

    fun getAllRemoteJobs() {
        viewModelScope.launch {
            _remoteJobs.postValue(Resource.Loading())
            try {
                val response = repository.getAllJobs()
                if (response.isSuccessful) {
                    _remoteJobs.postValue(Resource.Success(response.body()!!))
                }
            } catch (e: Exception) {
                _remoteJobs.postValue(Resource.Error(e.message!!))
            }
        }
    }
}