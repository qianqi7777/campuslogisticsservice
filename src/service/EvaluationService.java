package src.service;

import src.dao.EvaluationDAO;
import src.dao.RepairDAO;
import src.entity.Evaluation;

public class EvaluationService {
    private EvaluationDAO evaluationDAO = new EvaluationDAO();
    private RepairDAO repairDAO = new RepairDAO();

    public void addEvaluation(Evaluation evaluation, String submitterId) {
        // TODO
    }
}
