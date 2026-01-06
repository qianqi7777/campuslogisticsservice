package src.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import src.service.UserService;
import src.entity.Student;
import src.entity.Staff;

/**
 * 登录视图：Swing图形化登录界面
 * 替换了原有的命令行交互逻辑，使用 JFrame 实现界面
 */
public class LoginView extends JFrame {
    private UserService userService = new UserService();
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;

    public LoginView() {
        // 初始化窗口基本属性
        setTitle("校园后勤服务系统 - 登录");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 屏幕居中
        setResizable(false);

        initUI();
    }

    /**
     * 初始化界面组件
     */
    private void initUI() {
        JPanel panel = new JPanel();
        panel.setLayout(null); // 使用绝对布局简单排版

        // 标题
        JLabel titleLabel = new JLabel("欢迎使用校园服务管理系统", SwingConstants.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 18));
        titleLabel.setBounds(50, 20, 300, 30);
        panel.add(titleLabel);

        // 用户名
        JLabel userLabel = new JLabel("账号:");
        userLabel.setBounds(50, 70, 60, 25);
        panel.add(userLabel);

        usernameField = new JTextField(20);
        usernameField.setBounds(120, 70, 200, 25);
        panel.add(usernameField);

        // 密码
        JLabel passLabel = new JLabel("密码:");
        passLabel.setBounds(50, 110, 60, 25);
        panel.add(passLabel);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(120, 110, 200, 25);
        panel.add(passwordField);

        // 角色选择
        JLabel roleLabel = new JLabel("身份:");
        roleLabel.setBounds(50, 150, 60, 25);
        panel.add(roleLabel);

        String[] roles = {"学生", "职工", "管理员"};
        roleComboBox = new JComboBox<>(roles);
        roleComboBox.setBounds(120, 150, 200, 25);
        panel.add(roleComboBox);

        // 登录按钮
        JButton loginButton = new JButton("登录");
        loginButton.setBounds(80, 200, 100, 30);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
        panel.add(loginButton);

        // 退出按钮
        JButton exitButton = new JButton("退出");
        exitButton.setBounds(220, 200, 100, 30);
        exitButton.addActionListener(e -> System.exit(0));
        panel.add(exitButton);

        this.add(panel);
    }

    /**
     * 处理登录逻辑
     */
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        int roleIndex = roleComboBox.getSelectedIndex(); // 0: Student, 1: Staff, 2: Admin

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入账号和密码！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (roleIndex == 0) { // 学生
            Student student = userService.loginStudent(username, password);
            if (student != null) {
                JOptionPane.showMessageDialog(this, "登录成功！欢迎 " + student.getSName());
                new StudentView(student).setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "登录失败，用户名或密码错误。", "错误", JOptionPane.ERROR_MESSAGE);
            }
        } else if (roleIndex == 1) { // 职工
            Staff staff = userService.loginStaff(username, password);
            if (staff != null) {
                JOptionPane.showMessageDialog(this, "登录成功！欢迎 " + staff.getEName());
                new StaffView(staff).setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "登录失败，用户名或密码错误。", "错误", JOptionPane.ERROR_MESSAGE);
            }
        } else if (roleIndex == 2) { // 管理员
            Staff admin = userService.loginStaff(username, password);
            // 这里简单复用 Staff 登录检查，实际可能需要校验是否具有管理员权限
            if (admin != null) {
                 JOptionPane.showMessageDialog(this, "管理员登录成功！欢迎 " + admin.getEName());
                 new AdminView(admin).setVisible(true);
                 this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "登录失败，用户名或密码错误。", "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // 显示由 Main 调用
    public void showWin() {
        this.setVisible(true);
    }
}

