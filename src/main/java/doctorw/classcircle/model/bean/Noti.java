package doctorw.classcircle.model.bean;

import java.io.Serializable;

/**
 * Created by asus on 2017/5/1.
 */

public class Noti implements Serializable {
    private String notiId;
    private String name;
    private String title;
    private String context;
    private String time;

    public Noti() {
    }

    public Noti(String title, String name,String context, String time) {
        this.name = name;
        this.title = title;
        this.context = context;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotiId() {
        return notiId;
    }

    public void setNotiId(String notiId) {
        this.notiId = notiId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
