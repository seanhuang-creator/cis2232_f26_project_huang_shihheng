package ca.hccis.penaltytracker.bo;

import ca.hccis.penaltytracker.entity.PenaltyTracker;

/**
 * Business object class to handle calculations and business logic for PenaltyTracker.
 *
 * @author Sean
 * @since 2026-09
 */
public class PenaltyTrackerBO {

    /**
     * Calculates the impact score of a penalty based on the penalty type and quarter.
     * Formula: Base Penalty Severity * Quarter Multiplier (1.0 to 1.75)
     * - Severe penalties (e.g., Personal Foul, Unsportsmanlike): Base 20.0
     * - Moderate penalties (e.g., Holding, Pass Interference): Base 10.0
     * - Minor penalties (e.g., Offside, False Start): Base 5.0
     * - Default: Base 5.0
     *
     * @param record The PenaltyTracker entity object
     * @return Calculated impact score, rounded to two decimal places
     * @author Sean Huang
     * @since 2026-09
     */
    public double calculate(PenaltyTracker record) {
        // TDD Step 1: Minimum skeleton to compile.
        return 0.0;
    }
}