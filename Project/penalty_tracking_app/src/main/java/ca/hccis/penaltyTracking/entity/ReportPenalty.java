package ca.hccis.penaltyTracking.entity;

import ca.hccis.penaltyTracking.jpa.entity.TacklePenalty;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity class to hold the attributes of the penalty reports.
 * Extended with chart data for Referee/Team report.
 * @author Huang Shihheng
 * @since 20260925
 */
public class ReportPenalty {
    private String name;
    private double minScore;
    private double maxScore;
    private ArrayList<TacklePenalty> tacklePenalties;

    // Chart data - penalty count
    private List<String> penaltyTypeLabels;
    private List<Long> penaltyCountData;

    // Chart data - average impact by penalty
    private List<String> penaltyAvgLabels;
    private List<Double> penaltyAvgData;

    // Chart data - quarter distribution
    private List<String> quarterLabels;
    private List<Long> quarterCountData;

    // Chart data - penalized team
    private List<String> teamLabels;
    private List<Long> teamCountData;

    // Chart data - date trend
    private List<String> dateLabels;
    private List<Long> dateCountData;
    private List<Double> dateImpactData;

    // Chart data - age division
    private List<String> ageDivisionLabels;
    private List<Long> ageDivisionCountData;
    private List<Double> ageDivisionAvgData;

    // KPI
    private int totalCount;
    private double averageImpact;
    private double maxImpact;

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

    public List<String> getPenaltyTypeLabels() {
        return penaltyTypeLabels;
    }

    public void setPenaltyTypeLabels(List<String> penaltyTypeLabels) {
        this.penaltyTypeLabels = penaltyTypeLabels;
    }

    public List<Long> getPenaltyCountData() {
        return penaltyCountData;
    }

    public void setPenaltyCountData(List<Long> penaltyCountData) {
        this.penaltyCountData = penaltyCountData;
    }

    public List<String> getPenaltyAvgLabels() {
        return penaltyAvgLabels;
    }

    public void setPenaltyAvgLabels(List<String> penaltyAvgLabels) {
        this.penaltyAvgLabels = penaltyAvgLabels;
    }

    public List<Double> getPenaltyAvgData() {
        return penaltyAvgData;
    }

    public void setPenaltyAvgData(List<Double> penaltyAvgData) {
        this.penaltyAvgData = penaltyAvgData;
    }

    public List<String> getQuarterLabels() {
        return quarterLabels;
    }

    public void setQuarterLabels(List<String> quarterLabels) {
        this.quarterLabels = quarterLabels;
    }

    public List<Long> getQuarterCountData() {
        return quarterCountData;
    }

    public void setQuarterCountData(List<Long> quarterCountData) {
        this.quarterCountData = quarterCountData;
    }

    public List<String> getTeamLabels() {
        return teamLabels;
    }

    public void setTeamLabels(List<String> teamLabels) {
        this.teamLabels = teamLabels;
    }

    public List<Long> getTeamCountData() {
        return teamCountData;
    }

    public void setTeamCountData(List<Long> teamCountData) {
        this.teamCountData = teamCountData;
    }

    public List<String> getDateLabels() {
        return dateLabels;
    }

    public void setDateLabels(List<String> dateLabels) {
        this.dateLabels = dateLabels;
    }

    public List<Long> getDateCountData() {
        return dateCountData;
    }

    public void setDateCountData(List<Long> dateCountData) {
        this.dateCountData = dateCountData;
    }

    public List<Double> getDateImpactData() {
        return dateImpactData;
    }

    public void setDateImpactData(List<Double> dateImpactData) {
        this.dateImpactData = dateImpactData;
    }

    public List<String> getAgeDivisionLabels() {
        return ageDivisionLabels;
    }

    public void setAgeDivisionLabels(List<String> ageDivisionLabels) {
        this.ageDivisionLabels = ageDivisionLabels;
    }

    public List<Long> getAgeDivisionCountData() {
        return ageDivisionCountData;
    }

    public void setAgeDivisionCountData(List<Long> ageDivisionCountData) {
        this.ageDivisionCountData = ageDivisionCountData;
    }

    public List<Double> getAgeDivisionAvgData() {
        return ageDivisionAvgData;
    }

    public void setAgeDivisionAvgData(List<Double> ageDivisionAvgData) {
        this.ageDivisionAvgData = ageDivisionAvgData;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public double getAverageImpact() {
        return averageImpact;
    }

    public void setAverageImpact(double averageImpact) {
        this.averageImpact = averageImpact;
    }

    public double getMaxImpact() {
        return maxImpact;
    }

    public void setMaxImpact(double maxImpact) {
        this.maxImpact = maxImpact;
    }
}