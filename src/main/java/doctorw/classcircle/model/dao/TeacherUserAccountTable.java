package doctorw.classcircle.model.dao;

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
 * private String phone;
 private String password;
 private String headPic;
 private String teacherName;
 private String teacherNo;
 private String teacherSex;
 private String teacherRole;
 private String teachCourse;
 private String classNo;
 private String coverPic;
 private String identityPic;
 */

public class TeacherUserAccountTable {
    public  static  final String TAB_NAME = "tab_teacheruser";

    public static  final  String COL_PHONE = "phone";
    public static  final  String COL_PASSWORD = "password";
    public static  final  String COL_TEACHERNAME = "teacherName";
    public static  final  String COL_TEACHERNO = "teacherNo";
    public static  final  String COL_HEADPIC = "headPic";
    public static  final  String COL_TEACHERSEX = "teacherSex";
    public static  final  String COL_TEACHERROLE = "teacherRole";
    public static  final  String COL_TEACHERCOURSE = "teachCourse";
    public static  final  String COL_CLASSNO = "classNo";
    public static  final  String COL_COVERPIC = "coverPic";
    public static  final  String COL_IDENTITYPIC = "identityPic";

    public static final String CREARE_TAB = "create table "
            +TAB_NAME + " ("
            +COL_PHONE + " text primary key,"
            +COL_PASSWORD + " text,"
            +COL_TEACHERNAME + " text,"
            +COL_TEACHERNO + " text,"
            +COL_HEADPIC + " text,"
            +COL_TEACHERSEX + " text,"
            +COL_TEACHERROLE + " text,"
            +COL_TEACHERCOURSE + " text,"
            +COL_CLASSNO + " text,"
            +COL_IDENTITYPIC + " text,"
            +COL_COVERPIC + " text);";
}
