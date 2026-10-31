import java.io.FileNotFoundException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class UserInterface {

    //Scanner bruges til at læse brugeres input
    private Scanner scanner;

    //Controller forbinder UI med resten af programmet.
    private Controller controller;

    //Formatterer dato input fra brugeren.
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    //Opretter scanner og controller når UI oprettes
    public UserInterface() {
        scanner = new Scanner(System.in);
        controller = new Controller();
    }

    //Starter programmet og viser menuen igen, så længe running er true
    public void startProgram() {

        //Indlæseer gemte film fra movies.csv når programmet starter
        loadMovies();
        boolean running = true;

        while (running) {
            System.out.println("1. Opret en film");
            System.out.println("2. Vis alle film");
            System.out.println("3. Søg efter film");
            System.out.println("4. Redigér en film");
            System.out.println("5. Slet en film");
            System.out.println("6. Markér en film som set");
            System.out.println("7. Find film du ikke har set i et vist antal dage");
            System.out.println("0. Afslut");
            System.out.println("Vælg: ");

            String choice = scanner.nextLine().trim();

            //Brugerens valg bestemmer hvilken metode der køres
            switch (choice) {
                case "1" -> createMovie();
                case "2" -> showAllMovies();
                case "3" -> searchMovies();
                case "4" -> editMovie();
                case "5" -> deleteMovie();
                case "6" -> markMovieAsWatched();
                case "7" -> getMoviesNotWatchedForDays();

                //Sætter running til false, så while-løkke og program stopper
                case "0" -> {
                    saveMovies();
                    running = false;
                    System.out.println("Farvel!");
                }
                default -> {
                    System.out.println("Ukendt valg");
                }
            }
        }
    }

    //Læser oplysninger om en ny film fra brugeren
    private void createMovie() {
        System.out.println("Titel: ");
        String title = scanner.nextLine();

        System.out.println("Director: ");
        String director = scanner.nextLine();

        //parseInt omdanner brugeres tekst-input til et int
        int yearCreated = 0;
        boolean validInput = false;
        while (!validInput){
            yearCreated = readInt("Year Created: ");
            try {
                  Movie.validateYearCreated(yearCreated);
                  validInput=true;
            }
            catch(IllegalArgumentException e){
               System.out.println(e.getMessage() + " Prøv igen.");
            }
             

        }

        int lengthInMinutes = readInt("Length in minutes: ");

        boolean inColor = readYesNo("I farver (ja/nej");

        System.out.println("Genre: ");
        String genre = scanner.nextLine();

        //Sender alle oplysninger videre til Controller, som sender dem til MovieCollection
        //MovieCollection opretter Movie-objektet og gemmer det i listen
        try {
            controller.addMovie(title, director, yearCreated, inColor, lengthInMinutes, genre);
            System.out.println(title + " er tilføjet til filmsamlingen");
        } catch (IllegalArgumentException e) {
            System.out.println("Filmen blev ikke oprettet: " + e.getMessage());
        }
    }

    //Henter alle film gennem Controller
    private void showAllMovies() {
        ArrayList<Movie> movies = controller.getMovies();

        //Stopper metoden hvis filmsamlingen er tom
        if (movies.isEmpty()) {
            System.out.println("Filmlisten er tom");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        //Går gennem alle film i listen og viser oplysningerne om hver film
        for (Movie movie : movies) {
            System.out.println(movie.getTitle());
            System.out.println(movie.getDirector());
            System.out.println(movie.getYearCreated());
            System.out.println(movie.getGenre());
            System.out.println(movie.getLengthInMinutes());

            //Viser boolean-værdien som farver eller sort-hvis i stedet for true/false
            if (movie.isInColor()) {
                System.out.println("farver");
            } else {
                System.out.println("sort-hvid");
            }
            if (movie.hasBeenWatched()) {
                long days = controller.getDaysSinceWatched(movie);
                System.out.println("Sidst set: " + movie.getLastWatched().format(formatter) + " (for " + days + " dage siden)");

            } else {
                System.out.println("Sidst set: aldrig");
            }
        }
    }

    //Læser en søgetekst og sender den gennem Controller til MovieCollection
    private void searchMovies() {
        System.out.println("Søg efter titel: ");
        String searchText = scanner.nextLine();

        //Gemmer de film som MovieCollection fandt og sendte tilbage gennem Controller
        ArrayList<Movie> matches = controller.searchMovies(searchText);

        if (matches.isEmpty()) {
            System.out.println("Ingen film matcher din søgning");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        //Går gennem alle søgeresultater og viser filmens oplysninger
        for (Movie movie : matches) {
            System.out.println(movie.getTitle());
            System.out.println(movie.getDirector());
            System.out.println(movie.getYearCreated());
            System.out.println(movie.getGenre());
            System.out.println(movie.getLengthInMinutes());

            if (movie.isInColor()) {
                System.out.println("farver");
            } else {
                System.out.println("sort-hvid");
            }

            if (movie.hasBeenWatched()) {
                long days = controller.getDaysSinceWatched(movie);

                System.out.println("Sidst set: " + movie.getLastWatched().format(formatter)
                        + " (for " + days + " dage siden)");
            } else {
                System.out.println("Sidst set: aldrig");
            }
        }
    }

    private void editMovie() {
        System.out.println("Søg efter filmen du vil redigere");
        String searchText = scanner.nextLine();

        //Søger efter filmen gennem Controller og gemmer resultaterne
        ArrayList<Movie> matches = controller.searchMovies(searchText);

        if (matches.isEmpty()) {
            System.out.println("Ingen film matcher din søgning");
            return;
        }

        //Viser søgeresultaterne med numre, så brugeren kan vælge en film
        for (int i = 0; i < matches.size(); i++) {
            System.out.println((i + 1) + ". " + matches.get(i).getTitle());
        }

        int number = readInt("Vælg nummer: ");
        while (number < 0 || number > matches.size()) {
            System.out.println("Vælg et nummer mellem 1 og " + matches.size() + " - eller 0 for at fortryde.");
            number = readInt("Vælg nummer: ");
        }

        if (number == 0) {
            return;
        }
        //-1 bruges fordi Arraylist starter på index 0, mens brugeren vælger fra 1
        Movie selectedMovie = matches.get(number - 1);

        System.out.println("Title [" + selectedMovie.getTitle() + "]: ");
        String newTitle = scanner.nextLine();

        // Hvis brugeren trykker Enter, beholdes filmens gamle værdi
        if (newTitle.isEmpty()) {
            newTitle = selectedMovie.getTitle();
        }

        System.out.println("Director [" + selectedMovie.getDirector() + "]: ");
        String newDirector = scanner.nextLine();

        if (newDirector.isEmpty()) {
            newDirector = selectedMovie.getDirector();
        }

        int newYearCreated = readIntOrKeep("Year Created [" + selectedMovie.getYearCreated() + "] ", selectedMovie.getYearCreated());

        boolean newInColor = readYesNoOrKeep(
                "I farver (ja/nej) [" + selectedMovie.isInColor() + "]: ",
                selectedMovie.isInColor());

        int newLengthInMinutes = readIntOrKeep("Length in minutes [" + selectedMovie.getLengthInMinutes() + "]: ", selectedMovie.getLengthInMinutes());


        System.out.println("Genre [" + selectedMovie.getGenre() + "]: ");
        String newGenre = scanner.nextLine();

        if (newGenre.isEmpty()) {
            newGenre = selectedMovie.getGenre();
        }

        //Sender den valgte film og de nye værdier til Controller
        //Controller sender dem videre til MovieCollection, som ændrer Movie-objektet
        try {
            controller.editMovie(
                    selectedMovie,
                    newTitle,
                    newDirector,
                    newYearCreated,
                    newInColor,
                    newLengthInMinutes,
                    newGenre);
            System.out.println("Filmen er opdateret");

        } catch (IllegalArgumentException e) {
            System.out.println("Filmen blev ikke opdateret: " + e.getMessage());
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        System.out.println(selectedMovie.getTitle());
        System.out.println(selectedMovie.getDirector());
        System.out.println(selectedMovie.getYearCreated());
        System.out.println(selectedMovie.getGenre());
        System.out.println(selectedMovie.getLengthInMinutes());

        if (selectedMovie.isInColor()) {
            System.out.println("farver");
        } else {
            System.out.println("sort/hvid");
        }
        if (selectedMovie.hasBeenWatched()) {
            long days = controller.getDaysSinceWatched(selectedMovie);

            System.out.println("Sidst set: " + selectedMovie.getLastWatched().format(formatter)
                    + " (for " + days + " dage siden)");
        } else {
            System.out.println("Sidst set: aldrig");
        }

    }

    //Søger efter filmen der skal slettes gennem Controller
    private void deleteMovie() {
        System.out.println("Søg efter filmen du vil slette ");
        String searchText = scanner.nextLine();

        ArrayList<Movie> matches = controller.searchMovies(searchText);

        if (matches.isEmpty()) {
            System.out.println("Ingen film matcher din søgning");
            return;
        }

        for (int i = 0; i < matches.size(); i++) {
            System.out.println((i + 1) + ". " + matches.get(i).getTitle());

        }

        int number = readInt("Vælg nummer: ");

        while (number < 0 || number > matches.size()) {
            System.out.println("Vælg et nummer mellem 1 og " + matches.size() + " - eller 0 for at fortryde. ");
            number = readInt("Vælg nummer: ");
        }
        if (number == 0) {
            return;
        }

        //Henter den film brugeren valgte fra søgeresultaterne
        Movie selectedMovie = matches.get(number - 1);

        boolean confirm = readYesNo("Vil du slette " + selectedMovie.getTitle() + "? (ja/nej): ");

        if (confirm) {
            //Controller sender Movie-objekt videre til MovieCollection,
            //Som fjerner det fra Arraylisten
            controller.deleteMovie(selectedMovie);

            System.out.println("Filmen er slettet.");
        } else {
            System.out.println("Filmen blev ikke slettet.");
        }
    }

    // Søger efter den film brugeren vil markere som set
    private void markMovieAsWatched() {
        System.out.println("Søg efter filmen du vil markere som set");
        String searchText = scanner.nextLine();

        ArrayList<Movie> matches = controller.searchMovies(searchText);

        if (matches.isEmpty()) {
            System.out.println("Ingen film matcher din søgning");
            return;
        }

        // Viser søgeresultaterne med numre
        for (int i = 0; i < matches.size(); i++) {
            System.out.println((i + 1) + ". " + matches.get(i).getTitle());
        }

        int number = readInt("Vælg nummer: ");

        while (number < 0 || number > matches.size()) {
            System.out.println("Vælg et nummer mellem 1 og " + matches.size() + " - eller 0 for at fortryde.");
            number = readInt("Vælg nummer: ");
        }
        if (number == 0) {
            return;
        }

        // Henter den film brugeren valgte
        Movie selectedMovie = matches.get(number - 1);
         boolean validDate = false;

                 while(!validDate) {
                     LocalDate date = readDate("Hvornår så du den? (dd-mm-åååå, Enter = i dag): ");

                     try {
                         // Sender filmen til Controller, som markerer den som set i den valgte dato.
                         controller.markAsWatched(selectedMovie, date);

                         System.out.println(selectedMovie.getTitle() + " er markeret som set den: " + date.format(dateFormatter));
                         validDate = true;
                     } catch (IllegalArgumentException e) {
                         System.out.println("Filmen blev ikke markeret: " + e.getMessage() + " Prøv igen.");
                     }
                 }
    }

    private void getMoviesNotWatchedForDays() {

        int days = readInt("Antal dage: ");

        //Gemmer de film som MovieCollection fandt og sendte tilbage gennem Controller
        ArrayList<Movie> matches = controller.getMoviesNotWatchedForDays(days);


        if (matches.isEmpty()) {
            System.out.println("Ingen film matcher din søgning");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        //Går gennem alle søgeresultater og viser filmens oplysninger
        for (Movie movie : matches) {
            System.out.println(movie.getTitle());
            System.out.println(movie.getDirector());
            System.out.println(movie.getYearCreated());
            System.out.println(movie.getGenre());
            System.out.println(movie.getLengthInMinutes());

            if (movie.isInColor()) {
                System.out.println("I farve");
            } else {
                System.out.println("I sort-hvid");
            }
            if (movie.hasBeenWatched()) {
                long daysSinceWatched = controller.getDaysSinceWatched(movie);

                System.out.println("Sidst set: " + movie.getLastWatched().format(formatter)
                        + " (for " + daysSinceWatched + " dage siden)");
            } else {
                System.out.println("Sidst set: aldrig");
            }
        }
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.println(prompt);
            String input = scanner.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("\"" + input + "\" er ikke et helt tal. Prøv igen.");
            }
        }
    }

    private LocalDate readDate(String prompt) {
        while (true) {
            System.out.println(prompt);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                return LocalDate.now();
            }
            try {
                return LocalDate.parse(input, dateFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("\"" + input + "\" er ikke en dato på formen: dd-mm-åååå");
            }
        }
    }

    private boolean readYesNo(String prompt) {
        while (true) {
            System.out.println(prompt);
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("ja") || input.equalsIgnoreCase("j")) {
                return true;
            }

            if (input.equalsIgnoreCase("nej") || input.equalsIgnoreCase("n")) {
                return false;
            }
            System.out.println("Svar ja eller nej.");
        }
    }

    private int readIntOrKeep(String prompt, int oldValue) {
        while (true) {
            System.out.println(prompt);
            String input = scanner.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("\"" + input + "\" er ikke et helt tal. Prøv igen.");
            }

        }
    }

    private boolean readYesNoOrKeep(String prompt, boolean oldValue) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                return oldValue;
            }

            if (input.equalsIgnoreCase("ja") || input.equalsIgnoreCase("j")) {
                return true;
            }
            if (input.equalsIgnoreCase("nej") || input.equalsIgnoreCase("n")) {
                return false;
            }
            System.out.println("Svar ja eller nej.");
        }
    }

    //Indlæser de gemte film, når programmet starter
    private void loadMovies() {
        try {
            int count = controller.loadMovies();
            System.out.println(count + " film er indlæst.");

            int skipped = controller.getSkippedLineCount();

            if (skipped > 0) {
                System.out.println("Advarsel: " + skipped + " linje(r) i filen kunne ikke læses og er sprunget over.");
            }
        } catch (FileNotFoundException e) {

            //Hvis filen ikke findes, crasher programmet ikke
            System.out.println("Der er ingen gemt filmsamling endnu - du starter med en tom samling.");
        }

    }

    //Gemmer filmsamling i movies.csv
    private void saveMovies() {
        try {
            controller.saveMovies();
        } catch (FileNotFoundException e) {
            System.out.println("Filmsamlingen kunne ikke gemmes.");
        }
    }
}
