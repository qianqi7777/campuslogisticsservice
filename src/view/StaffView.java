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
        // 进入菜单时自动清理过期预约
        reservationService.cleanExpiredReservations();

        while (true) {
            System.out.println("\n--- 预约服务 ---");
            System.out.println("1. 查看可用场地");
            System.out.println("2. 查询场地状态 (指定ID)");
            System.out.println("3. 提交预约申请");
            System.out.println("4. 场馆管理 (添加/删除)");
            System.out.println("0. 返回");
            System.out.print("请选择：");

            String choice = scanner.nextLine();
            if ("0".equals(choice)) return;

            switch (choice) {
                case "1":
                    java.util.List<src.entity.Venue> venues = reservationService.getAvailableVenues();
                    System.out.println("--- 可用场地列表 ---");
                    if (venues.isEmpty()) {
                        System.out.println("当前没有可用场地。");
                    } else {
                        for (src.entity.Venue v : venues) {
                            System.out.printf("ID: %d, 名称: %s, 容量: %d, 位置: %s\n",
                                    v.getVenueID(), v.getVenueName(), v.getCapacity(), v.getLocation());
                        }
                    }
                    break;
                case "2":
                    System.out.print("请输入场地ID：");
                    try {
                        int vid = Integer.parseInt(scanner.nextLine());
                        reservationService.checkVenueStatus(vid);
                    } catch (NumberFormatException e) {
                        System.out.println("输入格式错误。");
                    }
                    break;
                case "3":
                    System.out.print("请输入场地ID：");
                    int venueId;
                    try {
                        venueId = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("ID格式错误。");
                        break;
                    }

                    // 显示当前时间作为样例
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String nowStr = sdf.format(new java.util.Date());
                    System.out.println("当前时间: " + nowStr);
                    System.out.print("请输入预约时间 (格式: yyyy-MM-dd HH:mm:ss)：");
                    String dateStr = scanner.nextLine();

                    System.out.print("请输入时长 (小时)：");
                    int duration;
                    try {
                        duration = Integer.parseInt(scanner.nextLine());
                        if (duration <= 0) {
                            System.out.println("时长必须大于0。");
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("时长格式错误。");
                        break;
                    }

                    try {
                        Reservation r = new Reservation();
                        r.setVenueID(venueId);
                        r.setReserverID(staff.getEid());
                        r.setResTime(Timestamp.valueOf(dateStr));
                        r.setDuration(duration);

                        String result = reservationService.submitReservationWithCheck(r);
                        System.out.println(result);
                    } catch (IllegalArgumentException e) {
                        System.out.println("时间格式错误，请参考样例格式。");
                    }
                    break;
                case "4":
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
            System.out.println("4. 解除挂失");
            System.out.println("5. 注册校园卡");
            System.out.println("0. 返回");
            System.out.print("请选择：");

            String choice = scanner.nextLine();
            if ("0".equals(choice)) return;

            switch (choice) {
                case "1":
                    cardService.getCardInfo(staff.getEid(), "staff");
                    break;
                case "2":
                    System.out.print("请输入卡号 (回车默认充值本卡)：");
                    String cid = scanner.nextLine();
                    if (cid.trim().isEmpty()) {
                        src.entity.CampusCard myCard = cardService.getCardInfo(staff.getEid(), "staff");
                        if (myCard != null) {
                            cid = myCard.getCardID();
                        } else {
                            break;
                        }
                    }
                    System.out.print("请输入金额：");
                    try {
                        java.math.BigDecimal amount = new java.math.BigDecimal(scanner.nextLine());
                        cardService.recharge(cid, amount);
                    } catch (NumberFormatException e) {
                        System.out.println("金额格式错误");
                    }
                    break;
                case "3":
                    System.out.println("正在提交挂失请求...");
                    cardService.reportLoss(staff.getEid(), "staff");
                    break;
                case "4":
                    System.out.println("正在解除挂失...");
                    cardService.cancelLoss(staff.getEid(), "staff");
                    break;
                case "5":
                    System.out.print("请输入新卡号：");
                    String newCid = scanner.nextLine();
                    if (newCid.trim().isEmpty()) {
                        System.out.println("卡号不能为空");
                        break;
                    }
                    src.entity.CampusCard newCard = new src.entity.CampusCard();
                    newCard.setCardID(newCid);
                    newCard.setUserID(staff.getEid());
                    newCard.setUserType("staff");
                    newCard.setBalance(java.math.BigDecimal.ZERO);
                    newCard.setStatus("正常");
                    if (cardService.addCard(newCard)) {
                        System.out.println("校园卡注册成功！");
                    } else {
                        System.out.println("校园卡注册失败（可能卡号已存在或您已绑定校园卡）。");
                    }
                    break;
                default:
                    System.out.println("无效选项");
            }
        }
    }
}
