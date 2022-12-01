package com.example.newsapp;


import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    //Tag for log messages
    private static final String LOG_TAG = NewsLoader.class.getName();
    private String url;

    public NewsLoader(Context context, String mUrl) {
        super(context);
        url = mUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        if (url == null) {
            return null;
        }
        return QueryUtils.fetchNewsData(url);
    }
}
