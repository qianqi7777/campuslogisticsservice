package src.service;

import src.dao.ReservationDAO;
import src.dao.VenueDAO;
import src.entity.Reservation;
import java.util.List;

public class ReservationService {
    private ReservationDAO reservationDAO = new ReservationDAO();
    private VenueDAO venueDAO = new VenueDAO();

    public void addReservation(Reservation reservation) {
        // TODO
    }

    public List<Reservation> selectByReserverId(String reserverId) {
        return reservationDAO.selectByReserverId(reserverId);
    }

    public void updateAuditStatus(int resId, String auditStatus) {
        // TODO
    }
}
