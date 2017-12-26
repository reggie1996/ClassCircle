package doctorw.classcircle.model.bean;

/**
 * Created by asus on 2017/4/25.
 */

public class ArticleTheme {
    /**
     * newsTypeId : 1
     * newsTypeName : 小升初
     */

    private String newsTypeId;
    private String newsTypeName;

    public ArticleTheme() {

    }

    public ArticleTheme(String newsTypeId, String newsTypeName) {
        this.newsTypeId = newsTypeId;
        this.newsTypeName = newsTypeName;
    }

    public String getNewsTypeId() {
        return newsTypeId;
    }

    public void setNewsTypeId(String newsTypeId) {
        this.newsTypeId = newsTypeId;
    }

    public String getNewsTypeName() {
        return newsTypeName;
    }

    public void setNewsTypeName(String newsTypeName) {
        this.newsTypeName = newsTypeName;
    }

    @Override

    public String toString() {
        return "ArticleTheme{" +
                "newsTypeId='" + newsTypeId + '\'' +
                ", newsTypeName='" + newsTypeName + '\'' +
                '}';
    }

}

