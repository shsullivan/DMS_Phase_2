/*
 * Shawn Sullivan
 * CEN 3024 - Software Development 1
 * June 25, 2025
 * DiscValidatorTest.java
 * This file test all input validation for each attribute of the disc object. The test file checks for valid
 * inputs as well as invalid inputs by checking for expected thrown exceptions.
 */

import jdk.jfr.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class DiscValidatorTest {

    static Stream<Arguments> invalidTextFieldInputs() {
        return Stream.of(
                // Test for Manufacturer
                Arguments.of(null, "validateManufacturer", "Manufacturer cannot be null"),
                Arguments.of("", "validateManufacturer", "Manufacturer cannot be empty"),
                Arguments.of("A".repeat(21), "validateManufacturer", "Manufacturer cannot be longer " +
                        "than 20 characters"),
                // Tests for Mold
                Arguments.of(null, "validateMold", "Mold cannot be null"),
                Arguments.of("", "validateMold", "Mold cannot be empty"),
                Arguments.of("B".repeat(21), "validateMold", "Mold cannot be longer than 20 " +
                        "characters"),
                // Tests for Plastic
                Arguments.of(null, "validatePlastic", "Plastic cannot be null"),
                Arguments.of("", "validatePlastic", "Plastic cannot be empty"),
                Arguments.of("C".repeat(21), "validatePlastic", "Plastic cannot be longer than 20" +
                        " characters"),
                // Tests for Color
                Arguments.of(null, "validateColor", "Color cannot be null"),
                Arguments.of("", "validateColor", "Color cannot be empty"),
                Arguments.of("RedRedRedRedRedRedRed", "validateColor", "Color cannot be longer than 20" +
                        " characters"),
                // Tests for contactName
                Arguments.of(null, "validateContactName", "Contact Name cannot be null"),
                Arguments.of("", "validateContactName", "Contact Name cannot be empty"),
                Arguments.of("1".repeat(41), "validateContactName", "Contact Name cannot be longer " +
                        "than 40 characters"),
                // Tests for foundAt
                Arguments.of(null, "validateFoundAt", "Found at location cannot be null"),
                Arguments.of("", "validateFoundAt", "Found at location cannot be empty"),
                Arguments.of("1".repeat(51), "validateFoundAt", "Found at location cannot be " +
                        "longer than 50 characters")
        );
    }

    @ParameterizedTest(name = "{index} | {1} | \"{0}\" | {2}")
    @MethodSource("invalidTextFieldInputs")
    @DisplayName("Test invalid inputs for manufacturer, mold, plastic, color, contactName, and foundAt (like requirements)")
    void testInvalidTextFieldInputs(String input, String methodName, String expectedMessage) throws Exception {
        Method method = DiscValidator.class.getMethod(methodName, String.class);

        InvocationTargetException exception = assertThrows(InvocationTargetException.class, () ->
            method.invoke(null, input)
        );

        Throwable cause = exception.getCause();
        assertInstanceOf(IllegalArgumentException.class, cause);
        assertEquals(expectedMessage, cause.getMessage());
    }

    private static Stream<Arguments> invalidConditionInputs() {
        return Stream.of(
                // Invalid numeric values
                Arguments.of("0", "Condition must be greater than 0"),
                Arguments.of("-1", "Condition must be greater than 0"),
                Arguments.of("11", "Condition must be between 1 and 10"),
                // NaN values
                Arguments.of("abc", "Condition must be a valid number"),
                Arguments.of("", "Condition must be a valid number"),
                Arguments.of(" ", "Condition must be a valid number")
        );
    }

    @ParameterizedTest(name = "[{index}] Input: \"{0}\" | Expect: {1}")
    @MethodSource("invalidConditionInputs")
    @DisplayName("Test Invalid Condition Inputs")
    void testInvalidConditionInputs(String input, String expectedMessage) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                DiscValidator.validateCondition(input)
        );
        assertEquals(expectedMessage, exception.getMessage());
    }

    private static Stream<Arguments> invalidDescriptionInputs() {
        return Stream.of(
                Arguments.of("", "Description cannot be empty"),
                Arguments.arguments(null, "Description cannot be empty")
        );
    }

    @ParameterizedTest(name = "[{index}] Input: \"{0}\" | Expect: {1}")
    @MethodSource("invalidDescriptionInputs")
    @DisplayName("Test Invalid Description Inputs")
    void testInvalidDescriptionInputs(String input, String expectedMessage) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                DiscValidator.validateDescription(input)
        );
        assertEquals(expectedMessage, exception.getMessage());
    }

    private static Stream<Arguments> invalidPositiveIntegerInputs() {
        return Stream.of(
                // Invalid numeric values (greater than 0)
                Arguments.of("-1", "Disc ID", "Disc ID must be greater than 0"),
                Arguments.of("0", "Disc ID", "Disc ID must be greater than 0"),
                // NaN values
                Arguments.of("asd", "Disc ID", "Disc ID must be a valid number"),
                Arguments.of("", "Disc ID", "Disc ID must be a valid number"),
                Arguments.of(" ", "Disc ID", "Disc ID must be a valid number")
        );
    }

    @ParameterizedTest(name = "[{index}] Input: \"{0}\" | Expect: {2}")
    @MethodSource("invalidPositiveIntegerInputs")
    @DisplayName("Test Invalid Positive Integer Inputs (DiscID, Condition)")
    void testInvalidPositiveIntegerInputs(String input, String fieldName, String expectedMessage) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                DiscValidator.validatePositiveInt(input, fieldName)
        );
        assertEquals(expectedMessage, exception.getMessage());
    }

    private static Stream<Arguments> invalidPositiveDoubleInputs() {
        return Stream.of(
                // Invalid numeric values
                Arguments.of("-1.0", "MSRP", "MSRP must be greater than 0"),
                Arguments.of("-0.01", "MSRP", "MSRP must be greater than 0"),

                // NaN Values
                Arguments.of("abc", "MSRP", "MSRP must be a valid value"),
                Arguments.of("", "MSRP", "MSRP must be a valid value"),
                Arguments.of(" ", "MSRP", "MSRP must be a valid value"),
                Arguments.of("12.34.56", "MSRP", "MSRP must be a valid value"),
                Arguments.of("12a", "MSRP", "MSRP must be a valid value")
        );
    }

    @ParameterizedTest(name = "[{index}] Input: \"{0}\" | Expect: {2}")
    @MethodSource("invalidPositiveDoubleInputs")
    @DisplayName("Test Invalid Positive Double Inputs (MSRP, etc.)")
    void testInvalidPositiveDoubleInputs(String input, String fieldName, String expectedMessage) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                DiscValidator.validatePositiveDouble(input, fieldName)
        );
        assertEquals(expectedMessage, exception.getMessage());
    }

    private static Stream<Arguments> invalidBooleanInputs() {
        return Stream.of(
                Arguments.of("yes", "Returned", "Returned must be true or false"),
                Arguments.of("no", "Returned", "Returned must be true or false"),
                Arguments.of("1", "Returned", "Returned must be true or false"),
                Arguments.of("0", "Returned", "Returned must be true or false"),
                Arguments.of("", "Returned", "Returned must be true or false"),
                Arguments.of(" ", "Returned", "Returned must be true or false")
        );
    }

    @ParameterizedTest(name = "[{index}] Input: \"{0}\" | Expect: {2}")
    @MethodSource("invalidBooleanInputs")
    @DisplayName("Test Invalid Boolean Inputs (Returned, Sold)")
    void testInvalidBooleanInputs(String input, String fieldName, String expectedMessage) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                DiscValidator.validateBooleanInput(input, fieldName)
        );
        assertEquals(expectedMessage, exception.getMessage());
    }

    private static Stream<Arguments> invalidContactPhoneInputs() {
        return Stream.of(
                Arguments.of(null, "Contact Phone cannot be empty"),
                Arguments.of("", "Contact Phone cannot be empty"),
                Arguments.of("123456789", "Contact Phone must be 10 digits"),
                Arguments.of("12345678901", "Contact Phone must be 10 digits"),
                Arguments.of("abcdefghij", "Contact Phone must contain only digits"),
                Arguments.of("123 456789", "Contact Phone must contain only digits"),
                Arguments.of("123-456789", "Contact Phone must contain only digits")
        );
    }

    @ParameterizedTest(name = "[{index}] Input: \"{0}\" | Expect: {1}")
    @MethodSource("invalidContactPhoneInputs")
    @DisplayName("Test Invalid contactPhone inputs")
    void testInvalidContactPhoneInputs(String input, String expectedMessage) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                DiscValidator.validateContactPhone(input)
        );
        assertEquals(expectedMessage, exception.getMessage());
    }

    private static Stream<Arguments> validTextFieldInputs() {
        return Stream.of(
                Arguments.of("validateManufacturer", "Innova"),
                Arguments.of("validateMold", "Wraith"),
                Arguments.of("validatePlastic", "Star"),
                Arguments.of("validateColor", "Red"),
                Arguments.of("validateContactName", "John Smith"),
                Arguments.of("validateFoundAt", "Springfield")
        );
    }

    @ParameterizedTest(name = "[{index}] Input: \"{0}\" | Expect: {1}")
    @MethodSource("validTextFieldInputs")
    @DisplayName("Test valid text field inputs")
    void testValidTextFieldInputs(String methodName, String input) throws Exception {
        Method method = DiscValidator.class.getMethod(methodName, String.class);
        String result = (String) method.invoke(null, input);
        assertEquals(input, result);
    }

    @ParameterizedTest(name = "[{index}] Valid input: \"{0}\" should be accepted")
    @ValueSource(strings = {"6", "17", "131"})
    @DisplayName("Test valid positive int inputs (discID, etc.)")
    void testValidPositiveIntInputs(String input) {
        assertDoesNotThrow(() -> {
            int value = Integer.parseInt(input);
            int result = DiscValidator.validatePositiveInt(input, "Disc ID");
            assertEquals(value, result);
        });
    }

    @ParameterizedTest(name = "[{index}] Valid input: \"{0}\" should be accepted")
    @ValueSource(strings = {"24.99", "10.50", "59.99"})
    @DisplayName("Test valid positive double inputs (MSRP)")
    void testValidPositiveDoubleInputs(String input) {
        assertDoesNotThrow(() -> {
            double value = Double.parseDouble(input);
            double result = DiscValidator.validatePositiveDouble(input, "Disc ID");
            assertEquals(value, result, 0.0);
        });
    }

    @ParameterizedTest(name = "[{index}] Valid input: \"{0}\" should be accepted")
    @ValueSource(strings = {"true", "false", "True", "False", "TRUE", "FALSE"})
    @DisplayName("Test valid boolean inputs (returned, sold)")
    void testValidBooleanInputs(String input) {
        assertDoesNotThrow(() -> {
            boolean value = Boolean.parseBoolean(input);
            boolean result = DiscValidator.validateBooleanInput(input, "Returned");
            assertEquals(value, result);
        });
    }

    @ParameterizedTest(name = "[{index}] Valid input: \"{0}\" should be accepted")
    @ValueSource(strings = {"1", "3", "6", "9", "10"})
    @DisplayName("Test valid inputs for condition")
    void testValidInputsForCondition(String input) {
        assertDoesNotThrow(() -> {
            int value = Integer.parseInt(input);
            int result = DiscValidator.validatePositiveInt(input, "Condition");
            assertEquals(value, result);
        });
    }
    @ParameterizedTest(name = "[{index}] Valid input: \"{0}\" should be accepted")
    @ValueSource(strings = {"My favorite disc", "Flippy", "Meat hook"})
    @DisplayName("Test valid inputs for description")
    void testValidInputsForDescription(String input) {
        assertDoesNotThrow(() -> {
            String value = input;
            String result = DiscValidator.validateDescription(input);
            assertEquals(value, result);
        });
    }

    @ParameterizedTest(name = "[{index}] Valid input: \"{0}\" should be accepted")
    @ValueSource(strings = {"3348675309", "5555551234", "1234567890"})
    @DisplayName("Test valid inputs for contactPhone")
    void testValidInputsForContactPhone(String input) {
        assertDoesNotThrow(() -> {
            String value = input;
            String result = DiscValidator.validateContactPhone(value);
            assertEquals(value, result);
        });
    }
}