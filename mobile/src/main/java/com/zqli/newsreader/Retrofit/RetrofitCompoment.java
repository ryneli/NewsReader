package com.zqli.newsreader.Retrofit;

import com.zqli.newsreader.Data.HackerNewsProvider;

import dagger.Component;

/**
 * Created by zhenqiangli on 6/11/17.
 */

@Component(modules = RetrofitModule.class)
public interface RetrofitCompoment {
    void Inject(HackerNewsProvider provider);
}
