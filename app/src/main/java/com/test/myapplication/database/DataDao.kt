package com.test.myapplication.database

import androidx.room.*


@Dao
interface DataDao {

    @Query("SELECT * FROM ModelSaveData")
    fun getAll(): List<ModelSaveData?>?

    @Query("DELETE FROM ModelSaveData")
    fun deleteData()

    @Insert
    fun insert(task: ModelSaveData?)

    @Delete
    fun delete(task: ModelSaveData?)

    @Update
    fun update(task: ModelSaveData?)

    @Query("SELECT * FROM ModelSaveData ORDER BY voteAverage ASC")
    fun getAscendingList(): List<ModelSaveData?>?

    @Query("SELECT * FROM ModelSaveData ORDER BY voteAverage DESC")
    fun getDescendingList(): List<ModelSaveData?>?

    @Query("SELECT * FROM ModelSaveData ORDER BY releaseDate ASC")
    fun getAscReleaseDates(): List<ModelSaveData?>?

    @Query("SELECT * FROM ModelSaveData ORDER BY releaseDate DESC")
    fun getDescReleaseDates(): List<ModelSaveData?>?
}