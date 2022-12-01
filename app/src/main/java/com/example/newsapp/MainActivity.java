package com.example.newsapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

    public class MainActivity extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<List<News>> {
        private static final String LOG_TAG = MainActivity.class.getName();
        private static final String GUARDIAN_REQUEST_URL =
                "https://content.guardianapis.com/search?";
        private static final int NEWS_LOADER_ID = 1;
        private NewsAdapter adapter;
        private TextView emptyStateTextView;

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            ListView newsListView = (ListView) findViewById(R.id.list_view);

            emptyStateTextView = (TextView) findViewById(R.id.empty_view);
            newsListView.setEmptyView(emptyStateTextView);

            adapter = new NewsAdapter(this, new ArrayList<News>());

            newsListView.setAdapter(adapter);

            newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    News currentNews = adapter.getItem(position);
                    Uri newsUri = Uri.parse(currentNews.getUrl());
                    Intent i = new Intent(Intent.ACTION_VIEW, newsUri);
                    startActivity(i);
                }
            });

            // Get a reference to the ConnectivityManager to check state of network connectivity
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);

            // Get details on the currently active default data network
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            // If there is a network connection, fetch data
            if (networkInfo != null && networkInfo.isConnected()) {
                // Get a reference to the LoaderManager, in order to interact with loaders.
                android.app.LoaderManager loaderManager = getLoaderManager();

                // Initialize the loader. Pass in the int ID constant defined above and pass in null for
                // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
                // because this activity implements the LoaderCallbacks interface).
                loaderManager.initLoader(NEWS_LOADER_ID, null, this);
            } else {
                // Update empty state with no connection error message
                emptyStateTextView.setText(R.string.bad_network_text);
            }
        }

        @NonNull
        @Override
        public Loader<List<News>> onCreateLoader(int id, Bundle bundle) {
            Uri baseUri = Uri.parse(GUARDIAN_REQUEST_URL);
            Uri.Builder uriBuilder = baseUri.buildUpon();
            uriBuilder.appendQueryParameter("q", "technology");
            uriBuilder.appendQueryParameter("api-key", "2348cf64-eacb-42a6-8f1d-77e95afa0f6c");
            uriBuilder.appendQueryParameter("show-tags", "contributor"); //to fetch authors



            return new NewsLoader(this, uriBuilder.toString());
        }

        @Override
        public void onLoadFinished(android.content.Loader<List<News>> loader, List<News> news) {

            if (news != null && !news.isEmpty()) {
                adapter.addAll();
            } else {
                emptyStateTextView.setText(R.string.empty_view_text);
            }
        }

        @Override
        public void onLoaderReset(android.content.Loader<List<News>> loader) {
            adapter.clear();
        }
    }