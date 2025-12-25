package src.entity;

/**
 * Evaluation 实体类
 * 对应数据库中的 Evaluation 表，用于存储维修单的评价信息
 * 字段说明：
 *  - evalID: 评价 ID（主键，自增）
 *  - repairID: 对应的维修单 ID
 *  - score: 评分（通常 1-5）
 *  - comment: 评价文本内容
 */
public class Evaluation {
    /** 评价 ID（主键） */
    private int evalID;
    /** 对应的维修单 ID */
    private int repairID;
    /** 评分 */
    private int score;
    /** 评价内容 */
    private String comment;

    public Evaluation() {}

    // getter / setter
    /**
     * 获取评价 ID
     * @return 评价 ID
     */
    public int getEvalID() { return evalID; }
    /**
     * 设置评价 ID
     * @param evalID 评价 ID
     */
    public void setEvalID(int evalID) { this.evalID = evalID; }

    /**
     * 获取维修单 ID
     * @return 维修单 ID
     */
    public int getRepairID() { return repairID; }
    /**
     * 设置维修单 ID
     * @param repairID 维修单 ID
     */
    public void setRepairID(int repairID) { this.repairID = repairID; }

    /**
     * 获取评分
     * @return 评分
     */
    public int getScore() { return score; }
    /**
     * 设置评分
     * @param score 评分
     */
    public void setScore(int score) { this.score = score; }

    /**
     * 获取评价内容
     * @return 评价内容
     */
    public String getComment() { return comment; }
    /**
     * 设置评价内容
     * @param comment 评价内容
     */
    public void setComment(String comment) { this.comment = comment; }
}
