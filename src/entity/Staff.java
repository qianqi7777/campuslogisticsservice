package src.entity;

public class Staff {
    private String eid;
    private String eName;
    private String dept;
    private String phone;
    private String position;
    private String password;

    public Staff() {}

    public Staff(String eid, String eName, String dept, String phone, String position, String password) {
        this.eid = eid;
        this.eName = eName;
        this.dept = dept;
        this.phone = phone;
        this.position = position;
        this.password = password;
    }

    public String getEid() { return eid; }
    public void setEid(String eid) { this.eid = eid; }

    public String getEName() { return eName; }
    public void setEName(String eName) { this.eName = eName; }

    public String getDept() { return dept; }
    public void setDept(String dept) { this.dept = dept; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
