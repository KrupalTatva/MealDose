package com.app.compose_structure.di

import com.app.compose_structure.data.local.ISharedPreferences
import com.app.compose_structure.data.local.SharedPreferencesImpl
import com.app.compose_structure.data.remote.IBaseRemoteService
import com.app.compose_structure.data.remote.RemoteServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
interface DataSourceModule {

    @Binds
    fun provideSharedPreferences(dataSource: SharedPreferencesImpl): ISharedPreferences

    @Binds
    fun provideBaseRemoteDataSource(dataSource: RemoteServiceImpl): IBaseRemoteService

}