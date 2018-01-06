package com.github.tommykw.musical.api

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.google.gson.internal.bind.DateTypeAdapter
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import io.reactivex.Observable
import tokyo.tommy_kw.musical.data.entity.Event
import java.util.*

class ApiClient {
    companion object {
        private val ENDPOINT = "http://api.openweathermap.org/data/"
        private var mApiService: ApiService

        init {
            mApiService = getRestAdapter().create(ApiService::class.java)
        }

        fun getApiClient(): ApiService {
            if (mApiService != null) {
                return mApiService
            }

            mApiService = getRestAdapter().create(ApiService::class.java)
            return mApiService
        }

        fun getOkClient(): OkHttpClient {
            val client = OkHttpClient()
            client.networkInterceptors().add(StethoInterceptor())
            return client
        }

        fun getEvent(): Observable<Event> {
            return Observable.just(null)
            //return mApiService.getWeather()
        }

        private fun getRestAdapter(): Retrofit {
            val gsonBuilder = GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .registerTypeAdapter(Date::class.java, DateTypeAdapter()).create()

            return Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .build()
        }
    }
}