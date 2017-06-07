package com.zqli.newsreader;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by zhenqiangli on 6/7/17.
 */

public class HackerNewsProvider {
    private static final String BASE_URL = "https://hacker-news.firebaseio.com/v0/";
    private static final String TAG = "HackerNewsProvider";
    public class Item {
        String title = "";
        String content = "";
    }

    private class RawItem {
        public String by;
        public int descendants;
        public int id;
        public List<Integer> kids = null;
        public int score;
        public int time;
        public String title;
        public String type;
        public String url;

        @Override
        public String toString() {
            return String.format("%s %s %s %s", id, by, score, title);
        }
    }

    /*
    * Generated from http://www.jsonschema2pojo.org/
    * */
    private interface HackerNewsService {
        @GET("item/{itemid}.json?print=pretty")
        Call<RawItem> getNewsItem(@Path("itemid") String itemid);
    }

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create();

    HackerNewsProvider() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        HackerNewsService hackerNewsService = retrofit.create(HackerNewsService.class);
        Call<RawItem> call = hackerNewsService.getNewsItem("8863");
        call.enqueue(new Callback<RawItem>() {
            @Override
            public void onResponse(Call<RawItem> call, Response<RawItem> response) {
                int statusCode = response.code();
                RawItem item = response.body();
                Log.d(TAG, "onResponse: " + item);
            }

            @Override
            public void onFailure(Call<RawItem> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
                t.printStackTrace();
            }
        });
    }
}
