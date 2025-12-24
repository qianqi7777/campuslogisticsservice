package src.service;

import src.dao.VenueDAO;
import src.entity.Venue;
import java.util.List;

public class VenueService {
    private VenueDAO venueDAO = new VenueDAO();

    public List<Venue> selectAvailableVenues() {
        return venueDAO.selectAvailableVenues();
    }
}
