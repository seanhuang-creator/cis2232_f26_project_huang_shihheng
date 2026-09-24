package ca.hccis.penaltytracker.bo;

import ca.hccis.penaltytracker.entity.PenaltyTracker;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Test Suite for PenaltyTrackerBO class.
 * All tests in this suite were designed and executed following a Test Driven Development (TDD) approach.
 * Includes data integration tests powered by real game records from penaltiesHistory.json.
 *
 * @author Sean
 * @since 2026-09
 */
public class PenaltyTrackerBOTest {

    private PenaltyTrackerBO bo;

    @BeforeEach
    public void setUp() {
        bo = new PenaltyTrackerBO();
    }

    /**
     * TDD Test 1: Verify calculation for a minor penalty (Offside) in Quarter 1.
     * Expected: Base 3.0 * Quarter Multiplier 1.0 = 3.0
     * Built following the TDD approach (Red-Green-Refactor).
     */
    @Test
    @DisplayName("TDD Test 1: Minor Penalty in Q1")
    public void testCalculateMinorPenaltyInQ1() {
        PenaltyTracker entity = new PenaltyTracker();
        entity.setPenalty("Offside");
        entity.setQuarter(1);

        double actualScore = bo.calculate(entity);

        // Assert 1: assertEquals
        assertEquals(3.0, actualScore, 0.001, "Offside in Q1 should calculate an impact score of 3.0");
    }

    /**
     * TDD Test 2: Verify high severity penalty (Personal Foul) in Quarter 4.
     * Expected: Base 15.0 * Quarter Multiplier 2.0 = 30.0
     * Built following the TDD approach (Red-Green-Refactor).
     */
    @Test
    @DisplayName("TDD Test 2: Severe Penalty in Q4")
    public void testCalculateSeverePenaltyInQ4() {
        PenaltyTracker entity = new PenaltyTracker();
        entity.setPenalty("Personal Foul");
        entity.setQuarter(4);

        double actualScore = bo.calculate(entity);

        // Assert 2: assertTrue
        assertTrue(actualScore > 20.0, "Personal Foul in Q4 should yield a high impact score exceeding 20.0");
        assertEquals(30.0, actualScore, 0.001, "Personal Foul in Q4 should exactly equal 30.0");
    }

    /**
     * TDD Test 3: Verify entity impactScore field is correctly updated upon calculation.
     * Built following the TDD approach (Red-Green-Refactor).
     */
    @Test
    @DisplayName("TDD Test 3: Verify Entity Field Update")
    public void testCalculateUpdatesEntityImpactScore() {
        PenaltyTracker entity = new PenaltyTracker();
        entity.setPenalty("Holding");
        entity.setQuarter(2);

        // Holding (Base 8.0) * Q2 (1.25) = 10.0
        bo.calculate(entity);

        // Assert 3: assertNotNull & assertEquals
        assertNotNull(entity.getImpactScore(), "Impact score in entity should not be null after calculation");
        assertEquals(10.0, entity.getImpactScore(), 0.001, "Entity impact score should be updated to 10.0");
    }

    // --- AI Generated Test Suite (Requirement 2) ---

    @Test
    @DisplayName("AI Test Suite: Null Entity Handling")
    public void testCalculateNullEntity() {
        double score = bo.calculate(null);
        assertEquals(0.0, score, 0.001, "Null entity should return 0.0");
    }

    @Test
    @DisplayName("AI Test Suite: Unrecognized Penalty Default Score")
    public void testCalculateUnknownPenalty() {
        PenaltyTracker entity = new PenaltyTracker();
        entity.setPenalty("Unsportsmanlike Conduct");
        entity.setQuarter(3);

        // Default Base 5.0 * Q3 (1.5) = 7.5
        double actual = bo.calculate(entity);
        assertEquals(7.5, actual, 0.001, "Default penalty in Q3 should calculate to 7.5");
    }

    @Test
    @DisplayName("AI Test Suite: Invalid Quarter Fallback")
    public void testCalculateInvalidQuarter() {
        PenaltyTracker entity = new PenaltyTracker();
        entity.setPenalty("Offside");
        entity.setQuarter(99); // Invalid quarter

        // Offside Base 3.0 * Default Multiplier 1.0 = 3.0
        double actual = bo.calculate(entity);
        assertEquals(3.0, actual, 0.001, "Invalid quarter should default to 1.0x multiplier");
    }

    // --- Data-Driven Integration Test using Real History Dataset ---

    @Test
    @DisplayName("Data Integration Test: Load & Validate All Records from penaltiesHistory.json")
    public void testCalculateAllHistoryRecordsFromJson() {
        // 1. Read penaltiesHistory.json from classpath resources
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("penaltiesHistory.json");
        assertNotNull(inputStream, "penaltiesHistory.json should exist in src/main/resources");

        // 2. Parse JSON using Gson
        Gson gson = new Gson();
        InputStreamReader reader = new InputStreamReader(inputStream);
        Type listType = new TypeToken<ArrayList<PenaltyTracker>>() {}.getType();
        List<PenaltyTracker> historyList = gson.fromJson(reader, listType);

        // Assert that the dataset was loaded successfully
        assertNotNull(historyList, "Parsed penalty history list should not be null");
        assertTrue(historyList.size() > 0, "Penalty history list should contain test records");

        // 3. Iterate through all real records and run calculate() on each
        for (PenaltyTracker entity : historyList) {
            double score = bo.calculate(entity);

            // Assertions on real game data calculations
            assertTrue(score >= 0, "Calculated impact score should be non-negative for: " + entity.getPenalty());
            assertNotNull(entity.getImpactScore(), "Impact score field should be set on entity for: " + entity.getPenalty());
        }

        System.out.println("Data Integration Test Passed: Successfully calculated impact scores for "
                + historyList.size() + " real PEIFOA penalty records.");
    }
}