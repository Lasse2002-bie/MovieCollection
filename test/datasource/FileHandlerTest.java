package datasource;

import domainmodel.Movie;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class FileHandlerTest {
    //Gem og indlæs igen
    @Test
    void saveAndLoadMovies() throws FileNotFoundException {
        FileHandler fileHandler = new FileHandler("test-movies.csv");

        Movie movie1 = new Movie("Adams æbler", "Anders Thomas Jensen", 2005, true, 94, "Komedie");
        Movie movie2 = new Movie("Rødgrød med fløde", "Søren Ågård", 2020, true, 120, "Drama");
        movie1.markAsWatched(LocalDate.of(2025, 10, 30));

        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(movie1);
        movies.add(movie2);

        //Gemmer de to testfilm i CSV filen
        fileHandler.saveMovies(movies);

        //Indlæser filmene igen fra CSV filen
        ArrayList<Movie> loadedMovies = fileHandler.loadMovies();

        //Tjekker at begge film blev indlæst igen
        assertEquals(2, loadedMovies.size());
        assertEquals("Adams æbler", loadedMovies.get(0).getTitle());
        assertEquals("Anders Thomas Jensen", loadedMovies.get(0).getDirector());
        assertEquals(2005, loadedMovies.get(0).getYearCreated());
        assertTrue(loadedMovies.get(0).isInColor());
        assertEquals(94, loadedMovies.get(0).getLengthInMinutes());
        assertEquals("Komedie", loadedMovies.get(0).getGenre());
        assertEquals(LocalDate.of(2025, 10, 30), loadedMovies.get(0).getLastWatched());


        assertEquals("Rødgrød med fløde", loadedMovies.get(1).getTitle());
        assertEquals("Søren Ågård", loadedMovies.get(1).getDirector());
        assertEquals(2020, loadedMovies.get(1).getYearCreated());
        assertTrue(loadedMovies.get(1).isInColor());
        assertEquals(120, loadedMovies.get(1).getLengthInMinutes());
        assertEquals("Drama", loadedMovies.get(1).getGenre());
        assertFalse(loadedMovies.get(1).hasBeenWatched());
    }

    //Gem en tom liste
    @Test
    void saveAndLoadEmptyList() throws FileNotFoundException {
        FileHandler fileHandler = new FileHandler("test-movies.csv");

        //Tom liste
        ArrayList<Movie> movies = new ArrayList<>();

        fileHandler.saveMovies(movies);

        ArrayList<Movie> loadedMovies = fileHandler.loadMovies();
        assertTrue (loadedMovies.isEmpty());
    }
//Indlæs en fil, der ikke findes
    @Test
    void loadNonExistingFile() {

        // Bruger et filnavn som ikke findes
        FileHandler fileHandler = new FileHandler("file-does-not-exist.csv");

        // Tjekker at FileNotFoundException bliver kastet
        assertThrows(FileNotFoundException.class, () -> {
            fileHandler.loadMovies();
        });
    }

    //Test - ugyldige linjer i CSV filen springes over
    @Test
    void loadFileWithCorruptedLines() throws FileNotFoundException {
        PrintStream output = new PrintStream(new File("test-movies.csv"));


        output.println("title;director;yearCreated;inColor;lengthInMinutes;genre;lastWatched");
        output.println("Jaws;Steven Spielberg;1975;true;124;Thriller;2025-10-30");
        output.println("Batman;Christopher Nolan;forkert;true;152;Action;");
        output.println("Dårlig film;Test");

        output.close();

        FileHandler fileHandler = new FileHandler("test-movies.csv");

        ArrayList<Movie> loadedMovies = fileHandler.loadMovies();

        assertEquals(1, loadedMovies.size());
        assertEquals("Jaws", loadedMovies.get(0).getTitle());
        assertEquals(2, fileHandler.getSkippedLineCount());
    }
}