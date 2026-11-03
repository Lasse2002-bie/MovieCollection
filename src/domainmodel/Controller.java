package domainmodel;
import datasource.FileHandler;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.Comparator;

public class Controller {

    //Forbindelse til domainmodel.MovieCollection, hvor selve filmsamling ligger
    private MovieCollection movieCollection;
    private boolean changed = false;

    //Opretter en domainmodel.MovieCollection, når domainmodel.Controller bliver oprettet
    public Controller() {

        movieCollection = new MovieCollection();

        //Opretter Filehandler og fortæller hvilken fil den skal bruge
        fileHandler = new FileHandler("movies.csv");
    }

    //Modtager filmens oplysninger fra UI og sender videre til domainmodel.MovieCollection
    public void addMovie(String title, String director, int yearCreated,
                         boolean inColor, int lengthInMinutes, String genre) {

        movieCollection.addMovie(title, director, yearCreated,
                inColor, lengthInMinutes, genre);
        changed = true;
    }

    //Henter listen af film fra domainmodel.MovieCollection og sender videre til UI
    public ArrayList<Movie> getMovies() {
        return movieCollection.getMovies();
    }

    //Sender søgeteksten videre til domainmodel.MovieCollection og returnerer de film der matcher
    public ArrayList<Movie> searchMovies(String searchText) {
        return movieCollection.searchMovies(searchText);
    }

    //Sender den valgte film og de nye oplysninger videre til domainmodel.MovieCollection
    //Returnerer true eller false alt efter om redigeringen virkede
    public boolean editMovie(Movie movie, String title, String director,
                             int yearCreated, boolean inColor,
                             int lengthInMinutes, String genre) {

        boolean edited = movieCollection.editMovie(movie, title, director, yearCreated, inColor, lengthInMinutes, genre);
        if (edited) {
            changed = true;
        }
        return edited;
    }

    //Sender filmen videre til domainmodel.MovieCollection som forsøger at slette den
    public boolean deleteMovie(Movie movie) {
        boolean deleted = movieCollection.deleteMovie(movie);  // 1. gem svaret

        if (deleted) {
            changed = true;
        }

        return deleted;
    }

    public void markAsWatched(Movie movie, LocalDate date) {
        movie.markAsWatched(date);
        changed = true;

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

        //Tilføjer alle indlæste film til domainmodel.MovieCollection
        for (Movie movie : loadedMovies) {
            movieCollection.addMovie(movie);
        }
        //Returnerer antal indlæste film
        return loadedMovies.size();
    }

    //Henter antal ugyldige linjer fra datasource.FileHandler
    public int getSkippedLineCount() {
        return fileHandler.getSkippedLineCount();
    }

    //Gemmer alle film fra domainmodel.MovieCollection i CSV filen
    public boolean saveMoviesIfChanged() throws FileNotFoundException {
        if (changed) {
            fileHandler.saveMovies(movieCollection.getMovies());
            changed = false;
            return true;
        }
        return false;
    }

    //Tager antal dage og ser hvilke film man har set indenfor det tidsrum.
    public ArrayList<Movie> getMoviesNotWatchedForDays(int days) {
        LocalDate limit = LocalDate.now().minusDays(days);
        return movieCollection.getMoviesNotWatchedSince(limit);

    }

    public ArrayList<Movie> getMoviesSortedByTitle() {
        return movieCollection.getMoviesSortedByTitle();
    }

    private Comparator<Movie> comparatorFor(SortField field) {
        return switch (field) {
            case TITLE -> new TitleComparator();
            case DIRECTOR -> new DirectorComparator();
            case LENGTH -> new LengthComparator();
            case GENRE -> new GenreComparator();
            case YEAR -> new YearComparator();

        };
    }

    public ArrayList<Movie> getMoviesSortedBy(SortField field) {
        return movieCollection.getMoviesSorted(comparatorFor(field));
    }
}