package doctorw.classcircle.model.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import doctorw.classcircle.model.dao.ParentUserAccountTable;

/**
 * Created by asus on 2017/4/12.
 */

public class ParentUserAccountDB extends SQLiteOpenHelper {

    public ParentUserAccountDB(Context context) {
        super(context, "parentuser.db", null, 1);
    }

    //数据库创建的时候调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据库表
        db.execSQL(ParentUserAccountTable.CREARE_TAB);

    }

    //数据库更新的时候调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
