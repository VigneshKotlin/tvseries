package com.populartv.app.model

import com.google.gson.annotations.SerializedName

data class TVResponseDetails(val backdrop_path : String, val original_name :String, val overview: String,
                             val poster_path : String, val status : String,
                             val first_air_date : String, val vote_average : Double)