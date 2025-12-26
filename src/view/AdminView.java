package src.view;

import src.entity.Staff;
import src.entity.Student;
import src.service.UserService;
import src.service.RepairService;
import src.service.ReservationService;
import src.service.CampusCardService;
import src.entity.Reservation;
import java.util.List;
import java.util.Scanner;

/**
 * 管理员控制台视图：AdminView
 * 提供简单的命令行菜单，供管理员执行各类管理操作（当前部分功能为占位）
 */
public class AdminView {
    private Staff staff;
    private Scanner scanner = new Scanner(System.in);
    private UserService userService = new UserService();
    private RepairService repairService = new RepairService();
    private ReservationService reservationService = new ReservationService();
    private CampusCardService cardService = new CampusCardService();

    public AdminView(Staff staff) {
        this.staff = staff;
    }

    /**
     * 显示管理员主菜单并响应输入
     * 当前实现为占位，真实功能应调用 AdminService 中具体业务方法
     */
    public void show() {
        while (true) {
            // 打印菜单选项
            System.out.println("\n=== 管理员主菜单 (" + staff.getEName() + ") ===");
            System.out.println("1. 账号管理 (设置/注销用户)");
            System.out.println("2. 报修服务 (统计/分配)");
            System.out.println("3. 预约服务 (审核)");
            System.out.println("4. 校园卡服务 (挂失/恢复)");
            System.out.println("0. 注销登录");
            System.out.print("请选 择：");

            // 读取用户输入
            String choice = scanner.nextLine();
            if ("0".equals(choice)) return;

            switch (choice) {
                case "1":
                    showAccountMenu();
                    break;
                case "2":
                    showRepairMenu();
                    break;
                case "3":
                    showReservationMenu();
                    break;
                case "4":
                    showCardMenu();
                    break;
                default:
                    System.out.println("无效选项");
            }
        }
    }

    private void showAccountMenu() {
        while (true) {
            System.out.println("\n--- 账号管理 ---");
            System.out.println("1. 添加学生");
            System.out.println("2. 删除学生");
            System.out.println("3. 查看所有学生");
            System.out.println("4. 添加职工");
            System.out.println("5. 删除职工");
            System.out.println("6. 查看所有职工");
            System.out.println("0. 返回");
            System.out.print("请选择：");

            String choice = scanner.nextLine();
            if ("0".equals(choice)) return;

            switch (choice) {
                case "1":
                    addStudent();
                    break;
                case "2":
                    deleteStudent();
                    break;
                case "3":
                    listStudents();
                    break;
                case "4":
                    addStaff();
                    break;
                case "5":
                    deleteStaff();
                    break;
                case "6":
                    listStaff();
                    break;
                default:
                    System.out.println("无效选项");
            }
        }
    }

    private void addStudent() {
        System.out.println("请输入学生信息：");
        System.out.print("学号：");
        String sid = scanner.nextLine();
        System.out.print("姓名：");
        String name = scanner.nextLine();
        System.out.print("学院：");
        String college = scanner.nextLine();
        System.out.print("手机号：");
        String phone = scanner.nextLine();
        System.out.print("年级：");
        String grade = scanner.nextLine();
        System.out.print("密码：");
        String pwd = scanner.nextLine();

        Student s = new Student(sid, name, college, phone, grade, pwd);
        if (userService.addUser(s)) {
            System.out.println("添加学生成功！");
        } else {
            System.out.println("添加学生失败（可能学号已存在）。");
        }
    }

    private void deleteStudent() {
        System.out.print("请输入要删除的学生学号：");
        String sid = scanner.nextLine();
        if (userService.deleteUser(sid, "student")) {
            System.out.println("删除成功！");
        } else {
            System.out.println("删除失败（学生不存在）。");
        }
    }

    private void listStudents() {
        List<Student> list = userService.getAllStudents();
        System.out.println("\n--- 学生列表 ---");
        System.out.printf("%-10s %-10s %-15s %-12s %-10s\n", "学号", "姓名", "学院", "手机号", "年级");
        for (Student s : list) {
            System.out.printf("%-10s %-10s %-15s %-12s %-10s\n", s.getSid(), s.getSName(), s.getCollege(), s.getPhone(), s.getGrade());
        }
    }

    private void addStaff() {
        System.out.println("请输入职工信息：");
        System.out.print("工号：");
        String eid = scanner.nextLine();
        System.out.print("姓名：");
        String name = scanner.nextLine();
        System.out.print("部门：");
        String dept = scanner.nextLine();
        System.out.print("手机号：");
        String phone = scanner.nextLine();
        System.out.print("职位：");
        String position = scanner.nextLine();
        System.out.print("密码：");
        String pwd = scanner.nextLine();

        Staff s = new Staff(eid, name, dept, phone, position, pwd);
        if (userService.addUser(s)) {
            System.out.println("添加职工成功！");
        } else {
            System.out.println("添加职工失败（可能工号已存在）。");
        }
    }

    private void deleteStaff() {
        System.out.print("请输入要删除的职工工号：");
        String eid = scanner.nextLine();
        // 防止删除自己
        if (eid.equals(staff.getEid())) {
            System.out.println("不能删除当前登录的管理员账号！");
            return;
        }
        if (userService.deleteUser(eid, "staff")) {
            System.out.println("删除成功！");
        } else {
            System.out.println("删除失败（职工不存在）。");
        }
    }

