package doctorw.classcircle.model.bean;

import java.io.Serializable;

/**
 * @author yiw
 * @ClassName: CommentItem
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2015-12-28 下午3:44:38
 */
public class CommentItem implements Serializable {

    private String commentId;
    private String text;
    private String toUserName;
    private String toUserNo;
    private String userName;
    private String userNo;

    public CommentItem() {
    }

    public CommentItem(String text, String userName) {
        this.text = text;
        this.userName = userName;
    }

    public CommentItem(String commentId, String text, String toUserName, String toUserNo, String userName, String userNo) {
        this.commentId = commentId;
        this.text = text;
        this.toUserName = toUserName;
        this.toUserNo = toUserNo;
        this.userName = userName;
        this.userNo = userNo;
    }


    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getToUserNo() {
        return toUserNo;
    }

    public void setToUserNo(String toUserNo) {
        this.toUserNo = toUserNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }
}
