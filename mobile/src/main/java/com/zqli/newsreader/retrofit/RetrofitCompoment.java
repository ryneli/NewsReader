package com.zqli.newsreader.retrofit;

import com.zqli.newsreader.repository.network.HackerNewsProvider;

import dagger.Component;

/**
 * Created by zhenqiangli on 6/11/17.
 */

@Component(modules = RetrofitModule.class)
public interface RetrofitCompoment {
    void Inject(HackerNewsProvider provider);
}
