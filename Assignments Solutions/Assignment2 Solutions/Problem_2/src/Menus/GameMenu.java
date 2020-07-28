package Menus;

import Game.Game;
import Player.Player;

public class GameMenu extends Menu{
    private Game game;
    private Player player1, player2;
    private long limit;

    public GameMenu(Player player1, Player player2, long limit) {
        this.player1 = player1;
        this.player2 = player2;
        this.limit = limit;
        game = new Game(player1, player2, limit);
    }


    public int getCommand(String input) {
        output = 2;
        if (input.matches("select -?\\d+,-?\\d+")) {
            processSelect(input.split(" ")[1]);
        } else if (input.equals("deselect")) {
            processDeselect();
        } else if (input.matches("move -?\\d+,-?\\d+")) {
            processMove(input.split(" ")[1]);
        } else if (input.equals("next_turn")) {
            processNextTurn();
        } else if (input.equals("show_turn")) {
            processShowTurn();
        } else if (input.equals("undo")) {
            processUndo();
        } else if (input.equals("undo_number")) {
            processUndoNumber();
        } else if (input.equals("show_moves")) {
            processShowMoves(false);
        } else if (input.equals("show_moves -all")) {
            processShowMoves(true);
        } else if (input.equals("show_killed")) {
            processShowKilled(false);
        } else if (input.equals("show_killed -all")) {
            processShowKilled(true);
        } else if (input.equals("show_board")) {
            processShowBoard();
        } else if (input.equals("help")) {
            processHelp();
        } else if (input.equals("forfeit")) {
            processForfeit();
        } else {
            showError("invalidCommand");
        }
        return output;
    }

    private void processSelect(String coordinates) {
        int resultSelecting;
        if(coordinates.split(",")[0].length() > 1 || coordinates.split(",")[1].length() > 1) {
            System.out.println("wrong coordination");
            return;
        }
        resultSelecting = game.selectPiece(Long.parseLong(coordinates.split(",")[0])-1,
                Long.parseLong(coordinates.split(",")[1])-1);
        switch (resultSelecting) {
            case 0:
                System.out.println("you can only select one of your pieces");
                break;
            case 1:
                System.out.println("selected");
                break;
            case -1:
                System.out.println("no piece on this spot");
                break;
            case -2:
                System.out.println("wrong coordination");
                break;
        }
    }

    private void processDeselect() {
        switch (game.deselectPiece()) {
            case 1:
                System.out.println("deselected");
                break;
            case -1:
                System.out.println("no piece is selected");
                break;
        }
    }

    private void processMove(String coordinates) {
        int resultMoving;
        if(coordinates.split(",")[0].length() > 1 || coordinates.split(",")[1].length() > 1) {
            System.out.println("wrong coordination");
            return;
        }
        resultMoving = game.movePiece(Long.parseLong(coordinates.split(",")[0])-1,
                Long.parseLong(coordinates.split(",")[1])-1);
        switch (resultMoving) {
            case -1:
                System.out.println("already moved");
                break;
            case -2:
                System.out.println("wrong coordination");
                break;
            case -3:
                System.out.println("do not have any selected piece");
                break;
            case -4:
                System.out.println("cannot move to the spot");
                break;
            case 1:
                System.out.println("moved");
                break;
            case 2:
                System.out.println("rival piece destroyed");
                break;
        }
    }

    private void processNextTurn() {
        int result = game.nextTurn();
        if (result == 1) {
            output = 1;
        }
    }

    private void processShowTurn() {
        game.showTurn();
    }

    private void processUndo() {
        game.undoMove();
    }

    private void processUndoNumber() {
        System.out.println("you have "+game.getTurn().getUndoLeft()+" undo moves");
    }

    private void processShowMoves(boolean showAll) {
        if (showAll) {
            for (String move : game.getMoves()) {
                System.out.println(move);
            }
        }else {
            for (String move : game.getTurn().getMoves()) {
                System.out.println(move);
            }
        }
    }

    private void processShowKilled(boolean showAll) {
        if (showAll) {
            for (String thisKill : game.getKilledPieces()) {
                System.out.println(thisKill);
            }
        }else {
            for (String thisKill : game.getTurn().getKilledPieces()) {
                System.out.println(thisKill);
            }
        }
    }

    private void processShowBoard() {
        System.out.println(this.game.getBoardAsString());
    }

    private void processHelp() {
        System.out.println("select [x],[y]\n" +
                "deselect\n" +
                "move [x],[y]\n" +
                "next_turn\n" +
                "show_turn\n" +
                "undo\n" +
                "undo_number\n" +
                "show_moves [-all]\n" +
                "show_killed [-all]\n" +
                "show_board\n" +
                "help\n" +
                "forfeit");
    }

    private void processForfeit() {
        System.out.println("you have forfeited");
        game.forfeitGame();
        output = 1;
    }
}
