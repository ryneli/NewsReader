package com.zqli.newsreader.Data;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by zhenqiangli on 6/8/17.
 */

interface HackerNewsServiceInterface {
    @GET("item/{itemid}.json?print=pretty")
    Call<HackerNewsRawItem> getNewsItem(@Path("itemid") String itemid);

    @GET("topstories.json?print=pretty")
    Call<String[]> getTopStories();
}
