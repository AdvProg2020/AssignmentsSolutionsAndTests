import OS.OS;

import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String input;
        while (!(input = scanner.nextLine()).trim().matches("install OS \\S+ \\S+"))
            System.out.println("invalid command");
        input = input.trim();
        OS os = new OS(scanner, input.split(" ")[2], input.split(" ")[3]);
        os.commandProcessor();
    }
}
