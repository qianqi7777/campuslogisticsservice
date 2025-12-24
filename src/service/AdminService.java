package src.service;

import src.dao.StaffDAO;
import src.entity.Staff;

public class AdminService {
    private StaffDAO staffDAO = new StaffDAO();

    public Staff login(String eid, String password) {
        return staffDAO.selectByEidAndPwd(eid, password);
    }

    public void statRepairByCollege() {
        // TODO
    }

    public void statRepairEvaluation() {
        // TODO
    }
}
