package doctorw.classcircle.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by asus on 2017/5/10.
 */

public class CircleDetail implements Parcelable {
    private String circleId;
    private String headUrl;//头像URl
    private String picUrl; //封面Url
    private String cirName; //圈子名字
    private String perNum; //成员数量
    private String dtNum; //动态数量
    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public CircleDetail() {
    }

    public CircleDetail(String circleId, String headUrl, String picUrl, String cirName, String perNum, String dtNum, String desc) {
        this.circleId = circleId;
        this.headUrl = headUrl;
        this.picUrl = picUrl;
        this.cirName = cirName;
        this.perNum = perNum;
        this.dtNum = dtNum;
        this.desc = desc;
    }

    public String getCircleId() {
        return circleId;
    }

    public void setCircleId(String circleId) {
        this.circleId = circleId;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getCirName() {
        return cirName;
    }

    public void setCirName(String cirName) {
        this.cirName = cirName;
    }

    public String getPerNum() {
        return perNum;
    }

    public void setPerNum(String perNum) {
        this.perNum = perNum;
    }

    public String getDtNum() {
        return dtNum;
    }

    public void setDtNum(String dtNum) {
        this.dtNum = dtNum;
    }

    @Override
    public String toString() {
        return "CircleDetail{" +
                "circleId='" + circleId + '\'' +
                ", headUrl='" + headUrl + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", cirName='" + cirName + '\'' +
                ", perNum='" + perNum + '\'' +
                ", dtNum='" + dtNum + '\'' +
                '}';
    }


    public static Creator<CircleDetail> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.circleId);
        dest.writeString(this.headUrl);
        dest.writeString(this.picUrl);
        dest.writeString(this.cirName);
        dest.writeString(this.perNum);
        dest.writeString(this.dtNum);
        dest.writeString(this.desc);
    }

    protected CircleDetail(Parcel in) {
        this.circleId = in.readString();
        this.headUrl = in.readString();
        this.picUrl = in.readString();
        this.cirName = in.readString();
        this.perNum = in.readString();
        this.dtNum = in.readString();
        this.desc = in.readString();
    }

    public static final Creator<CircleDetail> CREATOR = new Creator<CircleDetail>() {
        @Override
        public CircleDetail createFromParcel(Parcel source) {
            return new CircleDetail(source);
        }

        @Override
        public CircleDetail[] newArray(int size) {
            return new CircleDetail[size];
        }
    };
}
