package com.test.myapplication.database

import android.content.Context
import androidx.room.Room




class DatabaseClient(mCtx: Context) {


    private var mInstance: DatabaseClient? = null

    //our app database object
    private var appDatabase: AppDatabase? =
        Room.databaseBuilder(mCtx, AppDatabase::class.java, "TVSDIGI").build()



    @Synchronized
    fun getInstance(mCtx: Context): DatabaseClient? {
        if (mInstance == null) {
            mInstance = DatabaseClient(mCtx)
        }
        return mInstance
    }

    fun getAppDatabase(): AppDatabase? {
        return appDatabase
    }
}