package src.view;

import src.entity.Staff;
import java.util.Scanner;
import src.service.RepairService;
import src.service.ReservationService;
import src.service.CampusCardService;
import src.service.VenueService;
import src.entity.Venue;
import src.entity.Reservation;
import java.sql.Timestamp;

/**
 * 职工控制台视图
 * 提供职工相关的操作菜单
 */
public class StaffView {
    private Staff staff;
    private Scanner scanner = new Scanner(System.in);
    private RepairService repairService = new RepairService();
    private ReservationService reservationService = new ReservationService();
    private CampusCardService cardService = new CampusCardService();
    private VenueService venueService = new VenueService();

    public StaffView(Staff staff) {
        this.staff = staff;
    }

    public void show() {
        while (true) {
            System.out.println("\n=== 职工主菜单 (" + staff.getEName() + ") ===");
            System.out.println("1. 报修服务 (处理报修/更新状态/查看评价)");
            System.out.println("2. 预约服务");
            System.out.println("3. 校园卡服务 (查询/充值/挂失)");
            System.out.println("0. 注销登录");
            System.out.print("请选择：");

            String choice = scanner.nextLine();
            if ("0".equals(choice)) return;

            switch (choice) {
                case "1":
                    showRepairMenu();
                    break;
                case "2":
                    showReservationMenu();
                    break;
                case "3":
                    showCardMenu();
                    break;
                default:
                    System.out.println("无效选项");
            }
        }
    }

    private void showRepairMenu() {
        while (true) {
            System.out.println("\n--- 报修服务 ---");
            System.out.println("1. 处理报修单 (查看分配给我的)");
            System.out.println("2. 更新维修状态");
            System.out.println("3. 查看评价");
            System.out.println("0. 返回");
            System.out.print("请选择：");

            String choice = scanner.nextLine();
            if ("0".equals(choice)) return;

            switch (choice) {
                case "1":
                    repairService.getAssignedRepairs(staff.getEid());
                    break;
                case "2":
                    System.out.print("请输入维修单ID：");
                    int rid = Integer.parseInt(scanner.nextLine());
                    System.out.print("请输入新状态：");
                    String status = scanner.nextLine();
                    repairService.updateRepairStatus(rid, status);
                    break;
                case "3":
                    repairService.viewEvaluations(staff.getEid());
                    break;
                default:
                    System.out.println("无效选项");
            }
        }
    }

    private void showReservationMenu() {
        while (true) {
            System.out.println("\n--- 预约服务 ---");
            System.out.println("1. 查询场地状态");
            System.out.println("2. 提交预约申请");
            System.out.println("3. 场馆管理 (添加/删除)");
            System.out.println("0. 返回");
            System.out.print("请选择：");

            String choice = scanner.nextLine();
            if ("0".equals(choice)) return;

            switch (choice) {
                case "1":
                    System.out.print("请输入场地ID：");
                    int vid = Integer.parseInt(scanner.nextLine());
                    reservationService.checkVenueStatus(vid);
                    break;
                case "2":
                    System.out.print("请输入场地ID：");
                    int venueId = Integer.parseInt(scanner.nextLine());
                    System.out.print("请输入预约时间 (yyyy-MM-dd HH:mm:ss)：");
                    String dateStr = scanner.nextLine();
                    System.out.print("请输入时长 (小时)：");
                    int duration = Integer.parseInt(scanner.nextLine());

                    Reservation r = new Reservation();
                    r.setVenueID(venueId);
                    r.setReserverID(staff.getEid());
                    r.setResTime(Timestamp.valueOf(dateStr));
                    r.setDuration(duration);
                    reservationService.submitReservation(r);
                    System.out.println("预约申请已提交，等待审核。");
                    break;
                case "3":
                    manageVenues();
                    break;
                default:
                    System.out.println("无效选项");
            }
        }
    }

    private void manageVenues() {
        while (true) {
            System.out.println("\n--- 场馆管理 ---");
            System.out.println("1. 添加场馆");
            System.out.println("2. 删除场馆");
            System.out.println("3. 查看所有场馆");
            System.out.println("0. 返回");
            System.out.print("请选择：");

            String choice = scanner.nextLine();
            if ("0".equals(choice)) return;

            switch (choice) {
                case "1":
                    System.out.print("请输入场馆名称：");
                    String name = scanner.nextLine();
                    System.out.print("请输入容量：");
                    int capacity = Integer.parseInt(scanner.nextLine());
                    System.out.print("请输入位置：");
                    String location = scanner.nextLine();
                    Venue v = new Venue();
                    v.setVenueName(name);
                    v.setCapacity(capacity);
                    v.setLocation(location);
                    v.setIsAvailable("是");
                    venueService.addVenue(v);
                    break;
                case "2":
                    System.out.print("请输入要删除的场馆ID：");
                    int vid = Integer.parseInt(scanner.nextLine());
                    venueService.deleteVenue(vid);
                    break;
                case "3":
                    java.util.List<Venue> list = venueService.getAllVenues();
                    System.out.println("--- 所有场馆 ---");
                    for (Venue venue : list) {
                        System.out.printf("ID: %d, 名称: %s, 容量: %d, 位置: %s, 可用: %s\n",
                            venue.getVenueID(), venue.getVenueName(), venue.getCapacity(), venue.getLocation(), venue.getIsAvailable());
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
            System.out.println("1. 查询余额");
            System.out.println("2. 充值");
            System.out.println("3. 挂失");
            System.out.println("0. 返回");
            System.out.print("请选择：");

            String choice = scanner.nextLine();
            if ("0".equals(choice)) return;

            switch (choice) {
                case "1":
                    cardService.getCardInfo(staff.getEid());
                    break;
                case "2":
                    System.out.print("请输入卡号：");
                    String cid = scanner.nextLine();
                    System.out.print("请输入金额：");
                    java.math.BigDecimal amount = new java.math.BigDecimal(scanner.nextLine());
                    cardService.recharge(cid, amount);
                    break;
                case "3":
                    System.out.print("请输入卡号：");
                    String cid2 = scanner.nextLine();
                    cardService.reportLoss(cid2);
                    break;
                default:
                    System.out.println("无效选项");
            }
        }
    }
}
