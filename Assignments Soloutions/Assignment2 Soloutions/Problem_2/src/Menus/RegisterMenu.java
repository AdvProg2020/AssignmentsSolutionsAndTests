package Menus;
import Player.Player;

import java.util.ArrayList;

public class RegisterMenu extends Menu {

    public int getCommand(String input) {
        output = 0;
        if (input.matches("register \\S+ \\S+")) {
            processRegister(input.split(" "));
        } else if (input.matches("login \\S+ \\S+")) {
            processLogin(input.split(" "));
        } else if (input.matches("remove \\S+ \\S+")) {
            processRemove(input.split(" "));
        } else if (input.equals("list_users")) {
            processList();
        } else if (input.equals("help")) {
            processHelp();
        } else if (input.equals("exit")) {
            output = -1;
            System.out.print("program ended");
            exitMenu = true;
        } else {
            showError("invalidCommand");
        }
        return output;
    }

    private void processRegister(String[] splitInput) {
        if(checkUsernameAndPassword(splitInput)) {
            if (Player.getPlayerByUsername(splitInput[1]) != null)
                showError("alreadyExists");
            else {
                new Player(splitInput[1], splitInput[2]);
                System.out.println("register successful");
            }
        }
    }

    private void processLogin(String[] splitInput) {
        if(checkUsernameAndPassword(splitInput)) {
            if (Player.getPlayerByUsername(splitInput[1]) == null)
                showError("noUsers");
            else if (!Player.validateUsernameAndPassword(splitInput[1], splitInput[2]))
                showError("wrongPass");
            else {
                System.out.println("login successful");
                Menu.loggedOnPlayer = Player.getPlayerByUsername(splitInput[1]);
                output = 1;
                exitMenu = true;
            }
        }
    }

    private void processRemove(String[] splitInput) {
        if(checkUsernameAndPassword(splitInput)) {
            if(Player.getPlayerByUsername(splitInput[1]) == null)
                showError("noUsers");
            else if(!Player.validateUsernameAndPassword(splitInput[1], splitInput[2]))
                showError("wrongPass");
            else {
                Player.removePlayer(splitInput[1]);
                System.out.println("removed "+splitInput[1]+" successfully");
            }
        }
    }

    private void processList() {
        String[] allPlayers = Player.getAllPlayers();
        for(int i = 0 ; i < allPlayers.length ; i++) {
            for(int j = i ; j < allPlayers.length ; j++) {
                if(allPlayers[j].compareTo(allPlayers[i]) < 0) {
                    String tmp = allPlayers[j];
                    allPlayers[j] = allPlayers[i];
                    allPlayers[i] = tmp;
                }
            }
        }
        for (String player : allPlayers) {
            System.out.println(player);
        }
    }

    private void processHelp() {
        System.out.println("register [username] [password]\n" +
                "login [username] [password]\n" +
                "remove [username] [password]\n" +
                "list_users\n" +
                "help\n" +
                "exit");
    }
}
