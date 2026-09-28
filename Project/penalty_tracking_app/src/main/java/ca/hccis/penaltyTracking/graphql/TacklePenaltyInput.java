package ca.hccis.penaltyTracking.graphql;

import ca.hccis.penaltyTracking.jpa.entity.TacklePenalty;

public class TacklePenaltyInput {
    public String homeTeam;
    public String awayTeam;
    public String date;
    public String penalty;
    public Integer quarter;
    public String penalizedTeam;
    public Integer offendingPlayer;
    public String ageDivision;
    public String referee;

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
