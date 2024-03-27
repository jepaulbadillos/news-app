package com.example.newsapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class News {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("webTitle")
    @Expose
    private String title;
    @SerializedName("webUrl")
    @Expose
    private String link;
    @SerializedName("webPublicationDate")
    @Expose
    private String publication_date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPublication_date() {
        return publication_date;
    }

    public void setPublication_date(String publication_date) {
        this.publication_date = publication_date;
    }
}
