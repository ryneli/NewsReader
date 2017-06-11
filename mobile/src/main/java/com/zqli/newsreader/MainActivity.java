package com.zqli.newsreader;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zqli.newsreader.repository.network.HackerNewsProvider;
import com.zqli.newsreader.repository.DaggerNewsComponent;
import com.zqli.newsreader.repository.NewsProvider;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.news_list) RecyclerView newsList;
    HackerNewsProvider hackerNewsProvider;
    NewsListAdapter adapter;
    ArrayList<String> topStoryList;
    String[] topStoryIdList;
    @Inject NewsProvider newsProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        topStoryList = new ArrayList<String>();
        topStoryIdList = new String[0];
        adapter = new NewsListAdapter();
        hackerNewsProvider = new HackerNewsProvider();
        newsList.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        newsList.setAdapter(adapter);

        DaggerNewsComponent.builder().build().inject(this);
        newsProvider.getNewsIds();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // hackerNewsProvider.getItemAsync("8863");
        if (topStoryIdList.length == 0) {
            hackerNewsProvider.getTopStoriesAsync();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe public void onHackerNewsEvent(HackerNewsProvider.HackerNewsEvent event) {
        if (event.getTopStoryList() != null) {
            topStoryIdList = event.getTopStoryList();
            Log.d(TAG, "onHackerNewsEvent: story list " + topStoryIdList.length);
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    for (String id : topStoryIdList) {
                        hackerNewsProvider.getItemAsync(id);
                    }
                    return null;
                }
            }.execute();
        } else if (event.getTitle() != null) {
            topStoryList.add(event.getTitle());
            adapter.notifyItemInserted(topStoryList.size());
        }
    }

    private class NewsListAdapter extends RecyclerView.Adapter<NewsItemViewHolder> {
        @Override
        public NewsItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_news, parent, false);
            return new NewsItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(NewsItemViewHolder holder, int position) {
            holder.bind(topStoryList.get(position));
        }

        @Override
        public int getItemCount() {
            return topStoryList.size();
        }
    }

    class NewsItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.news_title) TextView title;
        NewsItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(String item) {
            Log.d(TAG, "bind: ");
            title.setText(item);
        }
    }
}
