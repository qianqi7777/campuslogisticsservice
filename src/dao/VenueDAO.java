package src.dao;

import src.entity.Venue;
import src.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VenueDAO {
    public Venue selectById(int venueId) {
        // TODO
        return null;
    }

    public List<Venue> selectAvailableVenues() {
        // TODO
        return new ArrayList<>();
    }

    public void updateIsAvailable(int venueId, String isAvailable) {
        // TODO
    }
}
