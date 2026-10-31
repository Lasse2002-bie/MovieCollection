import java.io.File; //gør det muligt at arbejde med en film
import java.io.FileNotFoundException; //Exception hvis filen ikke kan findes
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList; // Gør det muligt at bruge Arralist
import java.util.Scanner; // Scanner bruges til at læse fra filen

public class FileHandler {

    // Gemmer navnet på den fil vi vil arbejde med, fx "movies.csv"
    private String fileName;

    //Tæller hvor mange ugyldige linjer der springes over
    private int skippedLineCount;

    // Constructoren modtager filnavnet og gemmer det i fileName
    public FileHandler (String fileName) {
        this.fileName = fileName;
    }

    //Indlæser film fra filen og returnerer dem som en ArrayList
    // throws betyder at FileNotFoundException sendes videre, hvis filen ikke findes
    public ArrayList<Movie> loadMovies() throws FileNotFoundException {

        //Nulstiller antal ugyldige linjer
        skippedLineCount = 0;

        // Tom liste hvor de indlæste film senere bliver gemt
        ArrayList<Movie> movies = new ArrayList<>();

        // Åbner filen og gør Scanner klar til at læse den
        Scanner fileScanner = new Scanner(new File(fileName));

        //Tjekker om filen har en linje
        if (fileScanner.hasNextLine()) {

            // Læser første linje uden at gennem den, så headeren springes over
            fileScanner.nextLine();
        }

        //Kører så længe der stadig er flere linjer i filen
        while (fileScanner.hasNextLine()) {

            //Læser den næste linje fra CSV-filen og gemmer den som tekst
            String line = fileScanner.nextLine();

            //Prøver at lave CSV linjen om til movie
            Movie movie = parseMovie(line);

            //Tilføjer kun filmen til liten hvis den kunne læses korrekt

            if (movie != null) {
                movies.add(movie);
            } else {
                //Tæller en ugyldig CSV linje
                skippedLineCount++;
            }

        }
        //Lukker filen efter alle linjer er læst
        fileScanner.close();

        //Returnerer lisen med alle de film der blev indlæst
        return movies;
    }

        private Movie parseMovie(String line) {

        //Deler CSV linje op i felter ved hvert semikolon
            String [] fields = line.split(";", -1);
            //Hvis linjen ikke har 7 felter er den ugyldig
            if (fields.length != 7) {
                return null;
            }

            // Henter titel og instruktør fra CSV felterne
            try {
                String title = fields[0];
                String director = fields[1];
                int yearCreated = Integer.parseInt(fields[2]);
                boolean inColor = Boolean.parseBoolean(fields[3]);
                int lengthInMinutes = Integer.parseInt(fields[4]);
                String genre = fields[5];

                //Oprette movie med de værdier vi har læst fra CSV filen
                Movie movie = new Movie(title, director, yearCreated, inColor, lengthInMinutes, genre);

                //Hvis filmen har en dato for hvornår den sidst blev set
                if (!fields[6].isEmpty()) {
                    movie.markAsWatched(LocalDate.parse(fields[6]));
                }

                //Returnerer den færdige Movie
                return movie;
            } catch (IllegalArgumentException e) {
                //Hvis noget i linjen er ugyldigt returnerer vi null
                return null;
            }
    }

    //Returnerer hvor mange ugyldige linjer der blev sprunget over
    public int getSkippedLineCount() {
        return skippedLineCount;
    }
    public void saveMovies(ArrayList<Movie> movies) throws FileNotFoundException {

        //Åbner filen så vi kan skrive til den
        PrintStream output = new PrintStream(new File(fileName));

        //Skriver headeren som første linje i CSV filen
        output.println("title;director;yearCreated;inColor;lengthInMinutes;genre;lastwatched");

        //Går gennem alle film i listen én ad gangen
        for (Movie movie : movies) {
            // Laver filmen om til en CSV linje og skriver den i filen
            output.println(toCsvLine(movie));
        }

        //Lukker filen efter alle film er gemt
        output.close();
    }

    // Laver en movie om til tekst der kan gemmes i CSV filen
    private String toCsvLine(Movie movie) {
        //Starter tom, hvis filmen aldrig er blevet set
        String lastWatched = "";

        //Hvis filmen har været set gemmer vi datoen som tekst
        if (movie.hasBeenWatched()) {
            lastWatched = movie.getLastWatched().toString();

        }
        //Samler alle filmens oplysninger til CSV linje
        return movie.getTitle() + ";" + movie.getDirector() + ";" + movie.getYearCreated() + ";" + movie.isInColor() + ";" + movie.getLengthInMinutes() + ";" + movie.getGenre() + ";" + lastWatched;
    }
}
