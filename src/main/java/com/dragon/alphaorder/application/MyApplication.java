package com.dragon.alphaorder.application;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.dragon.alphaorder.entity.Dishes;
import com.dragon.alphaorder.greendao.DaoMaster;
import com.dragon.alphaorder.greendao.DaoSession;
import com.dragon.alphaorder.greendao.DishesDao;
import com.dragon.alphaorder.utils.CacheUtil;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/19.
 */

public class MyApplication extends Application {
    private static Context context;
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        instance = this;
        initDatabase();
        if (TextUtils.isEmpty(CacheUtil.getACache().getAsString("isFirst"))) {
            initDishes();
        }
    }

    //初始化dishes数据到数据库
    private void initDishes() {
        saveToDB(getAllDishesList(loadDishesJson()));
        CacheUtil.getACache().put("isFirst", "no");
    }

    //读取assets的json数据为string
    private String loadDishesJson() {
        try {
            InputStream is = getAssets().open("dishes.json");
            byte[] buffer = new byte[2048];
            int readBytes = 0;
            StringBuilder stringBuilder = new StringBuilder();
            while ((readBytes = is.read(buffer)) > 0) {
                stringBuilder.append(new String(buffer, 0, readBytes));
            }
            //            Logger.d(stringBuilder.toString());
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    //通过jsonarray解析Dishes List
    public List<Dishes> getAllDishesList(String jsonString) {
        List<Dishes> dishesList = new ArrayList<Dishes>();
        dishesList = JSON.parseArray(jsonString, Dishes.class);
        Logger.d(dishesList.size());
        return dishesList;
    }

    //Dishes数据存入数据库
    public void saveToDB(List<Dishes> dishes) {
        DishesDao dao = MyApplication.getInstance().getDaoSession().getDishesDao();
        dao.insertInTx(dishes);
    }

    public static Context getContext() {
        return context;
    }

    public static MyApplication getInstance() {
        return instance;
    }

    /* *
      * 设置greenDao
      */
    private void initDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(this, "alpha-db", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }
}
