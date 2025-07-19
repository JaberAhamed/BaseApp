package com.sj.baseapp.di

import android.content.Context
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

//    @Provides
//    @Singleton
//    fun provideUserSharedPreference(
//        @ApplicationContext context: Context
//    ): UserSharedPreference = UserSharedPreference(context)
//
//    @Singleton
//    @Provides
//    fun provideMoshi(): Moshi = MoshiUtil.getMoshi()
//
//    @Singleton
//    @Provides
//    fun provideApiInterface(
//        moshi: Moshi,
//        userSharedPreference: UserSharedPreference
//    ): ApiInterface = ApiClient.getApiInterface(
//        moshi,
//        userSharedPreference
//    )
}
