package com.test.myapplication.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ModelSaveData(


    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "popularity") var popularity: Double?=0.0,
    @ColumnInfo(name = "voteCount") var voteCount: Int?,
    @ColumnInfo(name = "video") var video: Boolean?= false,
    @ColumnInfo(name = "posterPath") var posterPath: String?,
    @ColumnInfo(name = "adult") var adult: Boolean?=false,
    @ColumnInfo(name = "backdropPath") var backdropPath: String?,
    @ColumnInfo(name = "originalLanguage") var originalLanguage: String?,
    @ColumnInfo(name = "originalTitle") var originalTitle: String?,
    @ColumnInfo(name = "title") var title: String?,
    @ColumnInfo(name = "voteAverage") var voteAverage:Double?=0.0,
    @ColumnInfo(name = "overview") var overview: String?,
    @ColumnInfo(name = "releaseDate") var releaseDate: String?
)


