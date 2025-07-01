import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RakerTest {

    private Raker raker;
    private Disc disc;

    @BeforeEach
    void setUp() {
        raker = new Raker();
        disc = new Disc(
                1, "Innova", "Wraith", "Star", "Red", 9,
                "My favorite disc", "John Doe", "3348675309", "Springfield",
                true, true, 24.99
        );
    }

    @Test
    void testAddDiscReturnsTrueForNewDisc() {
        boolean result = raker.addDisc(disc);
        assertTrue(result, "Expected addDisc to return true");
    }

    @Test
    void testAddDiscReturnsFalseForExistingDisc() {
        raker.addDisc(disc);
        boolean result = raker.addDisc(disc);
        assertFalse(result, "Expected addDisc to return false for existing disc being added");
    }

    @Test
    void testLoadingDiscsFromFile() throws IOException {
        // Create test file with valid and invalid inputs
        File tempFile = File.createTempFile("temp-discs", ".txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {
            bw.write("1-Innova-Wraith-Star-Red-9-Great driver-John Doe-1234567890-Springfield-true-false-19.99\n");
            bw.write("INVALID-LINE-WITH-MISSING-FIELDS\n");
            bw.write("2-Discraft-Buzzz-Z-Blue-7-Control driver-Jane Smith-0987654321" +
                        "-Sabin Woods-false-false-17.99\n");
            bw.flush();
        }
        // Read file using preinitialized Raker
        raker.addFromFile(tempFile.getAbsolutePath());

        // Verify the file contains the expected values
        Map<Integer, Disc> testDiscs = raker.getDiscs();
        assertEquals(2, testDiscs.size(), "Expected 2 valid discs to be loaded");

        assertTrue(testDiscs.containsKey(1));
        assertTrue(testDiscs.containsKey(2));

        Disc testDisc = testDiscs.get(1);
        assertEquals("Wraith", disc.getMold());
        assertEquals("John Doe", disc.getContactName());
    }

    @Test
    void testAddFromFileWithInvalidFileReturnsFalse() {
        String invalidFilePath = "invalid-file-path.txt";
        assertFalse(raker.addFromFile(invalidFilePath), "Expected addFromFile to return false");
    }

    @Test
    void testRemoveDiscWhenDiscExistsInDatabase() {
        raker.addDisc(disc);

        boolean result = raker.removeDisc(1);

        assertTrue(result, "Expected remove disc to return true");
        assertFalse(raker.getDiscs().containsKey(1), "Expected disc to be removed from database");
    }

    @Test
    void testRemoveDiscWHenDiscDoesNotExistInDatabase() {
        boolean result = raker.removeDisc(1);

        assertFalse(result, "Expected remove disc to return false when disc does not exist");
    }

    @Test
    void testThatAllListingMethodsBehaviorWhenDatabaseIsEmpty() {
        boolean listAllDiscsResult = raker.listAllDiscs();
        boolean listReturnDiscsResult = raker.listAllDiscs();
        boolean listSoldDiscsResult = raker.listSoldDiscs();

        assertFalse(listAllDiscsResult, "Expected listAllDiscs to return false because the database " +
                "is empty");
        assertFalse(listReturnDiscsResult, "Expected listReturnDiscs to return false because the database" +
                " is empty");
        assertFalse(listSoldDiscsResult, "Expect listSoldDiscs to return false because the database is empty");
    }

    @Test
    void testListAllDiscsWhenDatabaseIsNotEmpty() {
        raker.addDisc(disc);
        boolean result = raker.listAllDiscs();

        assertTrue(result, "Expected listAllDiscs to return true because the database is not empty");
    }

    @Test
    void testListReturnedDiscsWhenDatabaseIsNotEmpty() {
        raker.addDisc(disc);

        assertTrue(raker.listReturnedDiscs(), "Expected listReturnedDiscs to return true because the database" +
                " is not empty");
    }

    @Test
    void testListSoldDiscsWhenDatabaseIsNotEmpty() {
        raker.addDisc(disc);

        assertTrue(raker.listSoldDiscs(), "Expected listSoldDiscs to return true because the database is " +
                "not empty");
    }

    @Test
    void testFindByPhoneWhenDatabaseIsEmpty() {
        assertFalse(raker.findByPhone("3348675309"), "Expected findByPhone to return false because" +
                " the database is empty");
    }

    @Test
    void testFindByPhoneWhenDatabaseIsNotEmpty() {
        raker.addDisc(disc);

        assertTrue(raker.findByPhone("3348675309"), "Expected findByPhone to return true because" +
                " the phone number exists in the database");
    }

    @Test
    void testCalculateReturnedValueReturnsCorrectCalculatedValue() {
        raker.addDisc(disc);

        assertEquals(24.99, raker.calculateReturnedValue(), 0.001 );
    }

    @Test
    void testCalculateSoldValueReturnsCorrectCalculatedValue() {
        raker.addDisc(disc);

        assertEquals(19.99, raker.calculateSoldValue(), 0.1 );
    }

    @Test
    void testReturnDiscWhenDatabaseIsEmpty() {
        assertFalse(raker.returnDisc(1), "Expected returnDisc to return false because the database is empty");
    }

    @Test
    void testReturnDiscWhenDiscIDIsNotFoundInDatabase() {
        raker.addDisc(disc);
        assertFalse(raker.returnDisc(2), "Expected returnDisc to return false because the disc ID " +
                "provided is not found in the database");
    }

    @Test
    void testReturnDiscWhenDiscIDIsFoundInDatabase() {
        raker.addDisc(disc);
        assertTrue(raker.returnDisc(1), "Expected returnDisc to return true because the disc ID " +
                "provided is found in the database");
    }

    @Test
    void testSellDiscWhenDatabaseIsEmpty() {
        assertFalse(raker.sellDisc(1), "Expected sellDisc to return false because the database is empty");
    }

    @Test
    void testSellDiscWhenDiscIDIsNotFoundInDatabase() {
        raker.addDisc(disc);
        assertFalse(raker.returnDisc(2), "Expected sellDisc to return false because the disc ID " +
                "provided is not found in the database");
    }

    @Test
    void testSellDiscWhenDiscIDIsFoundInDatabase() {
        raker.addDisc(disc);
        assertTrue(raker.sellDisc(1), "Expected sellDisc to return true beacuse Disc ID" +
                " provided is found in the database");
    }

    @Test
    void testUpdateContactInformationWhenDatabaseIsEmpty() {
        assertFalse(raker.updateContactInformation(1, "John Doe", "3348675309"),
                "Expected updateContactInformation to return false because the database is empty");
    }

    @Test
    void testUpdateContactInformationWhenDiscIDIsNotFoundInDatabase() {
        raker.addDisc(disc);
        assertFalse(raker.updateContactInformation(2,"John Doe", "3348675309"),
                "Expected UpdateContactInformation to return false because disc ID provided is not found" +
                        "in the database");
    }

    @Test
    void testUpdateContactInformationWhenDiscIDIsFoundInDatabase() {
        raker.addDisc(disc);
        assertTrue(raker.updateContactInformation(1, "John Smith", "3348675309"),
                "Expected UpdateContactInformation to return true because disc ID provided is found in" +
                        "the database");
    }
}