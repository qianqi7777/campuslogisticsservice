package src.entity;

/**
 * Staff 实体类
 * 对应数据库中的 Staff 表，用于表示教职工/管理员账号信息
 * 字段说明：
 *  - eid: 工号（主键）
 *  - eName: 姓名
 *  - dept: 所属部门
 *  - phone: 手机号
 *  - position: 职位
 *  - password: 登录密码（项目示例中以明文存储，不建议在生产环境中如此）
 */
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

    // getter / setter
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
