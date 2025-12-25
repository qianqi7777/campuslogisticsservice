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
        // 尝试查找学生卡
        CampusCard card = cardDAO.selectByUserIdAndType(userId, "student");
        if (card == null) {
            // 尝试查找职工卡
            card = cardDAO.selectByUserIdAndType(userId, "staff");
        }
        if (card != null) {
            System.out.println("卡号: " + card.getCardID() + ", 余额: " + card.getBalance() + ", 状态: " + card.getStatus());
        } else {
            System.out.println("未找到校园卡信息");
        }
        return card;
    }

    /**
     * 充值
     * @param cardId 卡号
     * @param amount 金额
     */
    public void recharge(String cardId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("充值金额必须大于0");
            return;
        }
        if (cardDAO.updateBalance(cardId, amount.doubleValue())) {
            System.out.println("充值成功");
        } else {
            System.out.println("充值失败，卡号可能不存在");
        }
    }

    /**
     * 挂失 (用户自行挂失)
     * @param cardId 卡号
     */
    public void reportLoss(String cardId) {
        cardDAO.updateStatusByCardId(cardId, "挂失");
        System.out.println("已提交挂失请求");
    }

    // --- 管理员功能 ---

    /**
     * 处理挂失/恢复 (管理员)
     * @param cardId 卡号
     * @param recover 是否恢复
     */
    public void handleCardStatus(String cardId, boolean recover) {
        String status = recover ? "正常" : "挂失";
        cardDAO.updateStatusByCardId(cardId, status);
        System.out.println("校园卡状态已更新为: " + status);
    }

    /**
     * 获取所有校园卡信息 (管理员)
     * @return 校园卡列表
     */
    public java.util.List<CampusCard> getAllCards() {
        return cardDAO.selectAll();
    }

    /**
     * 删除校园卡 (管理员)
     * @param cardId 卡号
     */
    public void deleteCard(String cardId) {
        if (cardDAO.deleteCard(cardId)) {
            System.out.println("删除成功");
        } else {
            System.out.println("删除失败");
        }
    }
}
