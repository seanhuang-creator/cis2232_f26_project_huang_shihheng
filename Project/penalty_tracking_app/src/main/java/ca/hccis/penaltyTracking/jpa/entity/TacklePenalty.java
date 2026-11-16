package ca.hccis.penaltyTracking.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.AssertTrue;
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
        this.quarter = null;
        this.impactScore = 0.0;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(min = 1, max = 50, message = "Home Team is required")
    @NotNull(message = "Home Team is required")
    @Column(name = "homeTeam", nullable = false, length = 50)
    private String homeTeam;

    @Size(min = 1, max = 50, message = "Away Team is required")
    @NotNull(message = "Away Team is required")
    @Column(name = "awayTeam", nullable = false, length = 50)
    private String awayTeam;

    @NotBlank(message = "Game Date is required")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Game Date must be yyyy-MM-dd")
    @Column(name = "date", nullable = false, length = 10)
    private String date;

    @Size(min = 1, max = 50, message = "Penalty Type is required")
    @NotNull(message = "Penalty Type is required")
    @Column(name = "penalty", nullable = false, length = 50)
    private String penalty;

    @NotNull(message = "Quarter is required")
    @Min(value = 1, message = "Quarter must be between 1 and 4")
    @Max(value = 4, message = "Quarter must be between 1 and 4")
    @Column(name = "quarter", nullable = false)
    private Integer quarter;

    @Size(min = 1, max = 50, message = "Penalized Team is required")
    @NotNull(message = "Penalized Team is required")
    @Column(name = "penalizedTeam", nullable = false, length = 50)
    private String penalizedTeam;

    @NotNull(message = "Offending Player # is required")
    @Min(value = 0, message = "Offending Player # must be between 0 and 99")
    @Max(value = 99, message = "Offending Player # must be between 0 and 99")
    @Column(name = "offendingPlayer")
    private Integer offendingPlayer;

    @Size(min = 1, max = 20, message = "Age Division is required")
    @NotNull(message = "Age Division is required")
    @Column(name = "ageDivision", nullable = false, length = 20)
    private String ageDivision;

    @Size(min = 1, max = 50, message = "Referee is required")
    @NotNull(message = "Referee is required")
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

    @AssertTrue(message = "Away Team cannot be the same as Home Team.")
    public boolean isHomeAwayDifferent() {
        if (homeTeam == null || awayTeam == null) return true;
        if (homeTeam.isBlank() || awayTeam.isBlank()) return true;
        return !homeTeam.equalsIgnoreCase(awayTeam);
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
