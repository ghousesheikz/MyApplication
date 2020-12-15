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

    @Query("SELECT * FROM ModelSaveData WHERE category=:categoryid")
    fun getDatabyCategoryid(categoryid: String?): List<ModelSaveData?>?


    @Query("SELECT * FROM ModelSaveData WHERE price BETWEEN :price_low AND :price_high AND quantity>0  AND category=:categoryid")
    fun getDatabypriceandstock(price_low:String?,price_high:String?,categoryid: String?): List<ModelSaveData?>?

    @Query("SELECT * FROM ModelSaveData WHERE price BETWEEN :price_low AND :price_high AND quantity=0  AND category=:categoryid")
    fun getDatabypriceandoutofstock(price_low:String?,price_high:String?,categoryid: String?): List<ModelSaveData?>?


    @Query("SELECT MAX(price) FROM ModelSaveData")
    fun getmaxPrice(): Int?


}