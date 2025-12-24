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
        // TODO
    }
}
