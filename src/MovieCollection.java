import java.time.LocalDate;
import java.util.ArrayList;

//MovieCollection ejer selve listen med film. Det er altså her filmene faktisk bliver oprettet, fundet, ændret og slettet.

public class MovieCollection {

    //Listen der gemmer alle Movie-objekter i filmsamlingen
    private ArrayList<Movie> movies;

    //Opretter en tom Arraylist, når en MovieCollection bliver lavet
    public MovieCollection() {
        movies = new ArrayList<>();
    }
// Modtager filmens oplysninger fra controller og opretter et nyt Movie-objekt
    //Det nye Movie-objekt bliver derefter gemt i Arraylist
    public void addMovie(String title, String director, int yearCreated, boolean inColor,
                         int lengthInMinutes, String genre) {

        Movie movie = new Movie(title, director, yearCreated, inColor, lengthInMinutes, genre);
        movies.add(movie);
    }

//Returnerer listen af film til Controller, som kan sende den videre til UI
    public ArrayList<Movie> getMovies() {
        return movies;
    }

    //Returnerer hvor mange film der ligger i samlingen
    public int getNumberOfMovies() {
        return movies.size();      // TODO
    }

    //Søger gennem alle film og gemmer de film, hvor søgetekst indgår i titlen
    //toLowerCase gør at søgning ikke afhænger af stor og små
    public ArrayList<Movie> searchMovies(String searchText) {
        ArrayList<Movie> matches = new ArrayList<>();
        String text = searchText.toLowerCase();

        for (Movie movie : movies) {
            if (movie.getTitle().toLowerCase().contains(text)) {
                matches.add(movie);
            }
        }

        //Sender listen med søgeresultater til Controller
        return matches;
    }

    //Modtager den valgte film og de nye oplysninger fra Controller
    //Hvis filmen findes i samlingen, ændres dens værdier gennem setter
    public boolean editMovie(Movie movie, String title, String director, int yearCreated,
                             boolean inColor, int lengthInMinutes, String genre) {

        if (movies.contains(movie)) {
            Movie tempMovie = new Movie (title, director, yearCreated, inColor, lengthInMinutes, genre);
            movie.setTitle(title);
            movie.setDirector(director);
            movie.setYearCreated(yearCreated);
            movie.setInColor(inColor);
            movie.setLengthInMinutes(lengthInMinutes);
            movie.setGenre(genre);

            //true fortæller controller at redigering virkede
            return true;
        }

        // false betyder at filmen ikke blev fundet i samling
        return false;
    }

    //Fjerner den valgte Movie-objekt fra listen og returnerer om det virkede
    public boolean deleteMovie(Movie movie) {
        return movies.remove(movie);
        // TODO US6
    }
    //Laver en liste af Movies, som ikke er set endnu.
    public ArrayList<Movie> getMoviesNotWatchedSince(LocalDate date)
    {ArrayList<Movie> result = new ArrayList<>();
        for (Movie movie : movies) {
            if (!movie.hasBeenWatched() || movie.getLastWatched().isBefore(date)) {
                result.add(movie);
            }
        }
        return result;
    }

}
