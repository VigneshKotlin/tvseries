package com.populartv.app.data.database

import androidx.paging.PagingSource
import androidx.room.*
import com.populartv.app.model.TVSeries

@Dao
interface TVSeriesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE) //if some data is same/conflict, it'll be replace with new data.
    suspend fun insertTVSeries(tvseries: List<TVSeries>)

    @Query("SELECT * FROM tvseries ORDER BY page")
    fun getPopularTVSeries() : PagingSource<Int, TVSeries>

    @Query("Delete FROM tvseries")
    suspend fun clearAllTVSeries()

    @Query("SELECT * FROM tvseries WHERE original_name LIKE  :name||'%'")
    fun loadByName(name: String?): PagingSource<Int, TVSeries>
}