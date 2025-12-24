package src.view;

import src.entity.Staff;
import java.util.Scanner;

public class AdminView {
    private Staff staff;
    private Scanner scanner = new Scanner(System.in);

    public AdminView(Staff staff) {
        this.staff = staff;
    }

    public void show() {
        while (true) {
            System.out.println("\n=== 管理员主菜单 (" + staff.getEName() + ") ===");
            System.out.println("1. 审核场馆预约");
            System.out.println("2. 分配维修任务");
            System.out.println("3. 更新维修状态为已完成");
            System.out.println("4. 按学院统计报修数量");
            System.out.println("5. 查看维修评价统计");
            System.out.println("6. 校园卡挂失恢复");
            System.out.println("0. 注销登录");
            System.out.print("请选择：");

            String choice = scanner.nextLine();
            if ("0".equals(choice)) return;

            System.out.println("功能 [" + choice + "] 尚未实现具体逻辑。");
        }
    }
}
