package com.zqli.newsreader.Data;

import java.util.List;

/**
 * Created by zhenqiangli on 6/8/17.
 */

public class HackerNewsRawItem {
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
