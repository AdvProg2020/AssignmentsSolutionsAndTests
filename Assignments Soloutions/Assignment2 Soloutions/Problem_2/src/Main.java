import Menus.GameMenu;
import Menus.MainMenu;
import Menus.RegisterMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;
        RegisterMenu registerMenu = new RegisterMenu();
        MainMenu mainMenu = new MainMenu();
        GameMenu gameMenu = null;
        int output = 0; //0:registerMenu    1:loginMenu     2: gameMenu     -1:exitProgram
        while (output != -1) {
            input = scanner.nextLine();
            switch (output) {
                case 0:
                    output = registerMenu.getCommand(input);
                    break;
                case 1:
                    output = mainMenu.getCommand(input);
                    gameMenu = mainMenu.getGameMenu();
                    break;
                case 2:
                    output = gameMenu.getCommand(input);
                    break;
            }
        }
    }
}
