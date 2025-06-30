import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RakerTest {

    private Raker raker;
    private Disc disc;

    @BeforeEach
    void setUp() {
        raker = new Raker();
        disc = new Disc(
                1, "Innova", "Wraith", "Star", "Red", 9,
                "My favorite disc", "John Doe", "3348675309", "Springfield",
                false, false, 24.99
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


}