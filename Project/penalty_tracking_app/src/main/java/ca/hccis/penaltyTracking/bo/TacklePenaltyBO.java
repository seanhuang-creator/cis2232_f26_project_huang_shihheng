package ca.hccis.penaltyTracking.bo;

import ca.hccis.penaltyTracking.dao.TacklePenaltyDAO;
import ca.hccis.penaltyTracking.jpa.entity.TacklePenalty;
import ca.hccis.penaltyTracking.util.CisUtilityFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Business object for the Tackle Penalty entity.
 */
public class TacklePenaltyBO {

    //Base severity weights for each type of infraction.
    //Safety related infractions (unnecessary roughness / personal foul)
    //have the highest severity, minor procedural infractions the lowest.
    public static final double SEVERITY_MOUTH_GUARD_WARNING = 0.5;
    public static final double SEVERITY_MINOR = 1.0;
    public static final double SEVERITY_MODERATE = 2.0;
    public static final double SEVERITY_PASS_INTERFERENCE = 3.0;
    public static final double SEVERITY_SAFETY_RELATED = 5.0;
    public static final double SEVERITY_DEFAULT = 2.0;

    // The later in the game the flag is thrown, the higher its potential impact.
    // Multipliers based on quarter: Q1=1.0x, Q2=1.25x, Q3=1.5x, Q4=2.0x.
    private static final double[] QUARTER_MULTIPLIERS = {0.0, 1.0, 1.25, 1.5, 2.0};

    private static final Map<String, Double> PENALTY_SEVERITIES = new HashMap<>();

    static {
        //Minor procedural infractions
        PENALTY_SEVERITIES.put("Offside", SEVERITY_MINOR);
        PENALTY_SEVERITIES.put("No Yards", SEVERITY_MINOR);
        PENALTY_SEVERITIES.put("Time Count Violation", SEVERITY_MINOR);
        PENALTY_SEVERITIES.put("Illegal Kick Out of Bounds", SEVERITY_MINOR);
        PENALTY_SEVERITIES.put("Illegal Procedure", SEVERITY_MINOR);
        PENALTY_SEVERITIES.put("Procedure", SEVERITY_MINOR);
        PENALTY_SEVERITIES.put("Intentional Grounding", SEVERITY_MINOR);
        PENALTY_SEVERITIES.put("Illegal Formation", SEVERITY_MINOR);
        //Equipment warning
        PENALTY_SEVERITIES.put("Mouth Guard Warning", SEVERITY_MOUTH_GUARD_WARNING);
        //Moderate infractions
        PENALTY_SEVERITIES.put("Holding", SEVERITY_MODERATE);
        PENALTY_SEVERITIES.put("Illegal Use of Hands", SEVERITY_MODERATE);
        PENALTY_SEVERITIES.put("Illegal Block in the Back", SEVERITY_MODERATE);
        PENALTY_SEVERITIES.put("Tandem Buck Block", SEVERITY_MODERATE);
        PENALTY_SEVERITIES.put("Objectionable Conduct", SEVERITY_MODERATE);
        //High impact infraction
        PENALTY_SEVERITIES.put("Pass Interference", SEVERITY_PASS_INTERFERENCE);
        //Safety related infractions
        PENALTY_SEVERITIES.put("Unnecessary Roughness", SEVERITY_SAFETY_RELATED);
        PENALTY_SEVERITIES.put("Personal Foul", SEVERITY_SAFETY_RELATED);
    }

    /**
     * Calculate the impact score based on the severity of the penalty
     * and the quarter in which it occurred.
     * impactScore = severity(penalty) x quarterMultiplier(quarter)
     *
     * @return the impact score
     * @author Huang Shihheng
     * @since 20260925
     */
    public static double calculateImpactScore(TacklePenalty tacklePenalty) {
        double severity = getPenaltySeverity(tacklePenalty.getPenalty());
        double multiplier = getQuarterMultiplier(tacklePenalty.getQuarter());

        double score = severity * multiplier;

        tacklePenalty.setImpactScore(score);
        return score;
    }

    /**
     * Look up the severity weight of the infraction type.
     * Any penalty not in the list gets a default weight.
     *
     * @param penalty The infraction type
     * @return the severity weight
     * @author Huang Shihheng
     * @since 20260925
     */
    public static double getPenaltySeverity(String penalty) {
        if (penalty == null) {
            return 0.0;
        }
        return PENALTY_SEVERITIES.getOrDefault(penalty, SEVERITY_DEFAULT);
    }

    /**
     * Look up the multiplier for the quarter. Only quarters 1-4
     * are valid, anything else produces no impact.
     *
     * @param quarter The quarter the infraction occurred
     * @return the quarter multiplier
     * @author Huang Shihheng
     * @since 20260925
     */
    public static double getQuarterMultiplier(Integer quarter) {
        if (quarter == null || quarter < 1 || quarter > 4) {
            return 0.0;
        }
        return QUARTER_MULTIPLIERS[quarter];
    }

    /**
     * Get all penalties matching the referee or team name using jdbc.
     *
     * @param name the referee or team name to search for
     * @return list of matching penalties
     * @author Huang Shihheng
     * @since 20260925
     */
    public ArrayList<TacklePenalty> processSelectAllByRefereeTeamName(String name) {

        //**********************************************************************
        // This could be done using the repository but there will be times when
        // jdbc will be useful.  For the reports, the requirements state that you
        // are to use jdbc to obtain the data for the report.
        //**********************************************************************
        TacklePenaltyDAO tacklePenaltyDAO = new TacklePenaltyDAO();
        ArrayList<TacklePenalty> penalties = tacklePenaltyDAO.selectAllByRefereeTeamName(name);

        //Also write the report to a file
        CisUtilityFile.writeReportToFile("refereeTeamNameReport", penalties);

        return penalties;
    }

}
