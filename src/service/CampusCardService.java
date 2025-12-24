package src.service;

import src.dao.CampusCardDAO;
import src.entity.CampusCard;
import java.util.List;

public class CampusCardService {
    private CampusCardDAO campusCardDAO = new CampusCardDAO();

    public CampusCard selectByUserIdAndType(String userId, String userType) {
        return campusCardDAO.selectByUserIdAndType(userId, userType);
    }

    public void updateStatusToLost(String userId, String userType) {
        // TODO
    }

    public List<CampusCard> selectLostCards() {
        return campusCardDAO.selectLostCards();
    }

    public void recoverCard(String cardId) {
        // TODO
    }
}
