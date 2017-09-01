package com.example.make91.randomnames.network;

import com.example.make91.randomnames.beans.Person;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class APIService {
    OkHttpClient httpClient;
    APIServiceInterface service;
    private static final String BASE_URL = "http://uinames.com/";

    public APIService() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        httpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(loggingInterceptor)
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .build();

        service = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient)
                .addConverterFactory(JacksonConverterFactory.create())
                .build()
                .create(APIServiceInterface.class);
    }

    public Call<List<Person>> getPeople(int number, boolean extra) {
        return service.getPersonList(number, extra);
    }
}