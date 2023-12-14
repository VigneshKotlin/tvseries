package com.populartv.app.model

import com.google.gson.annotations.SerializedName


data class PopularTVListResponse( val page: Int,
                                  @SerializedName(value = "results")
                                  val tvSeries: List<TVSeries>,
                                  @SerializedName("total_pages")
                                  val totalPages: Int,
                                  @SerializedName("total_results")
                                  val totalResults: Int)


