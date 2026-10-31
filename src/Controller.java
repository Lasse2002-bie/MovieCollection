import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.time.LocalDate;
import java.io.FileNotFoundException;
public class Controller {

    //Forbindelse til MovieCollection, hvor selve filmsamling ligger
    private MovieCollection movieCollection;

    //Opretter en MovieCollection, når Controller bliver oprettet
    public Controller() {

        movieCollection = new MovieCollection();

        //Opretter Filehandler og fortæller hvilken fil den skal bruge
        fileHandler = new FileHandler("movies.csv");
    }

    //Modtager filmens oplysninger fra UI og sender videre til MovieCollection
    public void addMovie(String title, String director, int yearCreated,
                         boolean inColor, int lengthInMinutes, String genre) {

        movieCollection.addMovie(title, director, yearCreated,
                inColor, lengthInMinutes, genre);
    }

    //Henter listen af film fra MovieCollection og sender videre til UI
    public ArrayList<Movie> getMovies() {
        return movieCollection.getMovies();
    }

    //Sender søgeteksten videre til MovieCollection og returnerer de film der matcher
    public ArrayList<Movie> searchMovies(String searchText) {
        return movieCollection.searchMovies(searchText);
    }

    //Sender den valgte film og de nye oplysninger videre til MovieCollection
    //Returnerer true eller false alt efter om redigeringen virkede
    public boolean editMovie(Movie movie, String title, String director,
                             int yearCreated, boolean inColor,
                             int lengthInMinutes, String genre) {

        return movieCollection.editMovie(movie, title, director, yearCreated,
                inColor, lengthInMinutes, genre);
    }

    //Sender filmen videre til MovieCollection som forsøger at slette den
    public boolean deleteMovie(Movie movie) {
        return movieCollection.deleteMovie(movie);
    }

    public void markAsWatched(Movie movie, LocalDate date) {
        movie.markAsWatched(date);

    }

    public long getDaysSinceWatched(Movie movie) {

        return movie.getDaysSinceWatched(LocalDate.now());
    }

    //Håndterer gemning og indlæsning af film fra CSV filen
    private FileHandler fileHandler;

    //Indlæser film fra CSV filen
    public int loadMovies() throws FileNotFoundException {
        //henter de indlæste film fra fileHandler
        ArrayList<Movie> loadedMovies = fileHandler.loadMovies();

        //Tilføjer alle indlæste film til MovieCollection
        for (Movie movie : loadedMovies) {
            movieCollection.addMovie(movie);
        }
        //Returnerer antal indlæste film
        return loadedMovies.size();
    }

    //Henter antal ugyldige linjer fra FileHandler
    public int getSkippedLineCount() {
        return fileHandler.getSkippedLineCount();
    }

    //Gemmer alle film fra MovieCollection i CSV filen
    public void saveMovies() throws FileNotFoundException {
        fileHandler.saveMovies(movieCollection.getMovies());
    }

    //Tager antal dage og ser hvilke film man har set indenfor det tidsrum.
    public ArrayList<Movie> getMoviesNotWatchedForDays(int days) {
        LocalDate limit = LocalDate.now().minusDays(days);
        return movieCollection.getMoviesNotWatchedSince(limit);

    }
}
