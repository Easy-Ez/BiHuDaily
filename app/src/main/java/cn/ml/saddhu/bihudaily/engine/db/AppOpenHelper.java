package cn.ml.saddhu.bihudaily.engine.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.greenrobot.greendao.database.Database;

import cn.ml.saddhu.bihudaily.engine.domain.DaoMaster;
import cn.ml.saddhu.bihudaily.engine.domain.ReadHistoryDao;
import cn.ml.saddhu.bihudaily.engine.domain.SectionDao;
import cn.ml.saddhu.bihudaily.engine.domain.StoryDao;
import cn.ml.saddhu.bihudaily.engine.domain.StoryDetailDao;

/**
 * Created by sadhu on 2017/5/16.
 *
 * @see cn.ml.saddhu.bihudaily.engine.domain.DaoMaster.OpenHelper will drop all table
 */
public class AppOpenHelper extends DaoMaster.OpenHelper {
    public AppOpenHelper(Context context, String name) {
        super(context, name);
    }

    public AppOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        //  ClassesDao.createTable(db, ifNotExists); 创建新表
        //  alterColumn  插入新列
        //  tips: 这里中间不要break
        Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
        switch (oldVersion) {
            case 2:
                StoryDetailDao.createTable(db, true);
                SectionDao.createTable(db, true);
            case 3:
                alterColumn(db, StoryDao.TABLENAME, StoryDao.Properties.SectionId.columnName);
            case 4:
                ReadHistoryDao.createTable(db, true);
                alterColumn(db, StoryDao.TABLENAME, StoryDao.Properties.IsRead.columnName);
                break;

        }
    }

    private void alterColumn(Database db, String tableName, String... columnName) {
        //"ALTER TABLE mytable ADD dura TEXT";
        StringBuilder sb = new StringBuilder();
        for (String s : columnName) {
            sb.setLength(0);
            sb.append("ALTER TABLE ");
            sb.append(tableName);
            sb.append(" ADD ");
            sb.append(s);
            sb.append(" TEXT ;");
            Log.i("DaoMaster", "alterColumn: " + sb.toString());
            db.execSQL(sb.toString());
        }
    }
}
