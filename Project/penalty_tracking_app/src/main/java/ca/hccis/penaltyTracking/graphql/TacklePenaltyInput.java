package ca.hccis.penaltyTracking.graphql;

import ca.hccis.penaltyTracking.jpa.entity.TacklePenalty;

public class TacklePenaltyInput {

    private String homeTeam;
    private String awayTeam;
    private String date;
    private String penalty;
    private Integer quarter;
    private String penalizedTeam;
    private Integer offendingPlayer;
    private String ageDivision;
    private String referee;

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPenalty() {
        return penalty;
    }

    public void setPenalty(String penalty) {
        this.penalty = penalty;
    }

    public Integer getQuarter() {
        return quarter;
    }

    public void setQuarter(Integer quarter) {
        this.quarter = quarter;
    }

    public String getPenalizedTeam() {
        return penalizedTeam;
    }

    public void setPenalizedTeam(String penalizedTeam) {
        this.penalizedTeam = penalizedTeam;
    }

    public Integer getOffendingPlayer() {
        return offendingPlayer;
    }

    public void setOffendingPlayer(Integer offendingPlayer) {
        this.offendingPlayer = offendingPlayer;
    }

    public String getAgeDivision() {
        return ageDivision;
    }

    public void setAgeDivision(String ageDivision) {
        this.ageDivision = ageDivision;
    }

    public String getReferee() {
        return referee;
    }

    public void setReferee(String referee) {
        this.referee = referee;
    }

    public TacklePenalty toEntity() {
        TacklePenalty e = new TacklePenalty();
        e.setHomeTeam(this.homeTeam);
        e.setAwayTeam(this.awayTeam);
        e.setDate(this.date);
        e.setPenalty(this.penalty);
        e.setQuarter(this.quarter);
        e.setPenalizedTeam(this.penalizedTeam);
        e.setOffendingPlayer(this.offendingPlayer);
        e.setAgeDivision(this.ageDivision);
        e.setReferee(this.referee);
        // impactScore will be computed in BO before save
        return e;
    }
}