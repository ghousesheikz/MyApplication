package com.test.myapplication.database


import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = arrayOf(ModelSaveData::class),version = 1)
abstract class AppDatabase :RoomDatabase() {
    abstract fun dataDao() : DataDao


}