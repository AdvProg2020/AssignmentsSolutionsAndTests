package Game.Pieces;

import Game.Game;
import Game.Board;
import Player.Player;

public abstract class Piece {
    protected boolean[][] spotsInRange = new boolean[8][8];
    protected final Player owner;
    protected int x,y;
    protected final Board board;
    protected String name;
    protected final boolean isWhite;

    public Piece(Player owner,Board board, int x, int y, boolean isWhite) {
        this.owner = owner;
        this.board = board;
        this.x = x;
        this.y = y;
        this.isWhite = isWhite;

    }

    public Player getOwner() {
        return owner;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isRival(Piece anotherPiece) {
        if (anotherPiece == null)
            return false;
        return !(this.isWhite == anotherPiece.isWhite);
    }

    public boolean isInRange(long x, long y) {
        if (board.spotInBoardRange(x, y)) {
            this.getSpotsInRange();
            return spotsInRange[(int)x][(int)y];
        }
        return false;
    }

    protected boolean checkSpot(int x, int y) {
        if (board.isEmpty(x, y) || this.isRival(board.getPieceByCoordinates(x, y)))
            return true;
        return false;
    }

    protected abstract void getSpotsInRange();
}
