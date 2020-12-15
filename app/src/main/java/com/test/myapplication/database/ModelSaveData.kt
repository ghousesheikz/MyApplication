package com.test.myapplication.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ModelSaveData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") var name: String?,
    @ColumnInfo(name = "category") var category: String?,
    @ColumnInfo(name = "imgUrl") var imgUrl: String?,
    @ColumnInfo(name = "quantity") var quantity: Int?= 0,
    @ColumnInfo(name = "description") var description: String?,
    @ColumnInfo(name = "price") var price: Int?= 0
)


