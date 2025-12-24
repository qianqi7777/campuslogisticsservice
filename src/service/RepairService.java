package src.service;

import src.dao.RepairDAO;
import src.dao.StaffDAO;
import src.entity.Repair;

public class RepairService {
    private RepairDAO repairDAO = new RepairDAO();
    private StaffDAO staffDAO = new StaffDAO();

    public void addRepair(Repair repair) {
        // TODO
    }

    public void assignRepairTask(int repairId, String handlerId) {
        // TODO
    }

    public void updateToCompleted(int repairId) {
        // TODO
    }
}
