package doctorw.classcircle.model.dao;

import doctorw.classcircle.model.bean.ParentInfo;

/**
 *
 * private String phone;
 private String password;
 private String parentName;
 private String parentNo;
 private String headPic;
 private String childNo;
 private String childName;
 private String childSex;
 private String classNo;
 private String parentSex;
 private String coverPic;
 * 创建父母用户表
 * Created by asus on 2017/4/12.
 */

public class ParentUserAccountTable {
    public  static  final String TAB_NAME = "tab_parentuser";

    public static  final  String COL_PHONE = "phone";
    public static  final  String COL_PASSWORD = "password";
    public static  final  String COL_PARENTNAME = "parentName";
    public static  final  String COL_PARENTNO = "parentNo";
    public static  final  String COL_HEADPIC = "headPic";
    public static  final  String COL_CHILDNO = "childNo";
    public static  final  String COL_CHILDNAME = "childName";
    public static  final  String COL_CHILDSEX = "childSex";
    public static  final  String COL_CLASSNO = "classNo";
    public static  final  String COL_PARENTSEX = "parentSex";
    public static  final  String COL_COVERPIC = "coverPic";

    public static final String CREARE_TAB = "create table "
            +TAB_NAME + " ("
            +COL_PHONE + " text primary key,"
            +COL_PASSWORD + " text,"
            +COL_PARENTNAME + " text,"
            +COL_PARENTNO + " text,"
            +COL_HEADPIC + " text,"
            +COL_CHILDNO + " text,"
            +COL_CHILDNAME + " text,"
            +COL_CHILDSEX + " text,"
            +COL_CLASSNO + " text,"
            +COL_PARENTSEX + " text,"
            +COL_COVERPIC + " text);";
}
