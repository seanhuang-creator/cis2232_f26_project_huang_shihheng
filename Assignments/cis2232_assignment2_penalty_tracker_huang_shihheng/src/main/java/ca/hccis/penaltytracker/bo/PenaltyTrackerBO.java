package ca.hccis.penaltytracker.bo;

import ca.hccis.penaltytracker.entity.PenaltyTracker;

/**
 * Business Object for processing calculations related to PenaltyTracker.
 * Calculates game impact scores based on real PEIFOA penalty data.
 * Uses the standard Project Severity system: 0.5/1.0/2.0/3.0/5.0.
 *
 * @author Sean Huang
 * @since 2026-09
 */
public class PenaltyTrackerBO {

    // Standard Project Severity system (based on README: Minor=3.0, Technical/Major=8.0, Severe/Safety=15.0)
    public static final double SEVERITY_MOUTH_GUARD_WARNING = 3.0;
    public static final double SEVERITY_MINOR = 3.0;
    public static final double SEVERITY_TECHNICAL_MAJOR = 8.0;
    public static final double SEVERITY_PASS_INTERFERENCE = 8.0;
    public static final double SEVERITY_SAFETY_RELATED = 15.0;
    public static final double SEVERITY_DEFAULT = 5.0;

    /**
     * Calculates the Impact Score of a penalty based on infraction type and quarter.
     * Evaluates severity using the standard Project Severity system.
     *
     * @param entity The PenaltyTracker entity containing penalty data
     * @return Calculated impact score as a double
     * @author Sean Huang
     * @since 2026-09
     */
    public double calculate(PenaltyTracker entity) {
        if (entity == null) {
            return 0.0;
        }

        double baseScore = SEVERITY_DEFAULT; // Default base score for unspecified penalties
        String penaltyType = entity.getPenalty() != null ? entity.getPenalty().toLowerCase() : "";

        // 1. Minor Procedural Infractions (Severity: 3.0 per README)
        // Matches Project classification: Offside, Procedure, No Yards, Time Count Violation, Illegal Formation
        if (penaltyType.contains("offside")
                || penaltyType.contains("procedure")
                || penaltyType.contains("no yards")
                || penaltyType.contains("time count")
                || penaltyType.contains("formation")) {
            baseScore = SEVERITY_MINOR;
        }
        // Equipment warning (Severity: 3.0 per README - Minor category)
        else if (penaltyType.contains("mouth guard") || penaltyType.contains("equipment")) {
            baseScore = SEVERITY_MOUTH_GUARD_WARNING;
        }
        // 2. Technical / Major Infractions (Severity: 8.0 per README)
        // Matches Project classification: Holding, Illegal Block in the Back, Kick Out of Bounds,
        // Intentional Grounding, Illegal Use of Hands, Tandem Buck Block, Objectionable Conduct
        else if (penaltyType.contains("holding")
                || penaltyType.contains("illegal block")
                || penaltyType.contains("kick out of bounds")
                || penaltyType.contains("grounding")
                || penaltyType.contains("use of hands")
                || penaltyType.contains("buck block")
                || penaltyType.contains("objectionable")) {
            baseScore = SEVERITY_TECHNICAL_MAJOR;
        }
        // 3. Pass Interference (Severity: 8.0 per README - Technical/Major category)
        else if (penaltyType.contains("pass interference")) {
            baseScore = SEVERITY_PASS_INTERFERENCE;
        }
        // 4. Safety Related Infractions (Severity: 15.0 per README)
        // Matches Project classification: Unnecessary Roughness, Personal Foul
        else if (penaltyType.contains("roughness")
                || penaltyType.contains("personal foul")) {
            baseScore = SEVERITY_SAFETY_RELATED;
        }

        // Apply quarter multiplier (Later quarters significantly increase impact on game outcome)
        // Q1 = 1.0x, Q2 = 1.25x, Q3 = 1.5x, Q4 = 2.0x
        double quarterMultiplier;
        switch (entity.getQuarter()) {
            case 1:
                quarterMultiplier = 1.0;
                break;
            case 2:
                quarterMultiplier = 1.25;
                break;
            case 3:
                quarterMultiplier = 1.5;
                break;
            case 4:
                quarterMultiplier = 2.0;
                break;
            default:
                quarterMultiplier = 1.0;
                break;
        }

        double finalScore = baseScore * quarterMultiplier;
        entity.setImpactScore(finalScore);
        return finalScore;
    }
}