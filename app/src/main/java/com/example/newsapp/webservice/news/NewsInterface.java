package com.example.newsapp.webservice.news;

import com.example.newsapp.webservice.AppUrls;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsInterface {

    @GET(AppUrls.SEARCH)
    Call<NewsResponse> search_news(@Query("q") String q, @Query("api-key") String key);


}
