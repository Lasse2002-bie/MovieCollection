import java.util.ArrayList;

public class MovieCollection {
    private ArrayList<Movie> movies;

    public MovieCollection() {
        movies = new ArrayList<>();
    }

    public void addMovie(String title, String director, int yearCreated, boolean inColor,
                         int lengthInMinutes, String genre) {
        // TODO US1
        Movie movie = new Movie(title, director, yearCreated, inColor, lengthInMinutes, genre);
        movies.add(movie);
    }

    public ArrayList<Movie> getMovies() {

        return movies;   // TODO US3
    }

    public int getNumberOfMovies() {
        return movies.size();      // TODO
    }
// Finder alle film, hvor søgeteksten indgår i titlen - uanset store og små bogstaver
    public ArrayList<Movie> searchMovies(String searchText) {
        ArrayList<Movie> matches = new ArrayList<>();
        String text = searchText.toLowerCase();
        for (Movie movie : movies) {
            if (movie.getTitle().toLowerCase().contains(text)) {
                matches.add(movie);
            }
        }
        return matches;
            // TODO US4
    }

    public boolean editMovie(Movie movie, String title, String director, int yearCreated,
                             boolean inColor, int lengthInMinutes, String genre) {

        if (movies.contains(movie)) {
            movie.setTitle(title);
            movie.setDirector(director);
            movie.setYearCreated(yearCreated);
            movie.setInColor(inColor);
            movie.setLengthInMinutes(lengthInMinutes);
            movie.setGenre(genre);

            return true;
        }
        return false;  // TODO US5
    }

    public boolean deleteMovie(Movie movie) {
        return movies.remove(movie);
        // TODO US6
    }
}
