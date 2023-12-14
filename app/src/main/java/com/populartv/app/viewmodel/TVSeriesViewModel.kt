package com.populartv.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.*
import com.populartv.app.data.database.TVSeriesDataBase
import com.populartv.app.data.repository.PopularTVRepository
import com.populartv.app.model.TVSeries
import com.populartv.app.network.TVSeriesApiService
import com.populartv.app.utils.AppConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@HiltViewModel
class TVSeriesViewModel @Inject constructor(
    private val tvSeriesApiService: TVSeriesApiService,
    private val tvSeriesDatabase: TVSeriesDataBase,
): ViewModel(){
    /**
     * A PagingSource still loads the data; but when the paged data is exhausted, the Paging library triggers the RemoteMediator to load new data from the network source.
     * The RemoteMediator stores the new data in the local database, so an in-memory cache in the ViewModel is unnecessary.
     * Finally, the PagingSource invalidates itself, and the Pager creates a new instance to load the fresh data from the database.
     */

    @OptIn(ExperimentalPagingApi::class)
    fun getPopularTVSeries(): Flow<PagingData<TVSeries>> =
        Pager(
            config = PagingConfig(
                pageSize = AppConstants.PAGE_SIZE,
                prefetchDistance = 10,
                initialLoadSize = AppConstants.PAGE_SIZE, // How many items you want to load initially
            ),
            pagingSourceFactory = {
                // The pagingSourceFactory lambda should always return a brand new PagingSource
                // when invoked as PagingSource instances are not reusable.
                tvSeriesDatabase.getTVSeriesDAO().getPopularTVSeries()
            },
            remoteMediator = PopularTVRepository(
                tvSeriesApiService,
                tvSeriesDatabase,
            )
        ).flow
    @OptIn(ExperimentalPagingApi::class)
    fun getPopularTVSeriesByName(name: String): Flow<PagingData<TVSeries>> =
        Pager(
            config = PagingConfig(
                pageSize = AppConstants.PAGE_SIZE,
                prefetchDistance = 10,
                initialLoadSize = AppConstants.PAGE_SIZE, // How many items you want to load initially
            ),
            pagingSourceFactory = {
                // The pagingSourceFactory lambda should always return a brand new PagingSource
                // when invoked as PagingSource instances are not reusable.
                tvSeriesDatabase.getTVSeriesDAO().loadByName(name)
            },
            remoteMediator = PopularTVRepository(
                tvSeriesApiService,
                tvSeriesDatabase,
            )
        ).flow
}