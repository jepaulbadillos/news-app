package com.example.newsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.model.News;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private final ArrayList<News> feedData;

    public NewsAdapter(ArrayList<News> feedData) {
        this.feedData = feedData;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ((ViewHolder) holder).tv_title.setText(feedData.get(position).getTitle());
        ((ViewHolder) holder).tv_url.setText(feedData.get(position).getLink());
        LocalDateTime dateTime;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            dateTime = LocalDateTime.parse(feedData.get(position).getPublication_date(), DateTimeFormatter.ISO_DATE_TIME);
            String formattedDateTime = dateTime.format(DateTimeFormatter.ofPattern("MMM. d, yyyy - h:mma"));

            formattedDateTime = formattedDateTime.replace("AM", "am").replace("PM", "pm");
            ((ViewHolder) holder).tv_pub_date.setText(formattedDateTime);
        }else{
            ((ViewHolder) holder).tv_pub_date.setText(feedData.get(position).getPublication_date());
        }
    }

    @Override
    public int getItemCount() {
        return feedData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_title, tv_url, tv_pub_date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            this.tv_url = (TextView) itemView.findViewById(R.id.tv_link);
            this.tv_pub_date = (TextView) itemView.findViewById(R.id.pub_date);

        }
    }

    public void clear() {
        int size = feedData.size();
        if (size > 0) {
            feedData.subList(0, size).clear();
            notifyItemRangeRemoved(0, size);
        }
    }
}
