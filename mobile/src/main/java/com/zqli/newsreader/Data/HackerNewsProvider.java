package com.zqli.newsreader.Data;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zhenqiangli on 6/7/17.
 */

public class HackerNewsProvider {
    private static final String BASE_URL = "https://hacker-news.firebaseio.com/v0/";
    private static final String TAG = "HackerNewsProvider";
    private Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create();
    private HackerNewsServiceInterface hackerNewsService;

    public HackerNewsProvider() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        hackerNewsService = retrofit.create(HackerNewsServiceInterface.class);

    }

    public static class HackerNewsEvent {
        String title;
        HackerNewsEvent(String title) {
            this.title = title;
        }
        public String getTitle() {
            return title;
        }
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
}
