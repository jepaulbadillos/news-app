package com.example.newsapp.webservice.news;

import com.example.newsapp.model.News;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Response {
    @SerializedName("results")
    @Expose
    private ArrayList<News> result;

    public ArrayList<News> getResult() {
        return result;
    }

    public void setResult(ArrayList<News> result) {
        this.result = result;
    }
}
