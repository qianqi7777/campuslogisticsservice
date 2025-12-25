package src.entity;

/**
 * Student 实体类
 * 对应数据库中的 Student 表，用于表示学生账户与基本信息
 * 字段说明：
 *  - sid: 学号（主键）
 *  - sName: 学生姓名
 *  - college: 所属学院
 *  - phone: 手机号
 *  - grade: 年级
 *  - password: 登录密码（示例中明文存储，不建议用于真实系统）
 */
public class Student {
    /** 学号（主键） */
    private String sid;
    /** 学生姓名 */
    private String sName;
    /** 所属学院 */
    private String college;
    /** 手机号 */
    private String phone;
    /** 年级 */
    private String grade;
    /** 登录密码 */
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

    // getter / setter
    /**
     * 获取学号
     * @return 学号
     */
    public String getSid() { return sid; }
    /**
     * 设置学号
     * @param sid 学号
     */
    public void setSid(String sid) { this.sid = sid; }

    /**
     * 获取姓名
     * @return 姓名
     */
    public String getSName() { return sName; }
    /**
     * 设置姓名
     * @param sName 姓名
     */
    public void setSName(String sName) { this.sName = sName; }

    /**
     * 获取学院
     * @return 学院
     */
    public String getCollege() { return college; }
    /**
     * 设置学院
     * @param college 学院
     */
    public void setCollege(String college) { this.college = college; }

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
     * 获取年级
     * @return 年级
     */
    public String getGrade() { return grade; }
    /**
     * 设置年级
     * @param grade 年级
     */
    public void setGrade(String grade) { this.grade = grade; }

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
