package ca.hccis.penaltytracker;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Test Suite for Controller class.
 * Tests menu constants and ID assignment logic.
 *
 * @author seanhuang
 * @since 2026-09
 */
public class ControllerTest {

    /**
     * Test that MENU constant contains required options.
     */
    @Test
    @DisplayName("Controller Test: Menu Options")
    public void testMenuConstant() {
        String menu = Controller.MENU;

        assertNotNull(menu, "MENU constant should not be null");
        assertTrue(menu.contains("A) Add"), "MENU should contain 'Add' option");
        assertTrue(menu.contains("V) View"), "MENU should contain 'View' option");
        assertTrue(menu.contains("X) eXit"), "MENU should contain 'eXit' option");
    }

    /**
     * Test that PATH is set to a valid relative path.
     */
    @Test
    @DisplayName("Controller Test: Data Path Configuration")
    public void testDataPathConfiguration() {
        String path = Controller.PATH;

        assertNotNull(path, "PATH constant should not be null");
        assertFalse(path.isEmpty(), "PATH should not be empty");
        // Verify it's a relative path (doesn't start with drive letter like "D:")
        assertFalse(path.matches("^[A-Za-z]:.*"), "PATH should be a relative path, not absolute");
    }

    /**
     * Test that FILE_NAME follows naming convention.
     */
    @Test
    @DisplayName("Controller Test: File Name Configuration")
    public void testFileNameConfiguration() {
        String fileName = Controller.FILE_NAME;

        assertNotNull(fileName, "FILE_NAME constant should not be null");
        assertTrue(fileName.endsWith(".json"), "FILE_NAME should have .json extension");
        assertTrue(fileName.contains("huang_shihheng"), "FILE_NAME should contain author name");
    }
}
