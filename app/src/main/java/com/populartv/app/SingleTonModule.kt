package com.populartv.app

import android.content.Context
import androidx.room.Room
import com.populartv.app.data.database.RemoteKeysDao
import com.populartv.app.data.database.TVSeriesDAO
import com.populartv.app.data.database.TVSeriesDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SingleTonModule {
    @Singleton
    @Provides
    fun provideTVSeriesDatabase(@ApplicationContext context: Context): TVSeriesDataBase =
        Room.databaseBuilder(context, TVSeriesDataBase::class.java, "popular_tvseries_db")
            .build()

    @Singleton
    @Provides
    fun provideTVSeriesDao(moviesDatabase: TVSeriesDataBase): TVSeriesDAO = moviesDatabase.getTVSeriesDAO()

    @Singleton
    @Provides
    fun provideRemoteKeysDao(moviesDatabase: TVSeriesDataBase): RemoteKeysDao = moviesDatabase.getRemoteKeysDao()

}