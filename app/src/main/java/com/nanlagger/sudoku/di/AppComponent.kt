package com.nanlagger.sudoku.di

import android.content.Context
import com.nanlagger.sudoku.data.DosukuApi
import com.nanlagger.sudoku.data.repository.DosukuRepositoryImpl
import com.nanlagger.sudoku.domain.repository.DosukuRepository
import kotlinx.serialization.json.Json
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory


@ApplicationScope
@Component
abstract class AppComponent(private val context: Context) {

    abstract val dosukuRepository: DosukuRepository

    @ApplicationScope
    @Provides
    fun provideApplicationContext() = context

    @ApplicationScope
    @Provides
    fun provideRetrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
        return Retrofit.Builder()
            .client(client)
            .baseUrl("https://sudoku-api.vercel.app/api/")
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @ApplicationScope
    @Provides
    fun provideDosukuApi(retrofit: Retrofit): DosukuApi {
        return retrofit.create(DosukuApi::class.java)
    }

    @ApplicationScope
    @Provides
    fun provideDosukuRepository(dosukuApi: DosukuApi): DosukuRepository {
        return DosukuRepositoryImpl(dosukuApi)
    }
}