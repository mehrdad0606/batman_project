package com.iranian.cards.android.batmanproject.di.module

import com.iranian.cards.android.batmanproject.data.api.ApiHelper
import com.iranian.cards.android.batmanproject.data.api.retrofit.ApiHelperImpl
import com.iranian.cards.android.batmanproject.data.api.retrofit.ApiService
import com.iranian.cards.android.batmanproject.data.api.retrofit.ErrorInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single<ApiHelper> { return@single ApiHelperImpl(get()) }
    single { provideOkHttpClient() }
    single { provideRetrofit(get(), "http://www.omdbapi.com") }
    single { provideApiService(get()) }
}

private fun provideOkHttpClient(): OkHttpClient {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor) // TODO: 2/2/21 clean this line in release
        .addInterceptor(ErrorInterceptor())
        .build()
}

private fun provideRetrofit(
    okHttpClient: OkHttpClient,
    BASE_URL: String
): Retrofit =
    Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

private fun provideApiService(retrofit: Retrofit): ApiService =
    retrofit.create(ApiService::class.java)
