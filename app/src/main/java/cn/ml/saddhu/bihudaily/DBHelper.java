package cn.ml.saddhu.bihudaily;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import cn.ml.saddhu.bihudaily.engine.domain.DaoMaster;
import cn.ml.saddhu.bihudaily.engine.domain.DaoSession;

/**
 * Created by sadhu on 2016/11/13.
 * Email static.sadhu@gmail.com
 * Describe: 数据库帮助类
 */
public class DBHelper {


    private static DBHelper mInstance;
    private DaoSession mDaoSession;

    private DBHelper() {

    }

    public static DBHelper getInstance() {
        if (mInstance == null) {
            synchronized (DBHelper.class) {
                if (mInstance == null) {
                    mInstance = new DBHelper();
                }
            }
        }
        return mInstance;
    }

    public void init(Context ctx) {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(ctx, "BIHU_db", null);
        SQLiteDatabase db = devOpenHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }


}
