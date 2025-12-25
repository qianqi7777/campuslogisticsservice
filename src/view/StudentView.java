package src.view;

import src.entity.Student;
import java.util.Scanner;

/**
 * 学生控制台视图：StudentView
 * 提供学生用户可以执行的操作菜单（当前为占位示例）
 */
public class StudentView {
    private Student student;
    private Scanner scanner = new Scanner(System.in);

    public StudentView(Student student) {
        this.student = student;
    }

    /**
     * 显示学生主菜单并响应输入（实际功能由 Service 层实现并在此调用）
     */
    public void show() {
        while (true) {
            // 打印学生菜单
            System.out.println("\n=== 学生主菜单 (" + student.getSName() + ") ===");
            System.out.println("1. 提交维修申请");
            System.out.println("2. 评价已完成维修");
            System.out.println("3. 发布失物招领");
            System.out.println("4. 提交场馆预约");
            System.out.println("5. 查看个人预约记录");
            System.out.println("6. 校园卡账户查询");
            System.out.println("7. 挂失校园卡");
            System.out.println("0. 注销登录");
            System.out.print("请选择：");

            // 读取用户输入
            String choice = scanner.nextLine();
            if ("0".equals(choice)) return;

            // 占位提示，后续应调用 Service 方法
            System.out.println("功能 [" + choice + "] 尚未实现具体逻辑，仅为示例菜单。");
        }
    }
}
