package doctorw.classcircle.model.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *  Created by asus on 2017/4/28.
 *  (1) 用户id
 * （2）动态id
 * （3）班class级圈id
 * () 用户头像
 * （4）时间 createtime
 * （5）说说内容 content
 * （6）照片集 List<PhotoInfo> photos;
 * （7）用户点赞名单 private List<FavortItem> favorters;
   （8）用户评论名单 private List<CommentItem> comments;
 * （9）录音 vedioUrl
 * （10）视频 movieUrl
 * （）
 */

public class DongTai implements Parcelable {
    private String userId;
    private String userName;
    private String headUrl;
    private String dongtaiId;
    private String classId;
    private String createTime;
    private String content;
    private List<String> photos;
    private List<FavortItem> favorters;
    private List<CommentItem> comments;
    private String videoUrl;
    private String movieUrl;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public DongTai() {
    }


    public DongTai(String userId, String userName,String headUrl, String dongtaiId, String classId, String createTime, String content, List<String> photos, List<FavortItem> favorters, List<CommentItem> comments, String videoUrl, String movieUrl) {
        this.userId = userId;
        this.userName = userName;
        this.headUrl = headUrl;
        this.dongtaiId = dongtaiId;
        this.classId = classId;
        this.createTime = createTime;
        this.content = content;
        this.photos = photos;
        this.favorters = favorters;
        this.comments = comments;
        this.videoUrl = videoUrl;
        this.movieUrl = movieUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getDongtaiId() {
        return dongtaiId;
    }

    public void setDongtaiId(String dongtaiId) {
        this.dongtaiId = dongtaiId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public List<FavortItem> getFavorters() {
        return favorters;
    }

    public void setFavorters(List<FavortItem> favorters) {
        this.favorters = favorters;
    }

    public List<CommentItem> getComments() {
        return comments;
    }

    public void setComments(List<CommentItem> comments) {
        this.comments = comments;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getMovieUrl() {
        return movieUrl;
    }

    public void setMovieUrl(String movieUrl) {
        this.movieUrl = movieUrl;
    }

    public boolean hasFavort(){
        if(favorters!=null && favorters.size()>0){
            return true;
        }
        return false;
    }

    public boolean hasComment(){
        if(comments!=null && comments.size()>0){
            return true;
        }
        return false;
    }

    public String getCurUserFavortId(String curUserId){
        String favortid = "";
        if(!TextUtils.isEmpty(curUserId) && hasFavort()){
            for(FavortItem item : favorters){
                if(curUserId.equals(item.getUserId())){
                    favortid = item.getFavorId();
                    return favortid;
                }
            }
        }
        return favortid;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeString(this.userName);
        dest.writeString(this.headUrl);
        dest.writeString(this.dongtaiId);
        dest.writeString(this.classId);
        dest.writeString(this.createTime);
        dest.writeString(this.content);
        dest.writeStringList(this.photos);
        dest.writeList(this.favorters);
        dest.writeList(this.comments);
        dest.writeString(this.videoUrl);
        dest.writeString(this.movieUrl);
    }

    protected DongTai(Parcel in) {
        this.userId = in.readString();
        this.userName = in.readString();
        this.headUrl = in.readString();
        this.dongtaiId = in.readString();
        this.classId = in.readString();
        this.createTime = in.readString();
        this.content = in.readString();
        this.photos = in.createStringArrayList();
        this.favorters = new ArrayList<FavortItem>();
        in.readList(this.favorters, FavortItem.class.getClassLoader());
        this.comments = new ArrayList<CommentItem>();
        in.readList(this.comments, CommentItem.class.getClassLoader());
        this.videoUrl = in.readString();
        this.movieUrl = in.readString();
    }

    @Override
    public String toString() {
        return "DongTai{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", headUrl='" + headUrl + '\'' +
                ", dongtaiId='" + dongtaiId + '\'' +
                ", classId='" + classId + '\'' +
                ", createTime='" + createTime + '\'' +
                ", content='" + content + '\'' +
                ", photos=" + photos +
                ", favorters=" + favorters +
                ", comments=" + comments +
                ", videoUrl='" + videoUrl + '\'' +
                ", movieUrl='" + movieUrl + '\'' +
                '}';
    }

    public static final Creator<DongTai> CREATOR = new Creator<DongTai>() {
        @Override
        public DongTai createFromParcel(Parcel source) {
            return new DongTai(source);
        }

        @Override
        public DongTai[] newArray(int size) {
            return new DongTai[size];
        }
    };
}
