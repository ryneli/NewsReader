package com.zqli.newsreader.repository;

import com.zqli.newsreader.repository.local.News;

/**
 * Created by zhenqiangli on 6/11/17.
 */

public class NewsProviderImpl implements NewsProvider {
    @Override
    public News getNews(int id) {
        return null;
    }

    @Override
    public int[] getNewsIds() {
        return new int[0];
    }

    @Override
    public void deleteNews(int id) {

    }
}
