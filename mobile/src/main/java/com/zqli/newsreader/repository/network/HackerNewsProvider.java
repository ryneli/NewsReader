package com.zqli.newsreader.repository.network;

import android.util.Log;

import com.google.gson.Gson;
import com.zqli.newsreader.retrofit.DaggerRetrofitCompoment;
import com.zqli.newsreader.retrofit.RetrofitModule;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by zhenqiangli on 6/7/17.
 */

public class HackerNewsProvider {
    private static final String TAG = "HackerNewsProvider";
    private static final String BASE_URL = "https://hacker-news.firebaseio.com/v0/";
    @Inject Gson gson;
    @Inject Retrofit retrofit;
    private HackerNewsServiceInterface hackerNewsService;

    public HackerNewsProvider() {
        DaggerRetrofitCompoment.builder()
                .retrofitModule(new RetrofitModule(BASE_URL))
                .build().Inject(this);
        hackerNewsService = retrofit.create(HackerNewsServiceInterface.class);
    }

    public static class HackerNewsEvent {
        String title;
        String[] topStoryList;
        HackerNewsEvent(String title) {
            this.title = title;
        }
        HackerNewsEvent(String[] newsList) {
            this.topStoryList = newsList;
        }
        public String getTitle() {
            return title;
        }
        public String[] getTopStoryList() {return topStoryList;}
    }

    public void getItemAsync(final String id) {
        Call<HackerNewsRawItem> call = hackerNewsService.getNewsItem(id);
        call.enqueue(new Callback<HackerNewsRawItem>() {
            @Override
            public void onResponse(Call<HackerNewsRawItem> call, Response<HackerNewsRawItem> response) {
                int statusCode = response.code();
                HackerNewsRawItem item = response.body();
                EventBus.getDefault().post(new HackerNewsEvent(item.title));
                Log.d(TAG, "onResponse: " + item);
            }

            @Override
            public void onFailure(Call<HackerNewsRawItem> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
                t.printStackTrace();
            }
        });
    }

    public void getTopStoriesAsync() {
        Call<String[]> call = hackerNewsService.getTopStories();
        call.enqueue(new Callback<String[]>() {
            @Override
            public void onResponse(Call<String[]> call, Response<String[]> response) {
                Log.d(TAG, "onResponse: " + response.body().length);
                EventBus.getDefault().post(new HackerNewsEvent(response.body()));
            }

            @Override
            public void onFailure(Call<String[]> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
                t.printStackTrace();
            }
        });
    }
}
