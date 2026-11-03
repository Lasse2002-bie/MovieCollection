package domainmodel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

public class MovieCollectionTest {

    //Test us 16 & 17

    //Test2

    @Test
    void getMoviesSortedByTitleSortsAlphabetically() {
        MovieCollection collection = new MovieCollection();

        collection.addMovie("Zorro", "Test", 2000, true, 100, "Action");
        collection.addMovie("Avatar", "Test", 2009, true, 100, "Action");
        collection.addMovie("Batman", "Test", 2008, true, 100, "Action");

        var sorted = collection.getMoviesSortedByTitle();

        assertEquals("Avatar", sorted.get(0).getTitle());
        assertEquals("Batman", sorted.get(1).getTitle());
        assertEquals("Zorro", sorted.get(2).getTitle());
    }

    //Test 3

    @Test
    void sortingDoesNotChangeOriginalOrder() {
        MovieCollection collection = new MovieCollection();
        collection.addMovie("Zorro", "Test", 2000, true, 100, "Action");
        collection.addMovie("Avatar", "Test", 2009, true, 100, "Action");
        collection.addMovie("Batman", "Test", 2008, true, 100, "Action");

        collection.getMoviesSortedByTitle();

        assertEquals("Zorro", collection.getMovies().get(0).getTitle());
        assertEquals("Avatar", collection.getMovies().get(1).getTitle());
        assertEquals("Batman", collection.getMovies().get(2).getTitle());
    }

    //Dato test 4 - en film der aldrig er set skal være med
    @Test
    void neverWatchedMovieIsIncluded() {
        MovieCollection collection = new MovieCollection();

        collection.addMovie("Batman", "Christopher Nolan", 2008, true, 152, "Action");

        var movies = collection.getMoviesNotWatchedSince(LocalDate.of(2025, 10, 1));

        assertEquals(1, movies.size());
        assertEquals("Batman", movies.get(0).getTitle());
    }

    //Dato test 5 - tester før, på og efter grænsedatoen
    @Test
    void moviesWatchedBeforeAreIncluded() {
        MovieCollection collection = new MovieCollection();

        Movie Before = new Movie("Jaws", "Steven Spielberg", 1975, true, 124, "Thriller");
        Movie onDate = new Movie("Batman", "Christopher Nolan", 2008, true, 152, "Action");
        Movie After = new Movie("Avatar", "James Cameron", 2009, true, 162, "Action");

        Before.markAsWatched(LocalDate.of(2025, 9, 30));
        onDate.markAsWatched(LocalDate.of(2025, 10, 1));
        After.markAsWatched(LocalDate.of(2025, 10, 2));

        collection.addMovie(Before);
        collection.addMovie(onDate);
        collection.addMovie(After);

        var movies = collection.getMoviesNotWatchedSince(LocalDate.of(2025, 10, 1));

        assertTrue(movies.contains(Before));
        assertFalse(movies.contains(onDate));
        assertFalse(movies.contains(After));
    }

    //Del 5 tests
    //test 1

    @Test
    void NewCollectionHasZeroMovies() {
        MovieCollection collection = new MovieCollection();
        assertEquals(0, collection.getNumberOfMovies());
    }

    //Test 2
    @Test
    void addOneMovie() {
        MovieCollection collection = new MovieCollection();

        collection.addMovie("Jaws", "Steven Spielberg", 1975, true, 124, "Thriller");

        assertEquals(1, collection.getNumberOfMovies());
        assertEquals("Jaws", collection.getMovies().get(0).getTitle());
    }

    //Test 3
    @Test
    void addMultipleMovies() {
        MovieCollection collection = new MovieCollection();

        collection.addMovie("Jaws", "Steven Spielberg", 1975, true, 124, "Thriller");
        collection.addMovie("Batman", "Christopher Nolan", 2008, true, 152, "Action");
        collection.addMovie("Avatar", "James Cameron", 2009, true, 162, "Action");

        assertEquals(3, collection.getNumberOfMovies());
    }

