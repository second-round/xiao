package com.example.homework.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.example.homework.greendao.DaoMaster;
import com.example.homework.greendao.DaoSession;


public class GreendaoUtils {
    private static GreendaoUtils mInstance;
    private GreendaoUtils(){

    }

    /**
     * 双重检索锁
     * @return
     */
    public static GreendaoUtils getInstance(){

        if (mInstance==null){
            synchronized (GreendaoUtils.class){
                if (mInstance==null){

                    mInstance = new GreendaoUtils();

                }
            }
        }

        return mInstance;

    }

    /**
     * 初始化GreenDao,直接在Application中进行初始化操作
     */
    public void initGreenDao(Context context) {
        //创建daomaster
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context,"mall.db");
        SQLiteDatabase db = helper.getWritableDatabase();//db 读写数据库
        DaoMaster daoMaster = new DaoMaster(db);//创建对象

        //创建daosession
        daoSession = daoMaster.newSession();
    }

    private DaoSession daoSession;
    public DaoSession getDaoSession() {
        return daoSession;
    }

}
