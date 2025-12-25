package src.service;

import src.dao.LostItemDAO;
import src.entity.LostItem;

/**
 * LostItemService：失物招领业务层
 * 提供发布失物信息的业务接口（对 DAO 的包装）
 */
public class LostItemService {
    private LostItemDAO lostItemDAO = new LostItemDAO();

    /**
     * 发布失物招领信息
     * @param lostItem 要发布的失物实体
     */
    public void addLostItem(LostItem lostItem) {
        // 参数校验
        if (lostItem == null
            || lostItem.getItemName() == null || lostItem.getItemName().trim().isEmpty()
            || lostItem.getPlace() == null || lostItem.getPlace().trim().isEmpty()
            || lostItem.getPublisherID() == null || lostItem.getPublisherID().trim().isEmpty()) {
            throw new IllegalArgumentException("失物信息不完整");
        }
        // 默认状态为未认领
        if (lostItem.getStatus() == null) {
            lostItem.setStatus("未认领");
        }
        // 调用 DAO 插入记录
        lostItemDAO.insertLostItem(lostItem);
    }
}
