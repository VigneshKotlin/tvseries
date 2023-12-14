package com.populartv.app.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tvseries")
data class TVSeries (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val id : Int,
    @ColumnInfo(name = "backdrop_path", defaultValue = "")
    @SerializedName("backdrop_path")
    val backdrop_path : String,
    @ColumnInfo(name = "original_name")
    @SerializedName("original_name")
    val original_name :String,
    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    val overview: String,
    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    val poster_path : String,
    @ColumnInfo(name = "first_air_date")
    @SerializedName("first_air_date")
    val first_air_date : String,
    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    val vote_average : Double,
   /* @ColumnInfo(name = "title")
    val title: String,*/
    @ColumnInfo(name = "page")
    var page: Int,
)

