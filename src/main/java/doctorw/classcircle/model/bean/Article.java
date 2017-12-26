package doctorw.classcircle.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by asus on 2017/4/24.
 */

public class Article implements Parcelable {


    private String articleId;
    private String title;
    private String picArt;
    private String weburl;
    private String commentNum;
    private String favorNum;
    private String readNum;

    public Article() {
        this.title = "学习";
        this.picArt = "http://img.zcool.cn/community/01b72057a7e0790000018c1bf4fce0.png";
        this.weburl = "https://zhidao.baidu.com/question/1885817326500180748.html";
        this.commentNum = "0";
        this.favorNum = "0";
        this.readNum = "0";
        this.articleId = "0";
    }

    public Article(String title, String picArt, String weburl) {
        this.title = title;
        this.picArt = picArt;
        this.weburl = weburl;
        this.commentNum = "0";
        this.favorNum = "0";
        this.readNum = "0";
        this.articleId = "0";
    }

    public Article(String articleId, String title, String picArt, String weburl, String commentNum, String favorNum, String readNum) {
        this.articleId = articleId;
        this.title = title;
        this.picArt = picArt;
        this.weburl = weburl;
        this.commentNum = commentNum;
        this.favorNum = favorNum;
        this.readNum = readNum;
    }


    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getReadNum() {
        return readNum;
    }

    public void setReadNum(String readNum) {
        this.readNum = readNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicArt() {
        return picArt;
    }

    public void setPicArt(String picArt) {
        this.picArt = picArt;
    }

    public String getWeburl() {
        return weburl;
    }

    public void setWeburl(String weburl) {
        this.weburl = weburl;
    }

    public String getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(String commentNum) {
        this.commentNum = commentNum;
    }

    public String getFavorNum() {
        return favorNum;
    }

    public void setFavorNum(String favorNum) {
        this.favorNum = favorNum;
    }

    @Override
    public String toString() {
        return "Article{" +
                "articleId='" + articleId + '\'' +
                ", title='" + title + '\'' +
                ", picArt='" + picArt + '\'' +
                ", weburl='" + weburl + '\'' +
                ", commentNum='" + commentNum + '\'' +
                ", favorNum='" + favorNum + '\'' +
                ", readNum='" + readNum + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.articleId);
        dest.writeString(this.title);
        dest.writeString(this.picArt);
        dest.writeString(this.weburl);
        dest.writeString(this.commentNum);
        dest.writeString(this.favorNum);
        dest.writeString(this.readNum);
    }

    protected Article(Parcel in) {
        this.articleId = in.readString();
        this.title = in.readString();
        this.picArt = in.readString();
        this.weburl = in.readString();
        this.commentNum = in.readString();
        this.favorNum = in.readString();
        this.readNum = in.readString();
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel source) {
            return new Article(source);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };
}
