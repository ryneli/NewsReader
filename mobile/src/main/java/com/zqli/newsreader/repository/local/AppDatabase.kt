package com.zqli.newsreader.repository.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

/**
 * Created by zhenqiangli on 6/11/17.
 */

@Database(entities = arrayOf(News::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}

/*
* Original java code
@Database(entities = {News.class}, version = 1)
public abstract class AppDataBase extends Database {
    public abstract NewsDao newsDao();
}
*/
