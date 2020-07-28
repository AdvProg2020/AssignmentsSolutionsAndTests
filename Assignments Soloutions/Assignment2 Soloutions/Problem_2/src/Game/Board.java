package Game;

import Game.Pieces.*;
import Player.Player;

public class Board {
    private Piece selected;
    private Piece lastKilled;
    private Player player1;
    private Player player2;
    private Piece[][] allPieces = new Piece[8][8];

    public Board(Player player1, Player player2) {
        selected = null;
        lastKilled = null;
        this.player1 = player1;
        this.player2 = player2;
        allPieces[0][0] = new Rook(player1, this, 0, 0, true);
        allPieces[0][7] = new Rook(player1, this, 0, 7, true);
        allPieces[0][1] = new Knight(player1, this, 0, 1, true);
        allPieces[0][6] = new Knight(player1, this, 0, 6, true);
        allPieces[0][2] = new Bishop(player1, this, 0, 2, true);
        allPieces[0][5] = new Bishop(player1, this, 0, 5, true);
        allPieces[0][3] = new Queen(player1, this, 0, 3, true);
        allPieces[0][4] = new King(player1, this, 0, 4, true);
        for (int i = 0 ; i < 8 ; i++)
            allPieces[1][i] = new Pawn(player1, this, 1, i, true);
        allPieces[7][0] = new Rook(player2, this, 7, 0, false);
        allPieces[7][7] = new Rook(player2, this, 7, 7, false);
        allPieces[7][1] = new Knight(player2, this, 7, 1, false);
        allPieces[7][6] = new Knight(player2, this, 7, 6, false);
        allPieces[7][2] = new Bishop(player2, this, 7, 2, false);
        allPieces[7][5] = new Bishop(player2, this, 7, 5, false);
        allPieces[7][3] = new Queen(player2, this, 7, 3, false);
        allPieces[7][4] = new King(player2, this, 7, 4, false);
        for (int i = 0 ; i < 8 ; i++)
            allPieces[6][i] = new Pawn(player2, this, 6, i, false);
    }

    public Piece getPieceByCoordinates(long x, long y) {
        return allPieces[(int)x][(int)y];
    }

    public Piece getSelected() {
        return selected;
    }

    public void setLastKilled(Piece lastKilled) {
        this.lastKilled = lastKilled;
    }

    public Piece getLastKilled() {
        return lastKilled;
    }

    public int checkKingKilled() {
        if (lastKilled != null) {
            if (lastKilled.getName().equals("Kw"))
                return 1;
            else if (lastKilled.getName().equals("Kb"))
                return 2;
            else
                return 0;
        }else
            return 0;
    }

    public boolean isEmpty(long x, long y) {
        if (allPieces[(int)x][(int)y] == null)
            return true;
        return false;
    }

    @Override
    public String toString() {
        StringBuilder stringBoard = new StringBuilder();
        for (int i = 7 ; i >= 0 ; i --) {
            for (int j = 0 ; j < 8 ; j ++) {
                if (allPieces[i][j] == null)
                    stringBoard.append("  |");
                else
                    stringBoard.append(allPieces[i][j].getName()+"|");
            }
            if (i != 0)
                stringBoard.append("\n");
        }
        return stringBoard.toString();
    }

    public void setSelected(Piece selected) {
        this.selected = selected;
    }

    public void moveSelectedToCoordinates(long x, long y) {
        allPieces[selected.getX()][selected.getY()] = null;
        selected.setX((int)x);
        selected.setY((int)y);
        allPieces[(int)x][(int)y] = selected;
    }

    public boolean spotInBoardRange(long x, long y) {
        if (x <= 7 && x >= 0 && y <= 7 && y >= 0)
            return true;
        return false;
    }

    public void restoreLastKilledPiece() {
        allPieces[lastKilled.getX()][lastKilled.getY()] = lastKilled;
        lastKilled = null;
    }
}
