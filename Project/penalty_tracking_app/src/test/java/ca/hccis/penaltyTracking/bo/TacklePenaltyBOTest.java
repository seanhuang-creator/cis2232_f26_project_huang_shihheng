package ca.hccis.penaltyTracking.bo;

import ca.hccis.penaltyTracking.jpa.entity.TacklePenalty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TacklePenaltyBOTest {

    @Test
    void calculateImpactScore_minorPenaltyFirstQuarter() {
        TacklePenalty penalty = new TacklePenalty();
        penalty.setPenalty("Offside");
        penalty.setQuarter(1);

        double actual = TacklePenaltyBO.calculateImpactScore(penalty);

        Assertions.assertEquals(1.0, actual);
        Assertions.assertEquals(1.0, penalty.getImpactScore());
    }

    @Test
    void calculateImpactScore_personalFoulFourthQuarter() {
        TacklePenalty penalty = new TacklePenalty();
        penalty.setPenalty("Personal Foul");
        penalty.setQuarter(4);

        double actual = TacklePenaltyBO.calculateImpactScore(penalty);

        Assertions.assertEquals(20.0, actual);
        Assertions.assertEquals(20.0, penalty.getImpactScore());
    }

    @Test
    void calculateImpactScore_mouthGuardWarningSecondQuarter() {
        TacklePenalty penalty = new TacklePenalty();
        penalty.setPenalty("Mouth Guard Warning");
        penalty.setQuarter(2);

        double actual = TacklePenaltyBO.calculateImpactScore(penalty);

        Assertions.assertEquals(1.0, actual);
        Assertions.assertEquals(1.0, penalty.getImpactScore());
    }

    @Test
    void calculateImpactScore_unknownPenaltyUsesDefaultSeverity() {
        TacklePenalty penalty = new TacklePenalty();
        penalty.setPenalty("Clipping");
        penalty.setQuarter(2);

        double actual = TacklePenaltyBO.calculateImpactScore(penalty);

        Assertions.assertEquals(4.0, actual);
        Assertions.assertEquals(4.0, penalty.getImpactScore());
    }

    @Test
    void calculateImpactScore_invalidQuarterReturnsZero() {
        TacklePenalty penalty = new TacklePenalty();
        penalty.setPenalty("Offside");
        penalty.setQuarter(0);

        double actual = TacklePenaltyBO.calculateImpactScore(penalty);

        Assertions.assertEquals(0.0, actual);
        Assertions.assertEquals(0.0, penalty.getImpactScore());
    }

    @Test
    void calculateImpactScore_nullQuarterReturnsZero() {
        TacklePenalty penalty = new TacklePenalty();
        penalty.setPenalty("Offside");
        penalty.setQuarter(null);

        double actual = TacklePenaltyBO.calculateImpactScore(penalty);

        Assertions.assertEquals(0.0, actual);
        Assertions.assertEquals(0.0, penalty.getImpactScore());
    }

    @Test
    void calculateImpactScore_nullPenaltyReturnsZero() {
        TacklePenalty penalty = new TacklePenalty();
        penalty.setPenalty(null);
        penalty.setQuarter(4);

        double actual = TacklePenaltyBO.calculateImpactScore(penalty);

        Assertions.assertEquals(0.0, actual);
        Assertions.assertEquals(0.0, penalty.getImpactScore());
    }

}
