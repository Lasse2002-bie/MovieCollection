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
        @Test
        void lengthComparatorSortsShortestFirst() {
            LengthComparator comparator = new LengthComparator();

            assertTrue(comparator.compare(casablanca, jaws) < 0);     // 102 før 124
            assertTrue(comparator.compare(jaws, psycho) > 0);         // 124 efter 109
        }

        @Test
        void titleComparatorSortsAlphabetically() {
            TitleComparator comparator = new TitleComparator();

            assertTrue(comparator.compare(casablanca, psycho) < 0);   // C før P
            assertTrue(comparator.compare(psycho, jaws) > 0);         // P efter J
        }

        @Test
        void titleComparatorIgnoresCase() {
            TitleComparator comparator = new TitleComparator();
            Movie lowercase = new Movie("casablanca", "Michael Curtiz", 1942, false, 102, "Drama");

            assertEquals(0, comparator.compare(casablanca, lowercase));   // "Casablanca" = "casablanca"
            assertTrue(comparator.compare(lowercase, psycho) < 0);        // lille c kommer stadig før P
        }

        @Test
        void directorComparatorSortsAlphabetically() {
            DirectorComparator comparator = new DirectorComparator();

            assertTrue(comparator.compare(psycho, casablanca) < 0);   // Alfred før Michael
            assertTrue(comparator.compare(jaws, casablanca) > 0);     // Steven efter Michael
        }

        @Test
        void genreComparatorSortsAlphabetically() {
            GenreComparator comparator = new GenreComparator();

            assertTrue(comparator.compare(casablanca, psycho) < 0);   // Drama før Horror
            assertTrue(comparator.compare(jaws, psycho) > 0);         // Thriller efter Horror
        }
    }

