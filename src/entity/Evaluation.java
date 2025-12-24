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
    private int evalID;
    private int repairID;
    private int score;
    private String comment;

    public Evaluation() {}

    // getter / setter
    public int getEvalID() { return evalID; }
    public void setEvalID(int evalID) { this.evalID = evalID; }

    public int getRepairID() { return repairID; }
    public void setRepairID(int repairID) { this.repairID = repairID; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}
