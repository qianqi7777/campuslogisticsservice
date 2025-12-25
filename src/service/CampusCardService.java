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
        // 调用 DAO 查询校园卡信息
        return campusCardDAO.selectByUserIdAndType(userId, userType);
    }

    /**
     * 将指定用户的卡状态设置为挂失（业务层包装，内部调用 DAO）
     * @param userId 用户 ID
     * @param userType 用户类型
     */
    public void updateStatusToLost(String userId, String userType) {
        // 参数校验
        if (userId == null || userId.trim().isEmpty() || userType == null || userType.trim().isEmpty()) {
            throw new IllegalArgumentException("参数不合法");
        }
        // 确认卡存在
        if (campusCardDAO.selectByUserIdAndType(userId, userType) == null) {
            throw new RuntimeException("未找到对应的校园卡记录");
        }
        // 更新状态为挂失
        campusCardDAO.updateStatus(userId, userType, "挂失");
    }

    /**
     * 查询所有挂失的校园卡（管理端展示）
     */
    public List<CampusCard> selectLostCards() {
        // 调用 DAO 查询所有挂失卡
        return campusCardDAO.selectLostCards();
    }

    /**
     * 根据卡号恢复卡片（将状态改为正常）
     * @param cardId 卡号
     */
    public void recoverCard(String cardId) {
        // 参数校验
        if (cardId == null || cardId.trim().isEmpty()) {
            throw new IllegalArgumentException("cardId 不能为空");
        }
        // 确认卡存在
        if (campusCardDAO.selectByCardId(cardId) == null) {
            throw new RuntimeException("未找到对应的校园卡");
        }
        // 更新状态为正常
        campusCardDAO.updateStatusByCardId(cardId, "正常");
    }
}
