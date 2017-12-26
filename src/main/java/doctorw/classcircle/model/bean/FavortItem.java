package doctorw.classcircle.model.bean;

import java.io.Serializable;

public class FavortItem implements Serializable{

	private static final long serialVersionUID = 1L;
	private String favorId;
	private String userName;
	private String userId;

	public FavortItem() {
	}

	public FavortItem(String favorId, String userName, String userId) {
		this.favorId = favorId;
		this.userName = userName;
		this.userId = userId;
	}

    public FavortItem(String userName, String userId) {
        this.userName = userName;
        this.userId = userId;
    }

    public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getFavorId() {
		return favorId;
	}

	public void setFavorId(String favorId) {
		this.favorId = favorId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
