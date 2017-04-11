package com.dragon.alphaorder.utils;


import com.dragon.alphaorder.application.MyApplication;
import com.dragon.alphaorder.cache.ACache;

/**
 * Created by Administrator on 2017/2/19.
 */

public class CacheUtil {
    private static ACache myCache;

    public static ACache getACache() {
        synchronized (CacheUtil.class) {
            if (myCache == null) {
                myCache = ACache.get(MyApplication.getContext());

            }
        }
        return myCache;
    }
}
