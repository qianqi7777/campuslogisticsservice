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
        if (venueDAO.insertVenue(venue)) {
            System.out.println("添加场馆成功");
        } else {
            System.out.println("添加场馆失败");
        }
    }

    /**
     * 更新场馆信息 (职工/管理员)
     * @param venue 场馆对象
     */
    public void updateVenue(Venue venue) {
        if (venueDAO.updateVenue(venue)) {
            System.out.println("更新场馆成功");
        } else {
            System.out.println("更新场馆失败");
        }
    }

    /**
     * 删除场馆 (职工/管理员)
     * @param venueId 场馆ID
     */
    public void deleteVenue(int venueId) {
        if (venueDAO.deleteVenue(venueId)) {
            System.out.println("删除场馆成功");
        } else {
            System.out.println("删除场馆失败");
        }
    }

    /**
     * 获取所有场馆 (职工/管理员)
     * @return 场馆列表
     */
    public List<Venue> getAllVenues() {
        // 调用 DAO 查询所有
        return venueDAO.selectAll();
    }
}
