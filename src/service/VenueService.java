package src.service;

import src.dao.VenueDAO;
import src.entity.Venue;
import java.util.List;

/**
 * VenueService：场馆业务层
 * 提供场馆查询等简单服务接口
 */
public class VenueService {
    private VenueDAO venueDAO = new VenueDAO();

    /**
     * 查询所有可用的场馆
     * @return Venue 列表
     */
    public List<Venue> selectAvailableVenues() {
        return venueDAO.selectAvailableVenues();
    }
}
