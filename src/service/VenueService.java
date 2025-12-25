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
        // 调用 DAO 查询所有可用场馆
        return venueDAO.selectAvailableVenues();
    }

    /**
     * 添加场馆 (职工/管理员)
     * @param venue 场馆对象
     */
    public void addVenue(Venue venue) {
        // TODO: 调用 DAO 插入
    }

    /**
     * 更新场馆信息 (职工/管理员)
     * @param venue 场馆对象
     */
    public void updateVenue(Venue venue) {
        // TODO: 调用 DAO 更新
    }

    /**
     * 删除场馆 (职工/管理员)
     * @param venueId 场馆ID
     */
    public void deleteVenue(int venueId) {
        // TODO: 调用 DAO 删除
    }

    /**
     * 获取所有场馆 (职工/管理员)
     * @return 场馆列表
     */
    public List<Venue> getAllVenues() {
        // TODO: 调用 DAO 查询所有
        return venueDAO.selectAll();
    }
}
