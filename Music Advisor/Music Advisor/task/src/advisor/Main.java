package advisor;

import java.util.Scanner;

public class Main {

    public static String accessServer;

    public static void main(String[] args) {

        if (args.length > 0 && "-access".equals(args[0])) {
            accessServer = args[1];
        } else {
            accessServer = "https://accounts.spotify.com";
        }

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().toLowerCase();
        Handler auth = new Handler();
        Command command = new Command();

        while (true) {
            switch (input) {
                case "auth" -> auth.auth();
                case "exit" -> {
                    System.out.println("---GOODBYE!---");
                    return;
                }
                default -> {
                    if (auth.getAccess()) {
                        command.execute(input);
                    } else {
                        System.out.println("Please, provide access for application.");
                    }
                }
            }
            // Ask next input
            input = scanner.nextLine();
        }
    }
}
    class Command {

        void execute(String command) {

            switch (command) {
                case "new" -> NewReleases.info();
                case "featured" -> Featured.info();
                case "categories" -> Catagories.info();
                case "playlists mood" -> MoodPlaylists.info();
            }
        }
    }


