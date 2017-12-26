package doctorw.classcircle.model.http;

import android.content.Context;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;

import java.util.HashMap;
import java.util.List;

import doctorw.classcircle.model.bean.ArticleTheme;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by asus on 2017/4/25.
 */

public  class Datas {

    private static List<ArticleTheme> themes;

    public interface HandleResponse {
        void handleResponse(String s, Call call, Response response);
    }

    //获得班级圈动态
    public static  void loadClassDongtai(String url, final Context mContext, final HashMap<String, String>  params, final HandleResponse handleResponse){
        OkGo.get(url)
               .tag(mContext)
                .cacheKey("cacheKey")
                .params(params)
                .cacheMode(CacheMode.DEFAULT)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        handleResponse.handleResponse(s,call,response);
                    }
                });

    }


    public static  void loadSelfNotis(String url, final Context mContext, final HashMap<String, String>  params, final HandleResponse handleResponse){
        OkGo.get(url)
                .tag(mContext)
                .cacheKey("cacheKey")
                .params(params)
                .cacheMode(CacheMode.DEFAULT)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        handleResponse.handleResponse(s,call,response);
                    }
                });

    }

 public static  void loadNotis(String url, final Context mContext, final HashMap<String, String>  params, final HandleResponse handleResponse){
        OkGo.get(url)
               .tag(mContext)
                .cacheKey("cacheKey")
                .params(params)
                .cacheMode(CacheMode.DEFAULT)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        handleResponse.handleResponse(s,call,response);
                    }
                });

    }




    //获得文章主题列表
    public static void getDatas(String url, final Context mContext, final HandleResponse handleResponse) {
        OkGo.get(url)
                .tag(mContext)
                .cacheKey("cacheKey")
                .cacheMode(CacheMode.DEFAULT)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        handleResponse.handleResponse(s,call,response);
                    }
                });
    }
    public interface OnGetDataResult {
        void getResult(Object object);
    }


    public static List<ArticleTheme> getThemes(String url,final Context mContext, final OnGetDataResult getResult) {
        OkGo.get(url)     // 请求方式和请求url
                .tag(mContext)                       // 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                       themes = JSON.parseArray(s, ArticleTheme.class);
                        Toast.makeText(mContext, s.toString(), Toast.LENGTH_SHORT).show();
                        if(getResult!=null){
                            getResult.getResult(themes);
                        }
                    }
                });
        return themes;
    }


}
