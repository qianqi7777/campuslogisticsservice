package src.entity;

public class Evaluation {
    private int evalID;
    private int repairID;
    private int score;
    private String comment;

    public Evaluation() {}

    public int getEvalID() { return evalID; }
    public void setEvalID(int evalID) { this.evalID = evalID; }

    public int getRepairID() { return repairID; }
    public void setRepairID(int repairID) { this.repairID = repairID; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}
