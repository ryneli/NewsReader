package com.zqli.newsreader.repository;

import com.zqli.newsreader.model.News;

import javax.inject.Singleton;

/**
 * Created by zhenqiangli on 6/11/17.
 */

public interface NewsProvider {

    News getNews(int id);
    int[] getNewsIds();
    void deleteNews(int id);
}
