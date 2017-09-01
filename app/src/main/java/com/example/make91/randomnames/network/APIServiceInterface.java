package com.example.make91.randomnames.network;

import com.example.make91.randomnames.beans.Person;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIServiceInterface {
    @GET("api/")
    Call<List<Person>> getPersonList(@Query("amount") int amount, @Query("ext") boolean extra);
}
