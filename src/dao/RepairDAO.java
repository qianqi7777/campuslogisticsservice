package src.dao;

import src.entity.Repair;
import src.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepairDAO {
    public void insertRepair(Repair repair) {
        // TODO
    }

    public Repair selectById(int repairId) {
        // TODO
        return null;
    }

    public List<Repair> selectBySubmitterIdAndStatus(String submitterId, String status) {
        // TODO
        return new ArrayList<>();
    }

    public List<Repair> selectAll() {
        // TODO
        return new ArrayList<>();
    }

    public void updateHandlerAndStatus(int repairId, String handlerId, String status) {
        // TODO
    }

    public void updateStatus(int repairId, String status) {
        // TODO
    }

    public String selectHandlerIdByRepairId(int repairId) {
        // TODO
        return null;
    }
}
