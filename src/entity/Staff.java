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
    /** 工号（主键） */
    private String eid;
    /** 姓名 */
    private String eName;
    /** 所属部门 */
    private String dept;
    /** 手机号 */
    private String phone;
    /** 职位 */
    private String position;
    /** 登录密码 */
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
    /**
     * 获取工号
     * @return 工号
     */
    public String getEid() { return eid; }
    /**
     * 设置工号
     * @param eid 工号
     */
    public void setEid(String eid) { this.eid = eid; }

    /**
     * 获取姓名
     * @return 姓名
     */
    public String getEName() { return eName; }
    /**
     * 设置姓名
     * @param eName 姓名
     */
    public void setEName(String eName) { this.eName = eName; }

    /**
     * 获取部门
     * @return 部门
     */
    public String getDept() { return dept; }
    /**
     * 设置部门
     * @param dept 部门
     */
    public void setDept(String dept) { this.dept = dept; }

    /**
     * 获取手机号
     * @return 手机号
     */
    public String getPhone() { return phone; }
    /**
     * 设置手机号
     * @param phone 手机号
     */
    public void setPhone(String phone) { this.phone = phone; }

    /**
     * 获取职位
     * @return 职位
     */
    public String getPosition() { return position; }
    /**
     * 设置职位
     * @param position 职位
     */
    public void setPosition(String position) { this.position = position; }

    /**
     * 获取密码
     * @return 密码
     */
    public String getPassword() { return password; }
    /**
     * 设置密码
     * @param password 密码
     */
    public void setPassword(String password) { this.password = password; }
}
