package doctorw.classcircle.utils;

/**
 * Created by asus on 2017/4/10.
 */

public class Urls {
    public static final String SERVER = "http://loger.iask.in/classcircle/";
    //    public static final String SERVER = "http://192.168.1.121:8080/OkHttpUtils/";
    public static final String URL_TREGIS = SERVER + "tregister.do";
    public static final String URL_PREGIS = SERVER + "pregister.do";
    public static final String URL_CREATECLASS = SERVER + "addClass.do";

    public static final String URL_LOGIN = SERVER + "login.do";  //登录
    public static final String URL_HEADPIC = SERVER + "headPic.do"; //上传用户头像

    public static final String URL_GETTHEME = SERVER + "listNewsType.do"; //获得标题新闻列表
    public static final String URL_LOADART = SERVER + "appGetNews.do";   //根据标题获得文章
    public static final String URL_LOADARTCOMMENT = SERVER + "listCommentBuNewsId.do";   //根据newsId获得评论
    public static final String URL_POSTARTCOMMENT = SERVER + "addNewsComment.do";   //发送评论
    public static final String URL_ARTFACOR = SERVER + "newsFavor.do";   //记录点赞
    public static final String URL_SCHOUCANGART = SERVER + "listNewsCollection.do";   //获得收藏
    public static final String URL_ARTCOLLECTION = SERVER + "newsCollection.do";   //获得收藏
    public static final String URL_DELETEART = SERVER + "delNewsCollection.do";   //获得收藏


    public static final String URL_ADDDONGTAI = SERVER + "addclassinfo.do"; //添加动态
    public static final String URL_LOADCLASSDT = SERVER + "listClassCircle.do";//根据班级圈ID获得动态
    public static final String URL_LOADSELFDT = SERVER + "listClassInfoByNo.do";//根据用户ID获取动态
    public static final String URL_DELDT = SERVER + "delClassInfo.do";//根据动态编号删除动态infoId
    public static final String URL_COMMENT = SERVER + "addClassInfoComment.do";//评论
    public static final String URL_DELCOMMENT = SERVER + "delClassInfoComment.do";//删除动态
    public static final String URL_DELDIANZAN = SERVER + "delZan.do";//删除点赞
    public static final String URL_DIANZAN = SERVER + "dianzan.do";//删除点赞
    public static final String URL_SHOWGRADE = SERVER + "showGrade.do";//获得分数
    public static final String URL_ALBUM = SERVER + "listAlbum.do";//获得分数

    //通知相关delClassInfoComment
    public static final String URL_GETNOTI = SERVER + "listActivity.do";//根据群ID获取通知
    public static final String URL_DELNOTI = SERVER + "delActivity.do";//删除通知
    public static final String URL_PUBLISHNOTI = SERVER + "publishActivity.do";//发出通知

    //兴趣圈相关
    public static final String URL_ADDINTERESTINFO = SERVER + "addInterestInfo.do";//发班级圈信息
    public static final String URL_CREATEINTEREST = SERVER + "addInterestCircle.do";//新建兴趣圈
    public static final String URL_SETINTERESTINFO = SERVER + "setInterestInfo.do";//修改班级圈信息
    public static final String URL_SEARCHINTEREST = SERVER + "searchInterest.do";//搜索兴趣圈信息
    public static final String URL_MYINTEREST = SERVER + "listMyInterest.do";//搜索兴趣圈信息
    public static final String URL_GETCIRCLEBYID = SERVER + "getCircleDetailById.do";//搜索兴趣圈信息

    public static final String URL_RECOMMEND = SERVER + "recommend.do";//推荐兴趣圈
    public static final String URL_JOININTEREST = SERVER + "joinInterestCircle.do";//加入兴趣圈
    public static final String URL_LISTINTERESTCIRCLE = SERVER + "listInterestInfo.do";//退出兴趣圈、
    public static final String URL_DELETECIRCLEDT = SERVER + "appDelInterestInfoByInfoId.do";//删除动态
    public static final String URL_INTERESTCOMMENT = SERVER + "addInterestInfoComment.do";//评论
    public static final String URL_INTERESTLOVE = SERVER + "interestInfoFavor.do";//点赞、
    public static final String URL_DDELETEZAN = SERVER + "delInterestZan.do";//删除点赞
    public static final String URL_GETHOTDT = SERVER + "getHotDongTai.do";//删除点赞

}