    private void listStaff() {
        List<Staff> list = userService.getAllStaff();
        System.out.println("\n--- 职工列表 ---");
        System.out.printf("%-10s %-10s %-15s %-12s %-10s\n", "工号", "姓名", "部门", "手机号", "职位");
        for (Staff s : list) {
            System.out.printf("%-10s %-10s %-15s %-12s %-10s\n", s.getEid(), s.getEName(), s.getDept(), s.getPhone(), s.getPosition());
        }
    }

    private void showRepairMenu() {
        while (true) {
            System.out.println("\n--- 报修服务 ---");
            System.out.println("1. 统计报修数量");
            System.out.println("2. 分配维修任务");
            System.out.println("3. 查看所有报修单");
            System.out.println("0. 返回");
            System.out.print("请选择：");

            String choice = scanner.nextLine();
            if ("0".equals(choice)) return;

            switch (choice) {
                case "1":
                    repairService.getRepairStatistics();
                    break;
                case "2":
                    // 简单示例：输入ID分配
                    System.out.print("请输入维修单ID：");
                    int rid = Integer.parseInt(scanner.nextLine());

                    // 显示职工列表供选择
                    listStaff();

                    System.out.print("请输入指派的职工工号：");
                    String eid = scanner.nextLine();
                    repairService.assignRepairTask(rid, eid);
                    break;
                case "3":
                    List<src.entity.Repair> list = repairService.getAllRepairs();
                    System.out.println("--- 所有报修单 ---");
                    for (src.entity.Repair r : list) {
                        System.out.printf("ID: %d, 内容: %s, 状态: %s, 提交人: %s, 处理人: %s\n",
                            r.getRepairID(), r.getContent(), r.getStatus(), r.getSubmitterID(), r.getHandlerID());
                    }
                    break;
                default:
                    System.out.println("无效选项");
            }
        }
    }

    private void showReservationMenu() {
        while (true) {
            System.out.println("\n--- 预约服务 ---");
            System.out.println("1. 审核预约申请");
            System.out.println("2. 查看所有预约");
            System.out.println("0. 返回");
            System.out.print("请选择：");

            String choice = scanner.nextLine();
            if ("0".equals(choice)) return;

            switch (choice) {
                case "1":
                    // 列出待审核预约
                    List<Reservation> allRes = reservationService.getAllReservations();
                    System.out.println("--- 待审核预约 ---");
                    boolean hasPending = false;
                    for (Reservation r : allRes) {
                        if ("待审核".equals(r.getAuditStatus())) {
                            System.out.printf("ID: %d, 场馆ID: %d, 预约人: %s, 时间: %s\n",
                                r.getResID(), r.getVenueID(), r.getReserverID(), r.getResTime());
                            hasPending = true;
                        }
                    }
                    if (!hasPending) {
                        System.out.println("暂无待审核预约。");
                        break;
                    }

                    System.out.print("请输入预约ID：");
                    int rid = Integer.parseInt(scanner.nextLine());
                    System.out.print("是否通过 (y/n)：");
                    String pass = scanner.nextLine();
                    reservationService.auditReservation(rid, "y".equalsIgnoreCase(pass));
                    break;
                case "2":
                    List<src.entity.Reservation> list = reservationService.getAllReservations();
                    System.out.println("--- 所有预约 ---");
                    for (src.entity.Reservation r : list) {
                        System.out.printf("ID: %d, 场馆ID: %d, 预约人: %s, 时间: %s, 状态: %s\n",
                            r.getResID(), r.getVenueID(), r.getReserverID(), r.getResTime(), r.getAuditStatus());
                    }
                    break;
                default:
                    System.out.println("无效选项");
            }
        }
    }

    private void showCardMenu() {
        while (true) {
            System.out.println("\n--- 校园卡服务 ---");
            System.out.println("1. 挂失/恢复校园卡");
            System.out.println("2. 查看所有校园卡");
            System.out.println("3. 添加校园卡");
            System.out.println("0. 返回");
            System.out.print("请选择：");

            String choice = scanner.nextLine();
            if ("0".equals(choice)) return;

            switch (choice) {
                case "1":
                    System.out.print("请输入卡号：");
                    String cid = scanner.nextLine();
                    System.out.print("操作类型 (1.挂失 2.恢复)：");
                    String type = scanner.nextLine();
                    cardService.handleCardStatus(cid, "2".equals(type));
                    break;
                case "2":
                    List<src.entity.CampusCard> list = cardService.getAllCards();
                    System.out.println("--- 所有校园卡 ---");
                    for (src.entity.CampusCard c : list) {
                        System.out.printf("卡号: %s, 用户: %s, 类型: %s, 余额: %.2f, 状态: %s\n",
                            c.getCardID(), c.getUserID(), c.getUserType(), c.getBalance(), c.getStatus());
                    }
                    break;
                case "3":
                    System.out.print("请输入卡号：");
                    String newCid = scanner.nextLine();
                    System.out.print("请输入用户ID (学号/工号)：");
                    String uid = scanner.nextLine();
                    System.out.print("请输入用户类型 (student/staff)：");
                    String uType = scanner.nextLine();
                    System.out.print("请输入初始余额：");
                    java.math.BigDecimal balance = new java.math.BigDecimal(scanner.nextLine());

                    src.entity.CampusCard newCard = new src.entity.CampusCard();
                    newCard.setCardID(newCid);
                    newCard.setUserID(uid);
                    newCard.setUserType(uType);
                    newCard.setBalance(balance);
                    newCard.setStatus("正常");

                    if (cardService.addCard(newCard)) {
                        System.out.println("添加校园卡成功！");
                    } else {
                        System.out.println("添加校园卡失败。");
                    }
                    break;
                default:
                    System.out.println("无效选项");
            }
        }
    }
}
