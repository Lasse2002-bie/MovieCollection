package domainmodel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest {

    private Controller controller;

    // Kører før hver test, så hver test starter med de samme 5 film i samme rækkefølge
    @BeforeEach
    void setUp() {
        controller = new Controller();
        controller.addMovie("Psycho", "Alfred Hitchcock", 1960, false, 109, "Horror");
        controller.addMovie("The Godfather", "Francis Ford Coppola", 1972, true, 175, "Drama");
        controller.addMovie("Casablanca", "Michael Curtiz", 1942, false, 102, "Drama");
        controller.addMovie("The Godfather Part II", "Francis Ford Coppola", 1974, true, 202, "Drama");
        controller.addMovie("Vertigo", "Alfred Hitchcock", 1958, true, 128, "Thriller");
    }

    // US16: "Vis alle film" skal være i alfabetisk orden
    @Test
    void moviesSortedByTitle() {
        ArrayList<Movie> sorted = controller.getMoviesSortedByTitle();

        assertEquals("Casablanca", sorted.get(0).getTitle());
        assertEquals("Psycho", sorted.get(1).getTitle());
        assertEquals("The Godfather", sorted.get(2).getTitle());
        assertEquals("The Godfather Part II", sorted.get(3).getTitle());
        assertEquals("Vertigo", sorted.get(4).getTitle());
    }

    // US17: sortering efter årstal, ældste først
    @Test
    void moviesSortedByYear() {
        ArrayList<Movie> sorted = controller.getMoviesSortedBy(SortField.YEAR);

        assertEquals(1942, sorted.get(0).getYearCreated());
        assertEquals(1974, sorted.get(4).getYearCreated());
    }

    // US17: sortering efter længde, korteste først
    @Test
    void moviesSortedByLength() {
        ArrayList<Movie> sorted = controller.getMoviesSortedBy(SortField.LENGTH);

        assertEquals(102, sorted.get(0).getLengthInMinutes());
        assertEquals(202, sorted.get(4).getLengthInMinutes());
    }

    // Sortering må ikke ændre selve samlingen, den laver en kopi
    @Test
    void sortingDoesNotChangeCollection() {
        controller.getMoviesSortedBy(SortField.YEAR);
        controller.getMoviesSortedByTitle();

        ArrayList<Movie> movies = controller.getMovies();
        assertEquals("Psycho", movies.get(0).getTitle());
        assertEquals("Vertigo", movies.get(4).getTitle());
    }
}