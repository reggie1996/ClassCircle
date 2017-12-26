package doctorw.classcircle.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import doctorw.classcircle.model.bean.ParentInfo;
import doctorw.classcircle.model.db.ParentUserAccountDB;

/**
 * 父母用户数据库表的操作类
 * Created by asus on 2017/4/12.
 */

public class ParentUserAccountDao {

    private final ParentUserAccountDB mHelper;

    public ParentUserAccountDao(Context context) {
        mHelper = new ParentUserAccountDB(context);
    }

    public void addParentAccount(ParentInfo user) {
        //获取数据库对象
        SQLiteDatabase parentdb = mHelper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(ParentUserAccountTable.COL_PHONE, user.getPhone());
        values.put(ParentUserAccountTable.COL_PASSWORD, user.getPassword());
        values.put(ParentUserAccountTable.COL_CHILDNAME, user.getChildName());
        values.put(ParentUserAccountTable.COL_CHILDNO, user.getChildNo());
        values.put(ParentUserAccountTable.COL_CHILDSEX, user.getChildSex());
        values.put(ParentUserAccountTable.COL_CLASSNO, user.getClassNo());
        values.put(ParentUserAccountTable.COL_COVERPIC, user.getCoverPic());
        values.put(ParentUserAccountTable.COL_HEADPIC, user.getHeadPic());
        values.put(ParentUserAccountTable.COL_PARENTNAME, user.getParentName());
        values.put(ParentUserAccountTable.COL_PARENTNO, user.getParentNo());
        values.put(ParentUserAccountTable.COL_PARENTSEX, user.getParentSex());
        parentdb.replace(ParentUserAccountTable.TAB_NAME, null, values);
    }

    public ParentInfo getParentUserAccount(String phone) {
        //获取数据库对象
        SQLiteDatabase parentDb = mHelper.getReadableDatabase();
        //执行查询语句
        String sql = "select * from " + ParentUserAccountTable.TAB_NAME + " where " + ParentUserAccountTable.COL_PHONE + "=?";

        Cursor cursor = parentDb.rawQuery(sql, new String[]{phone});
        ParentInfo parentInfo = null;
        if(cursor.moveToNext()){
            parentInfo = new ParentInfo();
            parentInfo.setPhone(cursor.getString(cursor.getColumnIndex(ParentUserAccountTable.COL_PHONE)));
            parentInfo.setPassword(cursor.getString(cursor.getColumnIndex(ParentUserAccountTable.COL_PASSWORD)));
            parentInfo.setChildName(cursor.getString(cursor.getColumnIndex(ParentUserAccountTable.COL_CHILDNAME)));
            parentInfo.setChildNo(cursor.getString(cursor.getColumnIndex(ParentUserAccountTable.COL_CHILDNO)));
            parentInfo.setChildSex(cursor.getString(cursor.getColumnIndex(ParentUserAccountTable.COL_CHILDSEX)));
            parentInfo.setClassNo(cursor.getString(cursor.getColumnIndex(ParentUserAccountTable.COL_CLASSNO)));
            parentInfo.setCoverPic(cursor.getString(cursor.getColumnIndex(ParentUserAccountTable.COL_COVERPIC)));
            parentInfo.setHeadPic(cursor.getString(cursor.getColumnIndex(ParentUserAccountTable.COL_HEADPIC)));
            parentInfo.setParentName(cursor.getString(cursor.getColumnIndex(ParentUserAccountTable.COL_PARENTNAME)));
            parentInfo.setParentNo(cursor.getString(cursor.getColumnIndex(ParentUserAccountTable.COL_PARENTNO)));
            parentInfo.setParentSex(cursor.getString(cursor.getColumnIndex(ParentUserAccountTable.COL_PARENTSEX)));
        }
        //关闭资源
        cursor.close();
        //返回数据
        return parentInfo;
    }

}
