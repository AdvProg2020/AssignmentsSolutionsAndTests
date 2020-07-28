package Menus;

import Player.Player;

import java.util.Scanner;

public abstract class Menu {
    protected boolean exitMenu = false;
    protected int output;
    protected static Player loggedOnPlayer;

    protected boolean checkUsernameAndPassword(String[] splitInput) {
        if(!splitInput[1].matches("\\w+")) {
            showError("invalidUsername");
            return false;
        }
        if(!splitInput[2].matches("\\w+")) {
            showError("invalidPass");
            return false;
        }
        return true;
    }

    protected void showError(String errorType) {
        switch (errorType) {
            case "invalidUsername":
                System.out.println("username format is invalid");
                break;
            case "invalidPass":
                System.out.println("password format is invalid");
                break;
            case "alreadyExists":
                System.out.println("a user exists with this username");
                break;
            case "noUsers":
                System.out.println("no user exists with this username");
                break;
            case "wrongPass":
                System.out.println("incorrect password");
                break;
            case "invalidLimit":
                System.out.println("number should be positive to have a limit or 0 for no limit");
                break;
            case "selfPlaying":
                System.out.println("you must choose another player to start a game");
                break;
            case "invalidCommand":
                System.out.println("invalid command");
                break;
        }
    }
}
