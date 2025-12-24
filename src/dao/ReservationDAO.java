package src.dao;

import src.entity.Reservation;
import src.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {
    public void insertReservation(Reservation reservation) {
        // TODO
    }

    public Reservation selectById(int resId) {
        // TODO
        return null;
    }

    public List<Reservation> selectByReserverId(String reserverId) {
        // TODO
        return new ArrayList<>();
    }

    public void updateAuditStatus(int resId, String auditStatus) {
        // TODO
    }
}
