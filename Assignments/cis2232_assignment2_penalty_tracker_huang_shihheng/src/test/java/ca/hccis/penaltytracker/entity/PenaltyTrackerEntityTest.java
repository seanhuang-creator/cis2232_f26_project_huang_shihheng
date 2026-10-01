package ca.hccis.penaltytracker.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Test Suite for PenaltyTracker Entity class.
 * Tests getter/setter methods, toString(), and toJson() functionality.
 *
 * @author seanhuang
 * @since 2026-09
 */
public class PenaltyTrackerEntityTest {

    /**
     * Test all getter and setter methods for proper field assignment.
     */
    @Test
    @DisplayName("Entity Test: Getters and Setters")
    public void testGettersAndSetters() {
        PenaltyTracker entity = new PenaltyTracker();

        // Test all setters
        entity.setId(1);
        entity.setHomeTeam("Home Team");
        entity.setAwayTeam("Away Team");
        entity.setDate("2026-09-15");
        entity.setPenalty("Holding");
        entity.setQuarter(2);
        entity.setPenalizedTeam("Home Team");
        entity.setOffendingPlayer(25);
        entity.setAgeDivision("AFL");
        entity.setReferee("John");
        entity.setImpactScore(10.0);

        // Test all getters
        assertEquals(1, entity.getId(), "ID should be 1");
        assertEquals("Home Team", entity.getHomeTeam(), "Home team should match");
        assertEquals("Away Team", entity.getAwayTeam(), "Away team should match");
        assertEquals("2026-09-15", entity.getDate(), "Date should match");
        assertEquals("Holding", entity.getPenalty(), "Penalty should match");
        assertEquals(2, entity.getQuarter(), "Quarter should be 2");
        assertEquals("Home Team", entity.getPenalizedTeam(), "Penalized team should match");
        assertEquals(25, entity.getOffendingPlayer(), "Offending player should be 25");
        assertEquals("AFL", entity.getAgeDivision(), "Age division should match");
        assertEquals("John", entity.getReferee(), "Referee should match");
        assertEquals(10.0, entity.getImpactScore(), 0.001, "Impact score should be 10.0");
    }

    /**
     * Test toJson() method for proper JSON serialization.
     */
    @Test
    @DisplayName("Entity Test: JSON Serialization")
    public void testToJson() {
        PenaltyTracker entity = new PenaltyTracker();
        entity.setId(1);
        entity.setHomeTeam("Home Team");
        entity.setAwayTeam("Away Team");
        entity.setDate("2026-09-15");
        entity.setPenalty("Holding");
        entity.setQuarter(2);
        entity.setPenalizedTeam("Home Team");
        entity.setOffendingPlayer(25);
        entity.setAgeDivision("AFL");
        entity.setReferee("John");
        entity.setImpactScore(10.0);

        String json = entity.toJson();

        assertNotNull(json, "JSON output should not be null");
        assertTrue(json.contains("Home Team"), "JSON should contain home team");
        assertTrue(json.contains("Holding"), "JSON should contain penalty type");
        assertTrue(json.contains("10.0"), "JSON should contain impact score");
    }

    /**
     * Test toString() method for proper data formatting.
     */
    @Test
    @DisplayName("Entity Test: String Representation")
    public void testToString() {
        PenaltyTracker entity = new PenaltyTracker();
        entity.setId(1);
        entity.setHomeTeam("Home Team");
        entity.setAwayTeam("Away Team");
        entity.setDate("2026-09-15");
        entity.setPenalty("Holding");
        entity.setQuarter(2);
        entity.setPenalizedTeam("Home Team");
        entity.setOffendingPlayer(25);
        entity.setAgeDivision("AFL");
        entity.setReferee("John");
        entity.setImpactScore(10.0);

        String result = entity.toString();

        assertNotNull(result, "ToString output should not be null");
        assertTrue(result.contains("ID: 1"), "ToString should contain ID");
        assertTrue(result.contains("Home Team"), "ToString should contain home team");
        assertTrue(result.contains("Offending Player: #25"), "ToString should contain offending player");
        assertTrue(result.contains("Impact Score: 10.0"), "ToString should contain impact score");
    }

    /**
     * Test default constructor initializes fields properly.
     */
    @Test
    @DisplayName("Entity Test: Default Constructor")
    public void testDefaultConstructor() {
        PenaltyTracker entity = new PenaltyTracker();

        assertEquals(0, entity.getId(), "Default ID should be 0");
        assertEquals(0.0, entity.getImpactScore(), 0.001, "Default impact score should be 0.0");
        // Integer fields default to 0, String fields default to null
    }
}
