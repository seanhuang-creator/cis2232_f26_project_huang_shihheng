package ca.hccis.penaltyTracking.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Objects;

/**
 * JPA entity representing one penalty recorded from a PEI amateur
 * tackle football game (see the project topic document fields).
 */
@Entity
@Table(name = "TacklePenalty")
public class TacklePenalty {

    //This is added to this jpa entity class as an example of @Transient.  If you add attributes to this
    //class that are not to be persisted to the database, they should be @Transient.
    @Transient
    private String searchName;

    public TacklePenalty() {
        this.quarter = 0;
        this.impactScore = 0.0;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(min = 1, max = 50)
    @NotNull
    @Column(name = "homeTeam", nullable = false, length = 50)
    private String homeTeam;

    @Size(min = 1, max = 50)
    @NotNull
    @Column(name = "awayTeam", nullable = false, length = 50)
    private String awayTeam;

    @Size(min = 1, max = 10)
    @NotNull
    @Column(name = "date", nullable = false, length = 10)
    private String date;

    @Size(min = 1, max = 50)
    @NotNull
    @Column(name = "penalty", nullable = false, length = 50)
    private String penalty;

    @NotNull
    @Column(name = "quarter", nullable = false)
    private Integer quarter;

    @Size(min = 1, max = 50)
    @NotNull
    @Column(name = "penalizedTeam", nullable = false, length = 50)
    private String penalizedTeam;

    @Column(name = "offendingPlayer")
    private Integer offendingPlayer;

    @Size(min = 1, max = 20)
    @NotNull
    @Column(name = "ageDivision", nullable = false, length = 20)
    private String ageDivision;

    @Size(min = 1, max = 50)
    @NotNull
    @Column(name = "referee", nullable = false, length = 50)
    private String referee;

    @Column(name = "impactScore")
    private Double impactScore;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Double getImpactScore() {
        return impactScore;
    }

    public void setImpactScore(Double impactScore) {
        this.impactScore = impactScore;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    @Override
    public String toString() {
        return "TacklePenalty\n" +
                "    id             = " + id + ",\n" +
                "    homeTeam       = '" + homeTeam + "',\n" +
                "    awayTeam       = '" + awayTeam + "',\n" +
                "    date           = '" + date + "',\n" +
                "    penalty        = '" + penalty + "',\n" +
                "    quarter        = " + quarter + ",\n" +
                "    penalizedTeam  = '" + penalizedTeam + "',\n" +
                "    offendingPlayer= " + offendingPlayer + ",\n" +
                "    ageDivision    = '" + ageDivision + "',\n" +
                "    referee        = '" + referee + "',\n" +
                "    impactScore    = " + impactScore + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TacklePenalty)) return false;
        TacklePenalty that = (TacklePenalty) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