    // test 4
    @Test
    void searchMoviesWithoutMatchReturnsEmptyList() {
        MovieCollection collection = new MovieCollection();

        collection.addMovie("Jaws", "Steven Spielberg", 1975, true, 124, "Thriller");
        collection.addMovie("Batman", "Christopher Nolan", 2008, true, 152, "Action");

        var result = collection.searchMovies("Avater");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    //Test 5
    @Test
    void searchMoviesWithOneMatch() {
        MovieCollection collection = new MovieCollection();

        collection.addMovie("Jaws", "Steven Spielberg", 1975, true, 124, "Thriller");
        collection.addMovie("Batman", "Christopher Nolan", 2008, true, 152, "Action");
        collection.addMovie("Avatar", "James Cameron", 2009, true, 162, "Action");

        var result = collection.searchMovies("Jaws");

        assertEquals(1, result.size());
        assertEquals("Jaws", result.get(0).getTitle());
    }

    //Test 6
    @Test
    void searchMoviesWithMultipleMatches() {
        MovieCollection collection = new MovieCollection();

        collection.addMovie("The Godfather", "Francis Ford Coppola", 1972, true, 175, "Crime");
        collection.addMovie("Godzilla", "Ishirō Honda", 1954, false, 96, "Sci-fi");
        collection.addMovie("Batman", "Christopher Nolan", 2008, true, 152, "Action");

        var result = collection.searchMovies("God");

        assertEquals(2, result.size());
    }

    //Test 7 søgning ignorerer store og små bogstaver
    @Test
    void SearchMoviesInCaseInsensitive() {
        MovieCollection collection = new MovieCollection();

        collection.addMovie("Batman", "Christopher Nolan", 2008, true, 152, "Action");

        var result = collection.searchMovies("bAtMaN");

        assertEquals(1, result.size());
        assertEquals("Batman", result.get(0).getTitle());
    }

    //Test 8 editMovie ændrer filmen og returnerer true
    @Test
    void editMovieChangesMovie() {
        MovieCollection collection = new MovieCollection();

        collection.addMovie("Batman", "Christopher Nolan", 2008, true, 152, "Action");

        Movie movie = collection.getMovies().get(0);

        boolean result = collection.editMovie(movie,"The Dark Knight", "Christopher Nolan", 2008, true, 152, "Action");

        assertTrue(result);
        assertEquals("The Dark Knight", movie.getTitle());
    }

    //Test 9 editMovie returnerer false hvis filmen ikke findes i samlingen
    @Test
    void editMovieNotInCollectionReturnsFalse() {
        MovieCollection collection = new MovieCollection();

        Movie movie = new Movie("Batman", "Christopher Nolan", 2008, true, 152, "Action");

        boolean result = collection.editMovie(movie, "The Dark Knight", "Christopher Nolan", 2008, true, 152, "Action");

        assertFalse(result);
        assertEquals("Batman", movie.getTitle());
    }

    //Test 10 - deleteMovie fjerner filmen og returnerer true
    @Test
    void deleteMovieRemovesMovie() {
        MovieCollection collection = new MovieCollection();

        collection.addMovie("Batman", "Christopher Nolan", 2008, true, 152, "Action");

        Movie movie = collection.getMovies().get(0);

        boolean result = collection.deleteMovie(movie);

        assertTrue(result);
        assertEquals(0, collection.getNumberOfMovies());
    }

    //Test 11 - deleteMovie Returnerer false hvis filmen ikke findes
    @Test
    void deleteMovieNotInCollectionReturnsFalse() {
        MovieCollection collection = new MovieCollection();

        collection.addMovie("Jaws", "Steven Spielberg", 1975, true, 124, "Thriller");

        Movie movieNotInCollection = new Movie("Batman", "Christopher Nolan", 2008, true, 152, "Action");

        boolean result = collection.deleteMovie(movieNotInCollection);

        assertFalse(result);
        assertEquals(1, collection.getNumberOfMovies());

    }
}


