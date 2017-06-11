package com.zqli.newsreader.repository;

import com.zqli.newsreader.repository.local.News;

/**
 * Created by zhenqiangli on 6/11/17.
 */

public interface NewsProvider {

    News getNews(int id);
    int[] getNewsIds();
    void deleteNews(int id);
}
