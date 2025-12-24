package src.service;

import src.dao.CampusCardDAO;
import src.entity.CampusCard;
import java.util.List;

/**
 * CampusCardService：校园卡业务层
 * 封装校园卡相关的业务操作，为 View 层提供简洁接口
 */
public class CampusCardService {
    private CampusCardDAO campusCardDAO = new CampusCardDAO();

    /**
     * 根据用户 ID 和类型查询校园卡
     */
    public CampusCard selectByUserIdAndType(String userId, String userType) {
        return campusCardDAO.selectByUserIdAndType(userId, userType);
    }

    /**
     * 将指定用户的卡状态设置为挂失（业务层包装，内部调用 DAO）
     * @param userId 用户 ID
     * @param userType 用户类型
     */
    public void updateStatusToLost(String userId, String userType) {
        // TODO
        // 实现说明：
        // 1. 参数校验：userId, userType 非空
        // 2. 可以先通过 campusCardDAO.selectByUserIdAndType 确认卡存在
        // 3. 调用 campusCardDAO.updateStatus(userId, userType, "挂失")
        // 4. 若操作成功，可发送通知或记录日志
        // 5. 异常处理：捕获 SQLException 并向上抛出业务异常或返回失败信息
    }

    /**
     * 查询所有挂失的校园卡（管理端展示）
     */
    public List<CampusCard> selectLostCards() {
        return campusCardDAO.selectLostCards();
    }

    /**
     * 根据卡号恢复卡片（将状态改为正常）
     * @param cardId 卡号
     */
    public void recoverCard(String cardId) {
        // TODO
        // 实现说明：
        // 1. 参数校验：cardId 非空
        // 2. 调用 campusCardDAO.updateStatusByCardId(cardId, "正常")
        // 3. 可返回是否成功或抛出异常说明失败原因
    }
}
