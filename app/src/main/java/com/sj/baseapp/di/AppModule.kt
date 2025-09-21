package com.sj.baseapp.di

import android.content.Context
import com.sj.baseapp.networks.ApiInterface
import com.sj.baseapp.utils.UserSharedPreference
import com.sj.baseapp.utils.extension.MoshiUtil
import com.sj.hrm.network.ApiClient
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext context: Context): Context = context

    @Provides
    @Singleton
    fun provideUserSharedPreference(
        @ApplicationContext context: Context
    ): UserSharedPreference = UserSharedPreference(context)

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = MoshiUtil.getMoshi()

    @Singleton
    @Provides
    fun provideApiInterface(
        moshi: Moshi,
        userSharedPreference: UserSharedPreference
    ): ApiInterface = ApiClient.getApiInterface(
        moshi,
        userSharedPreference
    )
}
