package src.service;

import src.dao.CampusCardDAO;
import src.entity.CampusCard;
import java.math.BigDecimal;

/**
 * 校园卡服务类
 * 处理充值、查询、挂失、恢复
 */
public class CampusCardService {
    private CampusCardDAO cardDAO = new CampusCardDAO();

    // --- 学生/职工端功能 ---

    /**
     * 查询账户信息
     * @param userId 用户ID
     * @return 校园卡信息
     */
    public CampusCard getCardInfo(String userId) {
        // TODO: 查询校园卡
        return null;
    }

    /**
     * 充值
     * @param cardId 卡号
     * @param amount 金额
     */
    public void recharge(String cardId, BigDecimal amount) {
        // TODO: 更新余额
    }

    /**
     * 挂失 (用户自行挂失)
     * @param cardId 卡号
     */
    public void reportLoss(String cardId) {
        // TODO: 更新状态为挂失
    }

    // --- 管理员功能 ---

    /**
     * 处理挂失/恢复 (管理员)
     * @param cardId 卡号
     * @param recover 是否恢复
     */
    public void handleCardStatus(String cardId, boolean recover) {
        // TODO: 如果recover为true，恢复正常；否则挂失
    }

    /**
     * 获取所有校园卡信息 (管理员)
     * @return 校园卡列表
     */
    public java.util.List<CampusCard> getAllCards() {
        // TODO: 调用 DAO 查询所有
        return null;
    }

    /**
     * 删除校园卡 (管理员)
     * @param cardId 卡号
     */
    public void deleteCard(String cardId) {
        // TODO: 调用 DAO 删除
    }
}
