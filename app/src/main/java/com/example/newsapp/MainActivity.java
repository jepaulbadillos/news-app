package com.example.newsapp;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.model.News;
import com.example.newsapp.webservice.ApiClient;
import com.example.newsapp.webservice.news.NewsInterface;
import com.example.newsapp.webservice.news.NewsResponse;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Context mCon;
    private final ArrayList<News> newsData = new ArrayList<>();
    NewsAdapter adapter;
    RecyclerView rv_news;

    private final long delay = 1000; // 1 seconds after user stops typing
    private final Handler handler = new Handler();
    private String searchKey = "";

    private final Runnable input_finish_checker = () -> {
        Log.d("Search", "searchKey: " + searchKey);
        searchNews(searchKey);
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        rv_news = (RecyclerView) findViewById(R.id.rv_news);

        mCon = this;
        setAdapter();
        searchNews("");

        TextInputEditText inputEditText = ( TextInputEditText) findViewById(R.id.et_search);

        inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                searchKey = editable.toString();
                handler.removeCallbacks(input_finish_checker);
                handler.postDelayed(input_finish_checker, delay);
            }
        });
    }

    public void setAdapter(){

        adapter = new NewsAdapter(newsData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mCon);
        rv_news.setLayoutManager(layoutManager);
        rv_news.setAdapter(adapter);
    }

    public void searchNews(String q){
        NewsInterface newsInterface = ApiClient.getRetrofitInstance().create(NewsInterface.class);

        Call<NewsResponse> call = newsInterface.search_news(q,"4ddafbcf-b39a-427e-b1fe-d5c661b731bf");

        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {
                int status = response.code();
                if(status == HttpURLConnection.HTTP_OK){
                    if( !newsData.isEmpty()){
                        adapter.clear();
                        setAdapter();
                    }

                    NewsResponse newsResponse = response.body();

                    assert newsResponse != null;
                    Log.d("News", new Gson().toJson(newsResponse));
                    newsData.addAll(newsResponse.getResponse().getResult());
                    adapter.notifyItemRangeInserted(0, newsData.size());

                }
            }

            @Override
            public void onFailure(@NonNull Call<NewsResponse> call, @NonNull Throwable throwable) {
                throwable.printStackTrace();
                Toast.makeText(mCon, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}