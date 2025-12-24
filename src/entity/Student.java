package src.entity;

public class Student {
    private String sid;
    private String sName;
    private String college;
    private String phone;
    private String grade;
    private String password;

    public Student() {}

    public Student(String sid, String sName, String college, String phone, String grade, String password) {
        this.sid = sid;
        this.sName = sName;
        this.college = college;
        this.phone = phone;
        this.grade = grade;
        this.password = password;
    }

    public String getSid() { return sid; }
    public void setSid(String sid) { this.sid = sid; }

    public String getSName() { return sName; }
    public void setSName(String sName) { this.sName = sName; }

    public String getCollege() { return college; }
    public void setCollege(String college) { this.college = college; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
