package Menus;

import Player.Player;

public class MainMenu extends Menu {
    private GameMenu gameMenu = null;
    public int getCommand(String input) {
        output = 1;
        if (input.matches("new_game \\S+ -?\\d+")) {
            processNewGame(input.split(" "));
        } else if (input.equals("scoreboard")) {
            processScoreBoard();
        } else if (input.equals("help")) {
            processHelp();
        } else if (input.equals("logout")) {
            processLogout();
        }else if (input.equals("list_users")) {
            processList();
        } else {
            showError("invalidCommand");
        }
        return output;
    }

    private void processNewGame(String[] splitInput) {
        if(checkUsernameAndPassword(new String[]{"", splitInput[1], "pass"})) {
            if(Long.parseLong(splitInput[2]) < 0)
                showError("invalidLimit");
            else if(splitInput[1].equals(loggedOnPlayer.getUsername()))
                showError("selfPlaying");
            else if(Player.getPlayerByUsername(splitInput[1]) == null)
                showError("noUsers");
            else {
                System.out.println("new game started successfully between "+loggedOnPlayer.getUsername()+
                        " and "+splitInput[1]+" with limit "+ Long.parseLong(splitInput[2]));
                loggedOnPlayer.resetPlayer();
                Player.getPlayerByUsername(splitInput[1]).resetPlayer();
                gameMenu = new GameMenu(loggedOnPlayer, Player.getPlayerByUsername(splitInput[1]),
                        Long.parseLong(splitInput[2]));
                output = 2;
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

    public GameMenu getGameMenu() {
        return gameMenu;
    }

    private void processScoreBoard() {
        Player.sortPlayers();
        Player.printPlayers();
    }

    private void processHelp() {
        System.out.println("new_game [username] [limit]\n" +
                "scoreboard\n" +
                "list_users\n" +
                "help\n" +
                "logout");
    }

    private void processLogout() {
        System.out.println("logout successful");
        Menu.loggedOnPlayer = null;
        output = 0;
        exitMenu = true;
    }
}
