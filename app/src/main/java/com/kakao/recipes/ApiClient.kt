package com.kakao.recipes

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection

class ApiClient {

    private lateinit var retrofit: Retrofit
    private var gson: Gson = GsonBuilder().create()

    companion object {
        var apiClient: ApiClient? = null

        fun getInstance(): ApiClient? {
            if(apiClient == null) {
                apiClient = ApiClient()
            }
            return apiClient
        }
    }
    fun getClient(): Retrofit {

        val client2: OkHttpClient = OkHttpClient.Builder()

            .hostnameVerifier(HostnameVerifier { hostname, session ->
                val hv = HttpsURLConnection.getDefaultHostnameVerifier()
                true
            })
            .connectTimeout(100, TimeUnit.SECONDS)
            .addInterceptor(ApiKeyInterceptor())
            .readTimeout(100, TimeUnit.SECONDS).build()

        this.retrofit = Retrofit.Builder()
            .baseUrl(RecipesKeys.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client2)
            .build()

        return retrofit
    }

}
class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val newUrl = originalUrl.newBuilder()
            .addQueryParameter("apiKey", RecipesKeys.API_KEY)
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}

