import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


public class Movie {
    private static final int FIRST_MOVIE_YEAR = 1888;

    //Variabler der gemmer oplysninger om én film
    private String title;
    private String director;
    private int yearCreated;
    private boolean inColor;
    private int lengthInMinutes;
    private String genre;
    private LocalDate lastWatched;


    //Constructor der opretter et Movie-objekt med alle filmens oplysninger
    public Movie(String title, String director, int yearCreated, boolean inColor,
                 int lengthInMinutes, String genre) {

        //this henviser til objektets egne variabler
        setTitle(title);
        setDirector(director);
        setGenre(genre);
        setYearCreated(yearCreated);
        setLengthInMinutes(lengthInMinutes);
        setInColor(inColor);
    }

    //Getters burger til at hende filmens oplysninger
    public String getTitle() {
        return title;
    }

    //Setters bruges til at ændre filmens oplysninger
    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Titel må ikke være tom");
        }
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        if (director == null || director.trim().isEmpty()) {
            throw new IllegalArgumentException("Director må ikke være tom");
        }
        this.director = director;
    }

    public int getYearCreated() {
        return yearCreated;
    }

    public void setYearCreated(int yearCreated) {
        if (yearCreated < FIRST_MOVIE_YEAR || yearCreated > LocalDate.now().getYear()+1) {
            throw new IllegalArgumentException("Årstallet skal være mellem 1888 og næste år");
        }
        this.yearCreated = yearCreated;
    }

    public boolean isInColor() {
        return inColor;
    }

    public void setInColor(boolean inColor) {
        this.inColor = inColor;
    }

    public int getLengthInMinutes() {
        return lengthInMinutes;
    }

    public void setLengthInMinutes(int lengthInMinutes) {
        if (lengthInMinutes < 1) {
            throw new IllegalArgumentException("Filmen skal være mindst 1 minut lang");
        }
        this.lengthInMinutes = lengthInMinutes;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        if (genre == null || genre.trim().isEmpty()) {
            throw new IllegalArgumentException("Genre må ikke være tom");
        }
        this.genre = genre;
    }

    public LocalDate getLastWatched() {
        return lastWatched;
    }

    public boolean hasBeenWatched() {
        return lastWatched != null;
    }

    public void markAsWatched(LocalDate date) {
        lastWatched = date;
    }

    public long getDaysSinceWatched(LocalDate today) {
        return ChronoUnit.DAYS.between(lastWatched, today);
    }
}
