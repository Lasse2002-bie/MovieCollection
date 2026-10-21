import java.util.Scanner;

public class UserInterface {

    private Scanner scanner;
    private Controller controller;

    public UserInterface() {
        scanner = new Scanner(System.in);
        controller = new Controller();
    }

    public void startProgram() {
        boolean running = true;

        while (running) {
            System.out.println("1. Opret en film");
            System.out.println("0. Afslut");
            System.out.println("Vælg: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> createMovie();

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

    private void createMovie() {
        System.out.println("Titel: ");
        String title = scanner.nextLine();
        System.out.println("Director: ");
        String director = scanner.nextLine();
        System.out.println("Year Created");
        int yearCreated = scanner.nextInt();
        System.out.println("Lenght in minutes: ");
        int lengthInMinutes = scanner.nextInt();
        System.out.println("Is it in color?: ");
        boolean inColor = scanner.nextBoolean();
        System.out.println("Genre: ");
        String genre = scanner.nextLine();


    }
}