package ca.hccis.penaltytracker.bo;

import ca.hccis.penaltytracker.entity.PenaltyTracker;

/**
 * Business Object for processing calculations related to PenaltyTracker.
 * Calculates game impact scores based on real PEIFOA penalty data.
 *
 * @author Sean Huang
 * @since 2026-09
 */
public class PenaltyTrackerBO {

    /**
     * Calculates the Impact Score of a penalty based on infraction type and quarter.
     * Evaluates severity using real PEIFOA infraction categories.
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

        double baseScore = 5.0; // Default base score for unspecified penalties
        String penaltyType = entity.getPenalty() != null ? entity.getPenalty().toLowerCase() : "";

        // 1. Minor Infractions (Base Score: 3.0)
        // Matches real data: Offside, Procedure, Mouth Guard Warning, No Yards, Time Count Violation, Illegal Formation, Illegal Equipment
        if (penaltyType.contains("offside")
                || penaltyType.contains("procedure")
                || penaltyType.contains("mouth guard")
                || penaltyType.contains("no yards")
                || penaltyType.contains("time count")
                || penaltyType.contains("formation")
                || penaltyType.contains("equipment")) {
            baseScore = 3.0;
        }
        // 2. Technical / Major Infractions (Base Score: 8.0)
        // Matches real data: Holding, Pass Interference, Illegal Block in the Back, Kick Out of Bounds, Intentional Grounding, Illegal Use of Hands, Tandem Buck Block
        else if (penaltyType.contains("holding")
                || penaltyType.contains("pass interference")
                || penaltyType.contains("illegal block")
                || penaltyType.contains("kick out of bounds")
                || penaltyType.contains("grounding")
                || penaltyType.contains("use of hands")
                || penaltyType.contains("buck block")) {
            baseScore = 8.0;
        }
        // 3. Severe Safety & Conduct Infractions (Base Score: 15.0)
        // Matches real data: Unnecessary Roughness, Objectionable Conduct, Personal Foul
        else if (penaltyType.contains("roughness")
                || penaltyType.contains("objectionable")
                || penaltyType.contains("personal foul")) {
            baseScore = 15.0;
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