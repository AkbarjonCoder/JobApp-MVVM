package com.example.jobappmvvm.database


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.*
import com.example.jobappmvvm.model.JobToSave

@Dao
interface JobDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteJob(job: JobToSave)

    @Query("SELECT * FROM job_table ORDER BY id DESC")
    fun getAllFavoriteJob(): LiveData<List<JobToSave>>

    @Delete
    suspend fun deleteFavJob(job: JobToSave)
}