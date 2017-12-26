package doctorw.classcircle.model;

import android.content.Context;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import doctorw.classcircle.model.bean.UserInfo;
import doctorw.classcircle.model.dao.ParentUserAccountDao;
import doctorw.classcircle.model.dao.TeacherUserAccountDao;
import doctorw.classcircle.model.dao.UserAccountDao;
import doctorw.classcircle.model.db.DBManager;

/**
 * Created by asus on 2017/4/9.
 */

//数据模型层的全局类
public class Model {

    //创建对象
    private static Model model = new Model();
    private Context mContext;
    private DBManager dbManager;

    private  ParentUserAccountDao parentUserAccountDao;
    private ExecutorService executors = Executors.newCachedThreadPool();
    private TeacherUserAccountDao teacherUserAccountDao;
    private EventListener eventListener;
    private UserAccountDao userAccountDao;


    //私有化构造
    private Model() {

    }

    //获取单例对象
    public  static  Model getInstance(){
        return  model;
    }

    //初始化方法
    public  void  init(Context context){
        mContext = context;

        // 创建用户账号数据库的操作类对象
        userAccountDao = new UserAccountDao(mContext);
        teacherUserAccountDao = new TeacherUserAccountDao(mContext);
        //创建用户账号数据库的操作类对象
         parentUserAccountDao = new ParentUserAccountDao(mContext);
        //初始化全局监听
        eventListener = new EventListener(mContext);

    }
    //获取全局线程池对象
    public ExecutorService getGlobalThreadPool(){
        return executors;
    }

    //用户登录成功后 对模型层做的处理
    public void loginSuccess(UserInfo account) {
        //将用户信息保存到数据库
        // 校验
        if(account == null) {
            return;
        }
        Log.e("test",account.toString());
        if(dbManager != null) {
            dbManager.close();
        }
        dbManager = new DBManager(mContext, account.getName());
    }

    public DBManager getDbManager(){
        return dbManager;
    }
    //数据库操作类
    public ParentUserAccountDao getParentUserAccountDao(){
        return parentUserAccountDao;
    }

    // 获取用户账号数据库的操作类对象
    public UserAccountDao getUserAccountDao(){
        return userAccountDao;
    }
    //
    public TeacherUserAccountDao getTeacherUserAccountDao() {
        return teacherUserAccountDao;
    }
}
