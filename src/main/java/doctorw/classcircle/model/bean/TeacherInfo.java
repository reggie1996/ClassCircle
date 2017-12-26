package doctorw.classcircle.model.bean;

public class TeacherInfo {
	private String phone;
	private String password;
	private String headPic;
	private String teacherName;
	private String teacherNo;
	private String teacherSex;
	private String teacherRole;
	private String teachCourse;
	private String classNo;
	private String coverPic;
	private String identityPic;

	public TeacherInfo() {

	}

	public TeacherInfo(String phone, String password, String teacherName) {
		this.phone = phone;
		this.password = password;
		this.teacherName = teacherName;
	}

	public TeacherInfo(String phone, String password, String headPic, String teacherName, String teacherNo, String teacherSex, String teacherRole, String teachCourse, String classNo, String coverPic, String identityPic) {
		this.phone = phone;
		this.password = password;
		this.headPic = headPic;
		this.teacherName = teacherName;
		this.teacherNo = teacherNo;
		this.teacherSex = teacherSex;
		this.teacherRole = teacherRole;
		this.teachCourse = teachCourse;
		this.classNo = classNo;
		this.coverPic = coverPic;
		this.identityPic = identityPic;
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

	public String getHeadPic() {
		return headPic;
	}

	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getTeacherNo() {
		return teacherNo;
	}

	public void setTeacherNo(String teacherNo) {
		this.teacherNo = teacherNo;
	}

	public String getTeacherSex() {
		return teacherSex;
	}

	public void setTeacherSex(String teacherSex) {
		this.teacherSex = teacherSex;
	}

	public String getTeacherRole() {
		return teacherRole;
	}

	public void setTeacherRole(String teacherRole) {
		this.teacherRole = teacherRole;
	}

	public String getTeachCourse() {
		return teachCourse;
	}

	public void setTeachCourse(String teachCourse) {
		this.teachCourse = teachCourse;
	}

	public String getClassNo() {
		return classNo;
	}

	public void setClassNo(String classNo) {
		this.classNo = classNo;
	}

	public String getCoverPic() {
		return coverPic;
	}

	public void setCoverPic(String coverPic) {
		this.coverPic = coverPic;
	}

	public String getIdentityPic() {
		return identityPic;
	}

	public void setIdentityPic(String identityPic) {
		this.identityPic = identityPic;
	}

	@Override
	public String toString() {
		return "TeacherInfo{" +
				"phone='" + phone + '\'' +
				", password='" + password + '\'' +
				", headPic='" + headPic + '\'' +
				", teacherName='" + teacherName + '\'' +
				", teacherNo='" + teacherNo + '\'' +
				", teacherSex='" + teacherSex + '\'' +
				", teacherRole='" + teacherRole + '\'' +
				", teachCourse='" + teachCourse + '\'' +
				", classNo='" + classNo + '\'' +
				", coverPic='" + coverPic + '\'' +
				", identityPic='" + identityPic + '\'' +
				'}';
	}
}
