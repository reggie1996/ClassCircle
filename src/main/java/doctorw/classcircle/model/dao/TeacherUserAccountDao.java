package doctorw.classcircle.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import doctorw.classcircle.model.bean.ParentInfo;
import doctorw.classcircle.model.bean.TeacherInfo;
import doctorw.classcircle.model.db.TeacherUserAccountDB;

/**
 * Created by asus on 2017/4/21.
 */

public class TeacherUserAccountDao {

    private final TeacherUserAccountDB mHelper;

    public TeacherUserAccountDao(Context context) {
        mHelper = new TeacherUserAccountDB(context);
    }

    public void addTeacherAccount(TeacherInfo user) {
        //获取数据库对象
        SQLiteDatabase teacherdb = mHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(TeacherUserAccountTable.COL_PHONE, user.getPhone());
        values.put(TeacherUserAccountTable.COL_PASSWORD, user.getPassword());
        values.put(TeacherUserAccountTable.COL_TEACHERNAME, user.getTeacherName());
        values.put(TeacherUserAccountTable.COL_TEACHERNO, user.getTeacherNo());
        values.put(TeacherUserAccountTable.COL_HEADPIC, user.getHeadPic());
        values.put(TeacherUserAccountTable.COL_TEACHERSEX, user.getTeacherSex());
        values.put(TeacherUserAccountTable.COL_TEACHERROLE, user.getTeacherRole());
        values.put(TeacherUserAccountTable.COL_TEACHERCOURSE, user.getTeachCourse());
        values.put(TeacherUserAccountTable.COL_CLASSNO, user.getClassNo());
        values.put(TeacherUserAccountTable.COL_COVERPIC, user.getCoverPic());
        values.put(TeacherUserAccountTable.COL_IDENTITYPIC, user.getIdentityPic());
        teacherdb.replace(TeacherUserAccountTable.TAB_NAME, null, values);
    }

    public TeacherInfo getTeachertUserAccount(String phone) {
        //获取数据库对象
        SQLiteDatabase parentDb = mHelper.getReadableDatabase();
        //执行查询语句
        String sql = "select * from " + TeacherUserAccountTable.TAB_NAME + " where " + TeacherUserAccountTable.COL_PHONE + "=?";

        Cursor cursor = parentDb.rawQuery(sql, new String[]{phone});
        TeacherInfo teacherInfo = null;
        if(cursor.moveToNext()){
            teacherInfo = new TeacherInfo();
            teacherInfo.setPhone(cursor.getString(cursor.getColumnIndex(TeacherUserAccountTable.COL_PHONE)));
            teacherInfo.setPassword(cursor.getString(cursor.getColumnIndex(TeacherUserAccountTable.COL_PASSWORD)));
            teacherInfo.setTeacherName(cursor.getString(cursor.getColumnIndex(TeacherUserAccountTable.COL_TEACHERNAME)));
            teacherInfo.setTeacherNo(cursor.getString(cursor.getColumnIndex(TeacherUserAccountTable.COL_TEACHERNO)));
            teacherInfo.setTeacherSex(cursor.getString(cursor.getColumnIndex(TeacherUserAccountTable.COL_TEACHERSEX)));
            teacherInfo.setTeacherRole(cursor.getString(cursor.getColumnIndex(TeacherUserAccountTable.COL_TEACHERROLE)));
            teacherInfo.setTeachCourse(cursor.getString(cursor.getColumnIndex(TeacherUserAccountTable.COL_TEACHERCOURSE)));
            teacherInfo.setClassNo(cursor.getString(cursor.getColumnIndex(TeacherUserAccountTable.COL_CLASSNO)));
            teacherInfo.setCoverPic(cursor.getString(cursor.getColumnIndex(TeacherUserAccountTable.COL_COVERPIC)));
            teacherInfo.setIdentityPic(cursor.getString(cursor.getColumnIndex(TeacherUserAccountTable.COL_IDENTITYPIC)));
            teacherInfo.setHeadPic(cursor.getString(cursor.getColumnIndex(TeacherUserAccountTable.COL_HEADPIC)));
        }
        //关闭资源
        cursor.close();
        //返回数据
        return teacherInfo;
    }
}
