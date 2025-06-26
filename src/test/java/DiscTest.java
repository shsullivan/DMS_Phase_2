/*
 * Shawn Sullivan
 * CEN 3024 - Software Development 1
 * June 25, 2025
 * DiscTest.java
 * This file test all input validation for each attribute in the Disc class constructor. The test file checks for valid
 * inputs as well as invalid inputs by checking for expected thrown exceptions.
 */

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

class DiscTest {

    // Create a known good value for each Disc attribute
    private final String validManufacturer = "Innova";
    private final String validMold = "Wraith";
    private final String validPlastic = "Star";
    private final String validColor = "Blue";
    private final int validCondition = 5;
    private final String validDescription = "My favorite disc";
    private final String validContactName = "John Smith";
    private final String validContactPhone = "3348675309";
    private final String validFoundAt = "Springfield";
    private final boolean validReturned = true;
    private final boolean validSold = false;
    private final double validMSRP = 24.99;

    // Helper method to streamline testing String fields with equivalent validation tests (manufacturer, mold,
    // plastic, color)
    private Disc createDiscWith (String manufacturer, String mold, String plastic, String color) {
        return new Disc(1, manufacturer, mold, plastic, color, validCondition, validDescription,
                        validContactName, validContactPhone, validFoundAt, validReturned, validSold,
                        validMSRP);
    }

    // Method for supplying invalid text field inputs to the unit test
    private static Stream<Arguments> invalidInputs () {
        return Stream.of(
                Arguments.of(null, "Wraith", "Star", "Red", "Manufacturer cannot be null"),
                Arguments.of("", "Wraith", "Star", "Red", "Manufacturer cannot be empty"),
                Arguments.of("A".repeat(21), "Wraith", "Star", "Red", "Manufacturer cannot be longer " +
                        "than 20 characters"),
                Arguments.of("Innova", null, "Star", "Red", "Mold cannot be null"),
                Arguments.of("Innova", "", "Star", "Red", "Mold cannot be empty"),
                Arguments.of("Innova", "A".repeat(21), "Star", "Red", "Mold cannot be longer " +
                        "than 20 characters"),
                Arguments.of("Innova", "Wraith", null, "Red", "Plastic cannot be null"),
                Arguments.of("Innova", "Wraith", "", "Red", "Plastic cannot be empty"),
                Arguments.of("Innova", "Wraith", "A".repeat(21), "Red", "Plastic cannot be longer " +
                        "than 20 characters"),
                Arguments.of("Innova", "Wraith", "Star", null, "Color cannot be null"),
                Arguments.of("Innova", "Wraith", "Star", "", "Color cannot be empty"),
                Arguments.of("Innova", "Wraith", "Star", "A".repeat(21), "Color cannot be longer " +
                        "than 20 characters")
        );
    }

    // Test Units
    @ParameterizedTest(name = "{index}: {4}")
    @MethodSource("invalidInputs")
    void testInvalidTextInputs(String manufacturer, String mold, String plastic, String color, String expectedMessage) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                createDiscWith(manufacturer, mold, plastic, color)
        );
        assertEquals(expectedMessage, exception.getMessage());
    }

    @ParameterizedTest(name = "Invalid discID: {0}")
    @ValueSource(ints = {0, -1, -100})
    void testInvalidDiscIDs (int invalidID) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            new Disc(invalidID, validManufacturer, validMold, validPlastic, validColor, validCondition,
                    validDescription, validContactName, validContactPhone, validFoundAt, validReturned,
                    validSold, validMSRP)
        );
        assertTrue(exception.getMessage().contains("Disc ID cannot"));
    }

    @ParameterizedTest(name = "Invalid disc condition: {0}")
    @ValueSource(ints = {0, -1, 11, 100})
    void testInvalidDiscConditions (int invalidCondition) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new Disc(1, validManufacturer, validMold, validPlastic, validColor, invalidCondition,
                        validDescription, validContactName, validContactPhone, validFoundAt, validReturned,
                        validSold, validMSRP)
        );
        assertEquals("Condition must be between 1 and 10", exception.getMessage());
    }
}