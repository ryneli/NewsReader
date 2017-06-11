package com.zqli.newsreader.repository;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.util.Log;

import com.zqli.newsreader.model.AppDatabase;

import javax.inject.Scope;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhenqiangli on 6/11/17.
 */

@Module
public class NewsModule {
    @Provides
    NewsProvider provideNewsProvider() {
        Log.d("Dagger", "provideNewsProvider: ");
        return new NewsProviderImpl();
    }

    @Provides
    AppDatabase getAppDatabase(Context context) {
        return Room.databaseBuilder(context,
                AppDatabase.class, "database-name").build();
    }
}
