import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

public class MovieTest {

    //Test 1	tom titel afvises fra movie class
    @Test
    void emptyTitleIsRejected() {
        String title = "";
        String director = "Steven Spielberg";
        int yearCreated = 2002;
        boolean inColor = true;
        int lengthInMinutes = 120;
        String genre = "Action";

        assertThrows(IllegalArgumentException.class,
                () -> new Movie(title, director, yearCreated, inColor, lengthInMinutes, genre));

    }

    //Test 2 del 1 1887afvist
    @Test
    void year1887IsRejected() {
        String title = "Batman";
        String director = "Steven Spielberg";
        int yearCreated = 1887;
        boolean inColor = true;
        int lengthInMinutes = 120;
        String genre = "Action";

        assertThrows(IllegalArgumentException.class,
                () -> new Movie(title, director, yearCreated, inColor, lengthInMinutes, genre));

    }

    //Test 2 del 2 1888Accepteres
    @Test
    void year1888IsAccepted() {
        String title = "Batman";
        String director = "Steven Spielberg";
        int yearCreated = 1888;
        boolean inColor = true;
        int lengthInMinutes = 120;
        String genre = "Action";

        assertDoesNotThrow(
                () -> new Movie(title, director, yearCreated, inColor, lengthInMinutes, genre));

    }

    //Test 3 årstal to pr ude i fremtiden afvises
    @Test
    void yearInTwoYearsIsRejected() {
        String title = "Batman";
        String director = "Steven Spielberg";
        int yearCreated = LocalDate.now().getYear() + 2;
        boolean inColor = true;
        int lengthInMinutes = 120;
        String genre = "Action";

        assertThrows( IllegalArgumentException.class,
                () -> new Movie(title, director, yearCreated, inColor, lengthInMinutes, genre));
    }

    //Test 4 længde 0 afvises
    @Test
    void length0IsRejected() {
        String title = "Batman";
        String director = "Steven Spielberg";
        int yearCreated = 2015;
        boolean inColor = true;
        int lengthInMinutes = 0;
        String genre = "Action";

        assertThrows(IllegalArgumentException.class,
                () -> new Movie(title, director, yearCreated, inColor, lengthInMinutes, genre));
    }

    //Test 5 En ugyldig setter-værdi efterlader den gamle værdi uændret.
    @Test
    void invalidTitleDoesNotChangeOldTitle() {
        String title = "Batman";
        String director = "Steven Spielberg";
        int yearCreated = 2015;
        boolean inColor = true;
        int lengthInMinutes = 120;
        String genre = "Action";

        Movie movie = new Movie(title, director, yearCreated, inColor, lengthInMinutes, genre);

        assertThrows(IllegalArgumentException.class,
                () -> movie.setTitle(""));

        assertEquals("Batman", movie.getTitle());
    }

    //Test 7 en ugyldig redigering må ikke ændre noget
    @Test
    void InvalidEditDoesNothing() {
        MovieCollection collection = new MovieCollection();
        collection.addMovie("Batman", "Steven Spielberg", 2008, true, 174, "Action");

        Movie batman = collection.searchMovies("Batman").get(0);
        assertThrows(IllegalArgumentException.class,
                () -> collection.editMovie(batman, "Prutman", "Steven Spielberg", 1800, true, 174, "Action"));

        assertEquals("Batman", batman.getTitle());
        assertEquals(2008, batman.getYearCreated());


    }

}
