package doctorw.classcircle.model.bean;

/**
 * Created by asus on 2017/5/9.
 */

public class ArticleComment {

    private String headPhoto;
    private String name;
    private String comment;

    public ArticleComment() {
    }

    public ArticleComment(String headPhoto, String name, String comment) {
        this.headPhoto = headPhoto;
        this.name = name;
        this.comment = comment;

    }

    public String getHeadPhoto() {
        return headPhoto;
    }

    public void setHeadPhoto(String headPhoto) {
        this.headPhoto = headPhoto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
