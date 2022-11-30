package com.example.jobappmvvm.repository

import com.example.jobappmvvm.network.ApiService

class JobRepository(
    private val apiService: ApiService
) {
    suspend fun getAllJobs(limit: Int = 25) = apiService.getRemoteJobs(limit)
}