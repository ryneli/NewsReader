package com.zqli.newsreader.repository;

import com.zqli.newsreader.MainActivity;

import dagger.Component;

/**
 * Created by zhenqiangli on 6/11/17.
 */

@Component(modules = NewsModule.class)
public interface NewsComponent {
    void inject(MainActivity mainActivity);
}
