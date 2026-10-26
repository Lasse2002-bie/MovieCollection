import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Movie {

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
        this.title = title;
        this.director = director;
        this.yearCreated = yearCreated;
        this.inColor = inColor;
        this.lengthInMinutes = lengthInMinutes;
        this.genre = genre;
    }

    //Getters burger til at hende filmens oplysninger
    public String getTitle() {
        return title;
    }

    //Setters bruges til at ændre filmens oplysninger
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getYearCreated() {
        return yearCreated;
    }

    public void setYearCreated(int yearCreated) {
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
        this.lengthInMinutes = lengthInMinutes;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
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
