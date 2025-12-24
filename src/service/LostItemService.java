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
        // 实现说明：
        // 1. 参数校验：检查 itemName, place, publisherID 非空
        // 2. 设置默认状态（如 "未认领"）和发布时间（若由数据库处理可省略）
        // 3. 调用 lostItemDAO.insertLostItem(lostItem)
        // 4. 关闭/处理异常并可返回插入成功与否
    }
}
