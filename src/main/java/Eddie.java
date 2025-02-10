import java.util.Scanner;

public class Eddie {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Display.showStartScreen();

        while(true) {
            String userInput = scanner.nextLine();
            if(userInput.equals("bye")) {
                Display.showEndScreen();
                break;
            }
            Display.echoUserInput(userInput);
        }
        scanner.close();
    }
}

class Display {
    public static void showStartScreen() {
        String logo = "  ______   ______    ______    ___   ______  \n"
                    + " |  ____| |  __  \\  |  __  \\   | |  |  ____| \n"
                    + " | |___   | |  | |  | |  | |   | |  | |___   \n"
                    + " |  ___|  | |  | |  | |  | |   | |  |  ___|  \n"
                    + " | |____  | |__| |  | |__| |   | |  | |____  \n"
                    + " |______| |_____/   |______/   |_|  |______|";
        String startScreen = "Eddie:\n"
                + "Hello! I'm Eddie, your friendly assistant.\n"
                + "How can I help you today?\n";

        printLine();
        System.out.println(logo);
        printLine();
        System.out.println(startScreen);
        printLine();
    }
    public static void showEndScreen() {
        String endScreen = "Eddie:\n"
                        + "Goodbye! Hope to see you soon!\n";
        printLine();
        System.out.println(endScreen);
        printLine();
    }

    public static void printLine() {
        System.out.println("____________________________________________________________");
    }

    public static void echoUserInput(String input) {
        printLine();
        System.out.println("Eddie:\n" + input);
        printLine();
    }
}
