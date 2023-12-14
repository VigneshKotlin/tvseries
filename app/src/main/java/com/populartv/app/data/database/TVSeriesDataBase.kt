package com.populartv.app.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.populartv.app.model.RemoteKeys
import com.populartv.app.model.TVSeries
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Database(
    entities = [TVSeries::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)

abstract class TVSeriesDataBase: RoomDatabase() {
    abstract fun getTVSeriesDAO(): TVSeriesDAO
    abstract fun getRemoteKeysDao(): RemoteKeysDao

}