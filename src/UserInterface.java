import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {

    //Scanner bruges til at læse brugeres input
    private Scanner scanner;

    //Controller forbinder UI med resten af programmet.
    private Controller controller;

    //Opretter scanner og controller når UI oprettes
    public UserInterface() {
        scanner = new Scanner(System.in);
        controller = new Controller();
    }

    //Starter programmet og viser menuen igen, så længe running er true
    public void startProgram() {
        boolean running = true;

        while (running) {
            System.out.println("1. Opret en film");
            System.out.println("2. Vis alle film");
            System.out.println("3. Søg efter film");
            System.out.println("4. Redigér en film");
            System.out.println("5. Slet en film");
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

                case "7" -> getMoviesNotWatchedForDays();

                //Sætter running til false, så while-løkke og program stopper
                case "0" -> {
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

        System.out.println("Year Created");
        //parseInt omdanner brugeres tekst-input til et int
        int yearCreated = Integer.parseInt(scanner.nextLine());

        System.out.println("Lenght in minutes: ");
        int lengthInMinutes = Integer.parseInt(scanner.nextLine());

        System.out.println("Is it in color?: ");
        boolean inColor = Boolean.parseBoolean(scanner.nextLine());

        System.out.println("Genre: ");
        String genre = scanner.nextLine();

        //Sender alle oplysninger videre til Controller, som sender dem til MovieCollection
        //MovieCollection opretter Movie-objektet og gemmer det i listen
        controller.addMovie(title, director, yearCreated, inColor, lengthInMinutes, genre);
        System.out.println(title + " er tilføjet til filmsamlingen");
    }

    //Henter alle film gennem Controller
    private void showAllMovies() {
        ArrayList<Movie> movies = controller.getMovies();

        //Stopper metoden hvis filmsamlingen er tom
        if (movies.isEmpty()) {
            System.out.println("Filmlisten er tom");
            return;
        }

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

        System.out.println("Vælg nummer: ");
        int number = Integer.parseInt(scanner.nextLine());

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

        System.out.println("Year Created [" + selectedMovie.getYearCreated() + "]: ");
        String yearInput = scanner.nextLine();

        int newYearCreated;

        if (yearInput.isEmpty()) {
            newYearCreated = selectedMovie.getYearCreated();
        } else {
            newYearCreated = Integer.parseInt(yearInput);
        }

        System.out.println("I farver (true/false) [" + selectedMovie.isInColor() + "]: ");
        String colorInput = scanner.nextLine();

        boolean newInColor;

        if (colorInput.isEmpty()) {
            newInColor = selectedMovie.isInColor();
        } else {
            newInColor = Boolean.parseBoolean(colorInput);
        }

        System.out.println("Length in minutes [" + selectedMovie.getLengthInMinutes() + "]: ");
        String lengthInput = scanner.nextLine();

        int newLengthInMinutes;

        if (lengthInput.isEmpty()) {
            newLengthInMinutes = selectedMovie.getLengthInMinutes();
        } else {
            newLengthInMinutes = Integer.parseInt(lengthInput);
        }

        System.out.println("Genre [" + selectedMovie.getGenre() + "]: ");
        String newGenre = scanner.nextLine();

        if (newGenre.isEmpty()) {
            newGenre = selectedMovie.getGenre();
        }

        //Sender den valgte film og de nye værdier til Controller
        //Controller sender dem videre til MovieCollection, som ændrer Movie-objektet
        controller.editMovie(
                selectedMovie,
                newTitle,
                newDirector,
                newYearCreated,
                newInColor,
                newLengthInMinutes,
                newGenre);

        System.out.println("Filmen er opdateret");
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

        System.out.println("Vælg nummer: ");
        int number = Integer.parseInt(scanner.nextLine());

        //Henter den film brugeren valgte fra søgeresultaterne
        Movie selectedMovie = matches.get(number - 1);

        System.out.println("Vil du slette " + selectedMovie.getTitle() + "? (ja/nej): ");
        String confirm = scanner.nextLine();


        //Sletter kun filmen hvis brugeren svarer ja
        if (confirm.equalsIgnoreCase("ja")) {

            //Controller sender Movie-objekt videre til MovieCollection,
            //Som fjerner det fra Arraylisten
            controller.deleteMovie(selectedMovie);

            System.out.println("Filmen er slettet.");
        } else {
            System.out.println("Filmen blev ikke slettet.");
        }
    }

    private void getMoviesNotWatchedForDays() {
        System.out.println("Antal dage: ");
        int days = scanner.nextInt();

        //Gemmer de film som MovieCollection fandt og sendte tilbage gennem Controller
        ArrayList<Movie> matches = controller.getMoviesNotWatchedForDays(days);

        if (matches.isEmpty()) {
            System.out.println("Ingen film matcher din søgning");
        }

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
        }
    }


}
