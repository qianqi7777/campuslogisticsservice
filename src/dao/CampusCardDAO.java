package src.dao;

import src.entity.CampusCard;
import src.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CampusCardDAO {
    public CampusCard selectByUserIdAndType(String userId, String userType) {
        // TODO
        return null;
    }

    public void updateStatus(String userId, String userType, String status) {
        // TODO
    }

    public CampusCard selectByCardId(String cardId) {
        // TODO
        return null;
    }

    public void updateStatusByCardId(String cardId, String status) {
        // TODO
    }

    public List<CampusCard> selectLostCards() {
        // TODO
        return new ArrayList<>();
    }
}
