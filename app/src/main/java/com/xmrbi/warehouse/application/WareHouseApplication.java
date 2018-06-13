package com.xmrbi.warehouse.application;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.support.multidex.MultiDex;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.xmrbi.warehouse.base.Config;
import com.xmrbi.warehouse.component.greendao.DaoMaster;
import com.xmrbi.warehouse.component.greendao.DaoSession;
import com.xmrbi.warehouse.utils.ImageLoader;



/**
 * Created by wzn on 2018/3/29.
 */

public class WareHouseApplication extends Application {

    private static WareHouseApplication instances;
    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        instances = this;
        //初始化utils
        initUtils();
        initDb();
        //讯飞语音在线合成
//        SpeechUtility.createUtility(this, SpeechConstant.APPID+"=" + SPEECHCONSTANT_APPID);

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //分包
        MultiDex.install(this);
    }

    private void initUtils() {
        Utils.init(this);
        //log
        LogUtils.getConfig().setLogSwitch(Config.IS_OPEN_LOG);
    }

    /**
     * 初始化数据库
     */
    private void initDb() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        DaoMaster.DevOpenHelper mHelper = new DaoMaster.DevOpenHelper(this, Config.DB.DB_NAME, null);
        SQLiteDatabase db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        DaoMaster mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    /**
     * 获取数据库的session
     *
     * @return
     */
    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public static WareHouseApplication getInstances() {
        return instances;
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
        ImageLoader.GuideClearMemory(this);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        ImageLoader.GuideClearMemory(this);
    }
}
