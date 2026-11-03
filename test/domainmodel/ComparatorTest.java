package domainmodel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

    public class ComparatorTest {

        private Movie casablanca;
        private Movie psycho;
        private Movie jaws;

        @BeforeEach
        void setUp() {
            casablanca = new Movie("Casablanca", "Michael Curtiz", 1942, false, 102, "Drama");
            psycho     = new Movie("Psycho", "Alfred Hitchcock", 1960, false, 109, "Horror");
            jaws       = new Movie("Jaws", "Steven Spielberg", 1975, true, 124, "Thriller");
        }

        @Test
        void yearComparatorSortsOldestFirst() {
            YearComparator comparator = new YearComparator();

            assertTrue(comparator.compare(casablanca, psycho) < 0);  // 1942 før 1960
            assertTrue(comparator.compare(jaws, psycho) > 0);        // 1975 efter 1960
            assertEquals(0, comparator.compare(jaws, jaws));         // ens
        }
    }

