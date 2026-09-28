package ca.hccis.penaltyTracking.entity;

import ca.hccis.penaltyTracking.jpa.entity.TacklePenalty;

import java.util.ArrayList;

/**
 * Entity class to hold the attributes of the penalty reports.
 * @author Huang Shihheng
 * @since 20260925
 */
public class ReportPenalty {
    private String name;
    private double minScore;
    private double maxScore;
    private ArrayList<TacklePenalty> tacklePenalties;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMinScore() {
        return minScore;
    }

    public void setMinScore(double minScore) {
        this.minScore = minScore;
    }

    public double getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(double maxScore) {
        this.maxScore = maxScore;
    }

    public ArrayList<TacklePenalty> getTacklePenalties() {
        return tacklePenalties;
    }

    public void setTacklePenalties(ArrayList<TacklePenalty> tacklePenalties) {
        this.tacklePenalties = tacklePenalties;
    }
}
