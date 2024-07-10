package com.example.documentscanner.di

import com.example.documentscanner.localStorage.AppPreferences
import com.example.documentscanner.network.ApiInterface
import com.example.documentscanner.network.AuthTokenInterceptor
import com.example.documentscanner.network.AuthTokenManager
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Modules {
    @Provides
    @Singleton
    fun providesRetrofit(authTokenInterceptor: AuthTokenInterceptor):Retrofit {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient().newBuilder()
            .addInterceptor(authTokenInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .writeTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .build()
        return Retrofit.Builder()
            .baseUrl("http://192.168.1.28:8080")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .client(okHttpClient)
            .build()
    }
    @Provides
    @Singleton
    fun providesApi(retrofit: Retrofit):ApiInterface{
        return retrofit.create(ApiInterface::class.java)
    }
    @Provides
    @Singleton
    fun provideAuthToken(appPreferences: AppPreferences):AuthTokenManager{
        return AuthTokenManager(appPreferences)
    }
}