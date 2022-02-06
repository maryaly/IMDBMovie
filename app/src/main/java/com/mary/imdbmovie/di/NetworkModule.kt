package com.mary.imdbmovie.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mary.imdbmovie.BuildConfig
import com.mary.imdbmovie.R
import com.mary.imdbmovie.data.remote.MovieApiService
import com.mary.imdbmovie.data.remote.model.MovieDtoMapper
import com.mary.imdbmovie.util.Constants
import com.mary.imdbmovie.util.network.NetworkHelper
import com.mary.imdbmovie.util.network.NetworkHelperImpl
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideNetworkHelperImpl(@ApplicationContext mContext: Context): NetworkHelper =
        NetworkHelperImpl(mContext)

    @Singleton
    @Provides
    fun provideMovieDtoMapper(): MovieDtoMapper = MovieDtoMapper()

    @Singleton
    @Provides
    fun provideRetrofit(
            okHttpClient: OkHttpClient,
            moshi: Moshi
    ) : Retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .build()

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

    @Singleton
    @Provides
    fun provideOkHttpInterceptor(): Interceptor = Interceptor { chain: Interceptor.Chain ->
        val original: Request = chain.request()
        val requestBuilder: Request.Builder = original.newBuilder()
                .addHeader("x-rapidapi-key", BuildConfig.API_KEY)
                .addHeader("x-rapidapi-host", "movie-database-imdb-alternative.p.rapidapi.com")
        val request: Request = requestBuilder.build()
        chain.proceed(request)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
            loggingInterceptor: HttpLoggingInterceptor,
            interceptor: Interceptor
    ): OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(interceptor)
            .build()

    @Singleton
    @Provides
    fun provideGlideInstance(
            @ApplicationContext context: Context
    ) = Glide.with(context).setDefaultRequestOptions(
            RequestOptions()
                    .placeholder(R.drawable.ic_image)
                    .error(R.drawable.ic_image)
    )

    @Provides
    @Singleton
    fun provideMovieApiService(retrofit: Retrofit): MovieApiService = retrofit
            .create(MovieApiService::class.java)

}