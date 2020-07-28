package Game;

import Game.Pieces.Piece;
import Player.Player;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Game {
    private Player player1, player2, turn;
    private long limit;
    private boolean isLimited;
    private Board board;
    private boolean selected, moved, usedUndo;
    private ArrayList<String> killedPieces = new ArrayList<>();
    private ArrayList<String> moves = new ArrayList<>();
    public Game(Player player1, Player player2, long limit) {
        this.player1 = player1;
        this.player2 = player2;
        this.turn = player1;
        this.limit = limit;
        if (limit == 0)
            isLimited = false;
        else
            isLimited = true;
        this.player1.setUndoLeft(2);
        this.player2.setUndoLeft(2);
        this.board = new Board(player1, player2);
    }

    public String getBoardAsString() {
        return board.toString();
    }

    public int selectPiece(long x, long y) {
        if ( x < 0 || x > 7 || y < 0 || y > 7)
            return -2;
        else if (board.isEmpty(x, y))
            return -1;
        else if(turn != board.getPieceByCoordinates(x, y).getOwner())
            return 0;
        else if (turn == board.getPieceByCoordinates(x, y).getOwner()) {
            selected = true;
            board.setSelected(board.getPieceByCoordinates(x, y));
            return 1;
        }
        return -3;
    }

    public int deselectPiece() {
        if (!selected)
            return -1;
        else {
            board.setSelected(null);
            selected = false;
            return 1;
        }
    }

    public int movePiece(long x, long y) {
        if(moved)
            return -1;
        else if(x < 0 || x > 7 || y < 0 || y > 7)
            return -2;
        else if (!selected)
            return -3;
        else if(!board.getSelected().isInRange(x, y))
            return -4;
        else {
            //Move can be done
            moved = true;
            if (board.isEmpty(x, y)) {
                moves.add(createMoveString(board.getSelected(), (int) x, (int) y, false, null));
                turn.addToMoves(createMoveString(board.getSelected(), (int) x, (int) y, false, null));
                board.moveSelectedToCoordinates(x, y);
                return 1;
            } else {
                Piece killed = board.getPieceByCoordinates((int) x, (int) y);
                killedPieces.add(createKilledPiecesString(killed, (int) x, (int) y));
                killed.getOwner().addToKilledPieces(createKilledPiecesString(killed, (int) x, (int) y));
                moves.add(createMoveString(board.getSelected(), (int) x, (int) y, true, killed));
                turn.addToMoves(createMoveString(board.getSelected(), (int) x, (int) y, true, killed));
                board.setLastKilled(killed);
                board.moveSelectedToCoordinates(x, y);
                return 2;
            }
        }
    }

    private String createMoveString(Piece movedPiece, int destX, int destY, boolean hasKilled, Piece killed) {
        StringBuilder thisMove = new StringBuilder();
        thisMove.append(movedPiece.getName()).append(" ");
        thisMove.append(movedPiece.getX() + 1).append(",").append(movedPiece.getY() + 1);
        thisMove.append(" to ").append(1 + destX).append(",").append(1 + destY);
        if (hasKilled) {
            thisMove.append(" destroyed ").append(killed.getName());
        }
        return thisMove.toString();
    }

    private String createKilledPiecesString(Piece killed, int x, int y) {
        return killed.getName() + " killed in spot " + (x + 1) + "," + (y + 1);
    }

    public ArrayList<String> getKilledPieces() {
        return killedPieces;
    }

    public ArrayList<String> getMoves() {
        return moves;
    }

    public Player getTurn() {
        return turn;
    }

    public void undoMove() {
        if(turn.getUndoLeft() == 0) {
            System.out.println("you cannot undo anymore");
        }else if(!moved) {
            System.out.println("you must move before undo");
        }else if (usedUndo) {
            System.out.println("you have used your undo for this turn");
        }else {
            String lastMove = moves.get(moves.size()-1);
            int[] coordinates = new int[4];
            int i = 0;
            Pattern numberPattern = Pattern.compile("\\d");
            Matcher numberMatch = numberPattern.matcher(lastMove);
            while (numberMatch.find() && i < 4) {
                coordinates[i] = Integer.parseInt(numberMatch.group());
                i ++;
            }
            Piece lastSelected = board.getSelected();
            board.setSelected(board.getPieceByCoordinates(coordinates[2] - 1, coordinates[3] - 1));
            board.moveSelectedToCoordinates(coordinates[0]-1, coordinates[1]-1);
            board.setSelected(lastSelected);
            if(lastMove.contains("destroyed")) {
                killedPieces.remove(killedPieces.size() - 1);
                board.getLastKilled().getOwner().removeFromKilledPieces();
                board.restoreLastKilledPiece();
            }
            turn.removeFromMoves();
            moves.remove(moves.size() - 1);
            usedUndo = true;
            moved = false;
            turn.setUndoLeft(turn.getUndoLeft() - 1);
            System.out.println("undo completed");
        }
    }

    public int nextTurn() {
        if (!moved)
            System.out.println("you must move then proceed to next turn");
        else {
            if (turn == player1)
                turn = player2;
            else
                turn = player1;
            limit -= isLimited ? 1 : 0;
            selected = false;
            moved = false;
            usedUndo = false;
            System.out.println("turn completed");
            if (board.checkKingKilled() == 1) {
                finishGame(2);
                return 1;
            } else if (board.checkKingKilled() == 2) {
                finishGame(1);
                return 1;
            }
            if (isLimited && limit == 0) {
                finishGame(0);
                return 1;
            }
            board.setLastKilled(null);
            board.setSelected(null);
        }
        return 0;
    }

    public void showTurn() {
        System.out.print("it is player "+turn.getUsername()+" turn with color ");
        if (turn == player1)
            System.out.println("white");
        else
            System.out.println("black");
    }

    public void forfeitGame() {
        if (turn == player1)
            finishGame(-1);
        else
            finishGame(-2);
    }

    public void finishGame(int finishType) {
        switch (finishType) {
            case 0: //draw
                System.out.println("draw");
                player1.setScore(player1.getScore() + 1);
                player1.setDraws(player1.getDraws() + 1);
                player2.setDraws(player2.getDraws() + 1);
                player2.setScore(player2.getScore() + 1);
                break;
            case 1: //player 1 wins
                System.out.println("player "+player1.getUsername()+" with color white won");
                player1.setScore(player1.getScore() + 3);
                player1.setWins(player1.getWins() + 1);
                player2.setLosess(player2.getLosess() + 1);
                break;
            case 2: //player 2 wins
                System.out.println("player "+player2.getUsername()+" with color black won");
                player2.setScore(player2.getScore() + 3);
                player2.setWins(player2.getWins() + 1);
                player1.setLosess(player1.getLosess() + 1);
                break;
            case -1: //player 1 forfeits
                System.out.println("player "+player2.getUsername()+" with color black won");
                player1.setLosess(player1.getLosess() + 1);
                player1.setScore(player1.getScore() - 1);
                player2.setWins(player2.getWins() + 1);
                player2.setScore(player2.getScore() + 2);
                break;
            case -2: //player 2 forfeits
                System.out.println("player "+player1.getUsername()+" with color white won");
                player2.setLosess(player2.getLosess() + 1);
                player2.setScore(player2.getScore() - 1);
                player1.setWins(player1.getWins() + 1);
                player1.setScore(player1.getScore() + 2);
                break;
        }
    }
}
