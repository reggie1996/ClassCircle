package doctorw.classcircle.model.bean;

import java.util.List;

/**
 * Created by asus on 2017/5/2.
 */

public class photosBean {
    private String date;
    private List<String> photosUrl;

    public photosBean() {
    }

    public photosBean(String date, List<String> photosUrl) {
        this.date = date;
        this.photosUrl = photosUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getPhotosUrl() {
        return photosUrl;
    }

    public void setPhotosUrl(List<String> photosUrl) {
        this.photosUrl = photosUrl;
    }
}
