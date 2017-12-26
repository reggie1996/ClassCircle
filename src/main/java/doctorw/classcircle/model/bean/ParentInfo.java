package doctorw.classcircle.model.bean;

public class ParentInfo {
	private String phone;
	private String password;
	private String parentName;
	private String parentNo;
	private String headPic;
	private String childNo;
	private String childName;
	private String childSex;
	private String classNo;
	private String parentSex;
	private String coverPic;

	public ParentInfo() {

	}

	public ParentInfo(String phone) {
		this.phone = phone;
	}

	public ParentInfo(String phone, String password, String parentName, String parentNo, String headPic, String childNo, String childName, String childSex, String classNo, String parentSex, String coverPic) {
		this.phone = phone;
		this.password = password;
		this.parentName = parentName;
		this.parentNo = parentNo;
		this.headPic = headPic;
		this.childNo = childNo;
		this.childName = childName;
		this.childSex = childSex;
		this.classNo = classNo;
		this.parentSex = parentSex;
		this.coverPic = coverPic;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getParentNo() {
		return parentNo;
	}

	public void setParentNo(String parentNo) {
		this.parentNo = parentNo;
	}

	public String getHeadPic() {
		return headPic;
	}

	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}

	public String getChildNo() {
		return childNo;
	}

	public void setChildNo(String childNo) {
		this.childNo = childNo;
	}

	public String getChildName() {
		return childName;
	}

	public void setChildName(String childName) {
		this.childName = childName;
	}

	public String getChildSex() {
		return childSex;
	}

	public void setChildSex(String childSex) {
		this.childSex = childSex;
	}

	public String getClassNo() {
		return classNo;
	}

	public void setClassNo(String classNo) {
		this.classNo = classNo;
	}

	public String getParentSex() {
		return parentSex;
	}

	public void setParentSex(String parentSex) {
		this.parentSex = parentSex;
	}

	public String getCoverPic() {
		return coverPic;
	}

	public void setCoverPic(String coverPic) {
		this.coverPic = coverPic;
	}

	@Override
	public String toString() {
		return "ParentInfo{" +
				"phone='" + phone + '\'' +
				", password='" + password + '\'' +
				", parentName='" + parentName + '\'' +
				", parentNo='" + parentNo + '\'' +
				", headPic='" + headPic + '\'' +
				", childNo='" + childNo + '\'' +
				", childName='" + childName + '\'' +
				", childSex='" + childSex + '\'' +
				", classNo='" + classNo + '\'' +
				", parentSex='" + parentSex + '\'' +
				", coverPic='" + coverPic + '\'' +
				'}';
	}

}
