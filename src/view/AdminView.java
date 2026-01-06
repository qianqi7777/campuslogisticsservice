package src.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Vector;

import src.entity.*;
import src.service.*;

/**
 * 管理员控制台视图：Swing图形化界面
 */
public class AdminView extends JFrame {
    private Staff staff;
    private UserService userService = new UserService();
    private RepairService repairService = new RepairService();
    private ReservationService reservationService = new ReservationService();
    private CampusCardService cardService = new CampusCardService();
    private VenueService venueService = new VenueService();

    // Models for tables to refresh data
    private DefaultTableModel studentTableModel;
    private DefaultTableModel staffTableModel;
    private DefaultTableModel repairTableModel;
    private DefaultTableModel reservationTableModel;
    private DefaultTableModel cardTableModel;
    private DefaultTableModel venueTableModel;

    public AdminView(Staff staff) {
        this.staff = staff;
        setTitle("系统管理后台 - " + staff.getEName());
        setSize(1000, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
    }

    private void initUI() {
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("主页", createHomePanel());
        tabbedPane.addTab("用户管理", createAccountPanel());
        tabbedPane.addTab("报修管理", createRepairPanel());
        tabbedPane.addTab("场馆管理", createVenuePanel());
        tabbedPane.addTab("预约审核", createReservationPanel());
        tabbedPane.addTab("校园卡管理", createCardPanel());

        this.add(tabbedPane);
    }

    private JPanel createHomePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("<html><div style='text-align: center;'><h2>欢迎管理员: " + staff.getEName() + "</h2><p>请点击上方标签页进行管理操作</p></div></html>", SwingConstants.CENTER);
        panel.add(welcomeLabel, BorderLayout.CENTER);

        JButton logoutBtn = new JButton("注销登录");
        logoutBtn.addActionListener(e -> {
            new LoginView().setVisible(true);
            this.dispose();
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(logoutBtn);
        panel.add(bottomPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createAccountPanel() {
        JTabbedPane userTab = new JTabbedPane();

        // --- Student Management ---
        JPanel studentPanel = new JPanel(new BorderLayout());
        String[] studentCols = {"学号", "姓名", "学院", "班级", "电话"};
        studentTableModel = new DefaultTableModel(studentCols, 0);
        JTable studentTable = new JTable(studentTableModel);
        studentPanel.add(new JScrollPane(studentTable), BorderLayout.CENTER);

        JPanel studentTools = new JPanel();
        JButton refreshStudents = new JButton("刷新");
        JButton addStudent = new JButton("添加学生");
        JButton delStudent = new JButton("删除学生");

        refreshStudents.addActionListener(e -> loadStudentData());
        addStudent.addActionListener(e -> showAddStudentDialog());
        delStudent.addActionListener(e -> {
            int row = studentTable.getSelectedRow();
            if (row >= 0) {
                String sid = (String) studentTableModel.getValueAt(row, 0);
                if (JOptionPane.showConfirmDialog(this, "确认删除学生 " + sid + " ?") == JOptionPane.YES_OPTION) {
                    userService.deleteUser(sid, "student");
                    loadStudentData();
                }
            } else {
                JOptionPane.showMessageDialog(this, "请选择一行");
            }
        });

        studentTools.add(refreshStudents);
        studentTools.add(addStudent);
        studentTools.add(delStudent);
        studentPanel.add(studentTools, BorderLayout.NORTH);

        // --- Staff Management ---
        JPanel staffPanel = new JPanel(new BorderLayout());
        String[] staffCols = {"工号", "姓名", "部门", "职位", "电话"};
        staffTableModel = new DefaultTableModel(staffCols, 0);
        JTable staffTable = new JTable(staffTableModel);
        staffPanel.add(new JScrollPane(staffTable), BorderLayout.CENTER);

        JPanel staffTools = new JPanel();
        JButton refreshStaff = new JButton("刷新");
        JButton addStaff = new JButton("添加职工");
        JButton delStaff = new JButton("删除职工");

        refreshStaff.addActionListener(e -> loadStaffData());
        addStaff.addActionListener(e -> showAddStaffDialog());
        delStaff.addActionListener(e -> {
            int row = staffTable.getSelectedRow();
            if (row >= 0) {
                String eid = (String) staffTableModel.getValueAt(row, 0);
                if (JOptionPane.showConfirmDialog(this, "确认删除职工 " + eid + " ?") == JOptionPane.YES_OPTION) {
                    userService.deleteUser(eid, "staff");
                    loadStaffData();
                }
            } else {
                JOptionPane.showMessageDialog(this, "请选择一行");
            }
        });

        staffTools.add(refreshStaff);
        staffTools.add(addStaff);
        staffTools.add(delStaff);
        staffPanel.add(staffTools, BorderLayout.NORTH);

        userTab.addTab("学生列表", studentPanel);
        userTab.addTab("职工列表", staffPanel);

        // Initial Load
        loadStudentData();
        loadStaffData();

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(userTab, BorderLayout.CENTER);
        return mainPanel;
    }

    private JPanel createRepairPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = {"维修单ID", "内容", "提交时间", "状态", "提交人", "处理人"};
        repairTableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(repairTableModel);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel tools = new JPanel();
        JButton refreshBtn = new JButton("刷新");
        JButton assignBtn = new JButton("分配处理人");
        JButton statsBtn = new JButton("查看统计");

        refreshBtn.addActionListener(e -> loadRepairData());
        assignBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int rid = (int) repairTableModel.getValueAt(row, 0);
                showSelectStaffDialog(rid);
            } else {
                 JOptionPane.showMessageDialog(this, "请选择一个维修单");
            }
        });
        statsBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "统计及功能请查看控制台输出 (Service层目前设计为打印)");
            repairService.getRepairStatistics();
        });

        tools.add(refreshBtn);
        tools.add(assignBtn);
        tools.add(statsBtn);
        panel.add(tools, BorderLayout.NORTH);

        loadRepairData();
        return panel;
    }

    private JPanel createVenuePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = {"ID", "名称", "容量", "位置", "可用性"};
        venueTableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(venueTableModel);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel tools = new JPanel();
        JButton refreshBtn = new JButton("刷新");
        JButton addBtn = new JButton("添加场馆");
        JButton delBtn = new JButton("删除");

        refreshBtn.addActionListener(e -> loadVenueData());

        addBtn.addActionListener(e -> showAddVenueDialog());

        delBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int vid = (int) venueTableModel.getValueAt(row, 0);
                if (JOptionPane.showConfirmDialog(this, "确认删除场馆 ID: " + vid + " ?") == JOptionPane.YES_OPTION) {
                    venueService.deleteVenue(vid);
                    loadVenueData();
                }
            } else {
                JOptionPane.showMessageDialog(this, "请选择一个场馆");
            }
        });

        tools.add(refreshBtn);
        tools.add(addBtn);
        tools.add(delBtn);
        panel.add(tools, BorderLayout.NORTH);

        loadVenueData();
        return panel;
    }

    private JPanel createReservationPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = {"预约ID", "场馆ID", "用户ID", "预约时间", "时长", "状态"};
        reservationTableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(reservationTableModel);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel tools = new JPanel();
        JButton refreshBtn = new JButton("刷新");
        JButton approveBtn = new JButton("通过");
        JButton rejectBtn = new JButton("拒绝");

        refreshBtn.addActionListener(e -> loadReservationData());
        approveBtn.addActionListener(e -> handleAudit(table, true));
        rejectBtn.addActionListener(e -> handleAudit(table, false));

        tools.add(refreshBtn);
        tools.add(approveBtn);
        tools.add(rejectBtn);
        panel.add(tools, BorderLayout.NORTH);

        loadReservationData();
        return panel;
    }

    private void handleAudit(JTable table, boolean pass) {
        int row = table.getSelectedRow();
        if (row >= 0) {
            int rid = (int) reservationTableModel.getValueAt(row, 0);
            reservationService.auditReservation(rid, pass);
            loadReservationData();
            JOptionPane.showMessageDialog(this, pass ? "已通过" : "已拒绝");
        } else {
            JOptionPane.showMessageDialog(this, "请选择预约申请");
        }
    }

    private JPanel createCardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = {"卡号", "余额", "状态", "用户ID", "用户类型"};
        cardTableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(cardTableModel);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel tools = new JPanel();
        JButton refreshBtn = new JButton("刷新");
        JButton rechargeBtn = new JButton("充值");
        JButton addBtn = new JButton("办卡");
        JButton recoverBtn = new JButton("解挂");
        JButton lossBtn = new JButton("挂失");

        refreshBtn.addActionListener(e -> loadCardData());
        rechargeBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                String cid = (String) cardTableModel.getValueAt(row, 0);
                String amountStr = JOptionPane.showInputDialog(this, "请输入充值金额:");
                try {
                    BigDecimal amount = new BigDecimal(amountStr);
                    cardService.recharge(cid, amount);
                    loadCardData();
                    JOptionPane.showMessageDialog(this, "充值成功");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "金额格式错误");
                }
            } else {
                 JOptionPane.showMessageDialog(this, "请选择校园卡");
            }
        });

        recoverBtn.addActionListener(e -> handleCardStatus(table, true));
        lossBtn.addActionListener(e -> handleCardStatus(table, false));

        addBtn.addActionListener(e -> showAddCardDialog());

        tools.add(refreshBtn);
        tools.add(addBtn);
        tools.add(rechargeBtn);
        tools.add(recoverBtn);
        tools.add(lossBtn);
        panel.add(tools, BorderLayout.NORTH);

        loadCardData();
        return panel;
    }

    private void handleCardStatus(JTable table, boolean recover) {
        int row = table.getSelectedRow();
        if (row >= 0) {
            String cid = (String) cardTableModel.getValueAt(row, 0);
            cardService.handleCardStatus(cid, recover);
            loadCardData();
            JOptionPane.showMessageDialog(this, recover ? "已设为正常" : "已设为挂失");
        } else {
            JOptionPane.showMessageDialog(this, "请选择校园卡");
        }
    }

    // --- Data Loading Methods ---

    private void loadStudentData() {
        studentTableModel.setRowCount(0);
        List<Student> list = userService.getAllStudents();
        for (Student s : list) {
            studentTableModel.addRow(new Object[]{s.getSid(), s.getSName(), s.getCollege(), s.getGrade(), s.getPhone()});
        }
    }

    private void loadStaffData() {
        staffTableModel.setRowCount(0);
        List<Staff> list = userService.getAllStaff();
        for (Staff s : list) {
            staffTableModel.addRow(new Object[]{s.getEid(), s.getEName(), s.getDept(), s.getPosition(), s.getPhone()});
        }
    }

    private void loadRepairData() {
        repairTableModel.setRowCount(0);
        List<Repair> list = repairService.getAllRepairs();
        for (Repair r : list) {
            repairTableModel.addRow(new Object[]{
                r.getRepairID(), r.getContent(), r.getSubmitTime(), r.getStatus(), r.getSubmitterID(), r.getHandlerID()
            });
        }
    }

    private void loadVenueData() {
        venueTableModel.setRowCount(0);
        List<Venue> list = venueService.getAllVenues();
        for (Venue v : list) {
            venueTableModel.addRow(new Object[]{
                v.getVenueID(), v.getVenueName(), v.getCapacity(), v.getLocation(), v.getIsAvailable()
            });
        }
    }

    private void loadReservationData() {
        reservationTableModel.setRowCount(0);
        List<Reservation> list = reservationService.getAllReservations();
        for (Reservation r : list) {
            reservationTableModel.addRow(new Object[]{
                r.getResID(), r.getVenueID(), r.getReserverID(), r.getResTime(), r.getDuration(), r.getAuditStatus()
            });
        }
    }

    private void loadCardData() {
        cardTableModel.setRowCount(0);
        List<CampusCard> list = cardService.getAllCards();
        for (CampusCard c : list) {
            cardTableModel.addRow(new Object[]{
                c.getCardID(), c.getBalance(), c.getStatus(), c.getUserID(), c.getUserType()
            });
        }
    }

    // --- Dialogs ---

    private void showAddStudentDialog() {
        JDialog d = new JDialog(this, "添加学生", true);
        d.setSize(300, 300);
        d.setLayout(new GridLayout(7, 2));
        d.setLocationRelativeTo(this);

        JTextField idF = new JTextField();
        JTextField nameF = new JTextField();
        JTextField colF = new JTextField();
        JTextField gradeF = new JTextField();
        JTextField phoneF = new JTextField();
        JTextField pwdF = new JTextField();

        d.add(new JLabel("学号:")); d.add(idF);
        d.add(new JLabel("姓名:")); d.add(nameF);
        d.add(new JLabel("学院:")); d.add(colF);
        d.add(new JLabel("班级:")); d.add(gradeF);
        d.add(new JLabel("电话:")); d.add(phoneF);
        d.add(new JLabel("密码:")); d.add(pwdF);

        JButton ok = new JButton("确定");
        ok.addActionListener(e -> {
            Student s = new Student(idF.getText(), nameF.getText(), colF.getText(), phoneF.getText(), gradeF.getText(), pwdF.getText());
            if (userService.addUser(s)) {
                JOptionPane.showMessageDialog(d, "添加成功");
                d.dispose();
                loadStudentData();
            } else {
                JOptionPane.showMessageDialog(d, "添加失败");
            }
        });
        d.add(ok);
        d.setVisible(true);
    }

    private void showAddStaffDialog() {
        JDialog d = new JDialog(this, "添加职工", true);
        d.setSize(300, 300);
        d.setLayout(new GridLayout(7, 2));
        d.setLocationRelativeTo(this);

        JTextField idF = new JTextField();
        JTextField nameF = new JTextField();
        JTextField deptF = new JTextField();
        JTextField posF = new JTextField();
        JTextField phoneF = new JTextField();
        JTextField pwdF = new JTextField();

        d.add(new JLabel("工号:")); d.add(idF);
        d.add(new JLabel("姓名:")); d.add(nameF);
        d.add(new JLabel("部门:")); d.add(deptF);
        d.add(new JLabel("职位:")); d.add(posF);
        d.add(new JLabel("电话:")); d.add(phoneF);
        d.add(new JLabel("密码:")); d.add(pwdF);

        JButton ok = new JButton("确定");
        ok.addActionListener(e -> {
            Staff s = new Staff(idF.getText(), nameF.getText(), deptF.getText(), posF.getText(), phoneF.getText(), pwdF.getText());
            if (userService.addUser(s)) {
                JOptionPane.showMessageDialog(d, "添加成功");
                d.dispose();
                loadStaffData();
            } else {
                JOptionPane.showMessageDialog(d, "添加失败");
            }
        });
        d.add(ok);
        d.setVisible(true);
    }

    private void showAddVenueDialog() {
        JDialog d = new JDialog(this, "添加场馆", true);
        d.setSize(300, 250);
        d.setLayout(new GridLayout(5, 2));
        d.setLocationRelativeTo(this);

        JTextField nameF = new JTextField();
        JTextField capF = new JTextField();
        JTextField locF = new JTextField();
        JComboBox<String> availBox = new JComboBox<>(new String[]{"是", "否"});

        d.add(new JLabel("名称:")); d.add(nameF);
        d.add(new JLabel("容量:")); d.add(capF);
        d.add(new JLabel("位置:")); d.add(locF);
        d.add(new JLabel("可用:")); d.add(availBox);

        JButton ok = new JButton("确定");
        ok.addActionListener(e -> {
            try {
                String name = nameF.getText().trim();
                int cap = Integer.parseInt(capF.getText().trim());
                String loc = locF.getText().trim();

                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(d, "名称不能为空");
                    return;
                }

                Venue v = new Venue();
                v.setVenueName(name);
                v.setCapacity(cap);
                v.setLocation(loc);
                v.setIsAvailable((String) availBox.getSelectedItem());

                venueService.addVenue(v);
                JOptionPane.showMessageDialog(d, "添加成功");
                d.dispose();
                loadVenueData();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(d, "容量必须为整数");
            }
        });
        d.add(ok);
        d.setVisible(true);
    }

    private void showAddCardDialog() {
        JDialog d = new JDialog(this, "办理校园卡", true);
        d.setSize(300, 250);
        d.setLayout(new GridLayout(5, 2));
        d.setLocationRelativeTo(this);

        JTextField cardIdF = new JTextField();
        JTextField userIdF = new JTextField();
        JComboBox<String> typeBox = new JComboBox<>(new String[]{"student", "staff"});
        JTextField balanceF = new JTextField("0");

        d.add(new JLabel("卡号:")); d.add(cardIdF);
        d.add(new JLabel("用户ID:")); d.add(userIdF);
        d.add(new JLabel("类型:")); d.add(typeBox);
        d.add(new JLabel("初始余额:")); d.add(balanceF);

        JButton ok = new JButton("确定");
        ok.addActionListener(e -> {
            CampusCard c = new CampusCard();
            c.setCardID(cardIdF.getText());
            c.setUserID(userIdF.getText());
            c.setUserType((String) typeBox.getSelectedItem());
            c.setBalance(new BigDecimal(balanceF.getText()));
            c.setStatus("正常");

            if (cardService.addCard(c)) {
                JOptionPane.showMessageDialog(d, "办卡成功");
                d.dispose();
                loadCardData();
            } else {
                JOptionPane.showMessageDialog(d, "办卡失败");
            }
        });
        d.add(ok);
        d.setVisible(true);
    }

    private void showSelectStaffDialog(int repairId) {
        JDialog d = new JDialog(this, "选择处理人", true);
        d.setSize(500, 400);
        d.setLayout(new BorderLayout());
        d.setLocationRelativeTo(this);

        String[] cols = {"工号", "姓名", "部门", "职位"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        JTable table = new JTable(model);

        // Load staff
        List<Staff> list = userService.getAllStaff();
        for (Staff s : list) {
            model.addRow(new Object[]{s.getEid(), s.getEName(), s.getDept(), s.getPosition()});
        }

        d.add(new JScrollPane(table), BorderLayout.CENTER);

        JButton ok = new JButton("确定分配");
        ok.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                String eid = (String) model.getValueAt(row, 0);
                repairService.assignHandler(repairId, eid);
                repairService.updateRepairStatus(repairId, "处理中");
                JOptionPane.showMessageDialog(d, "已分配给: " + model.getValueAt(row, 1));
                d.dispose();
                loadRepairData();
            } else {
                JOptionPane.showMessageDialog(d, "请选择一名职工");
            }
        });

        JPanel btnPanel = new JPanel();
        btnPanel.add(ok);
        d.add(btnPanel, BorderLayout.SOUTH);

        d.setVisible(true);
    }
}

