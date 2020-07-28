package Game.Pieces;

import Game.Board;
import Player.Player;

public class Queen extends Piece {
    public Queen(Player owner, Board board, int x, int y, boolean isWhite) {
        super(owner, board, x, y, isWhite);
        if (isWhite)
            this.name = "Qw";
        else
            this.name = "Qb";
    }

    @Override
    protected void getSpotsInRange() {
        for (int i = 0 ; i < 8 ; i++)
            for (int j = 0 ; j < 8 ; j++)
                spotsInRange[i][j] = false;

        for (int i = 1 ; i < 8 - x ; i++) {
            if (checkSpot(x + i, y))
                spotsInRange[x + i][y] = true;
            else
                break;
            if (this.isRival(board.getPieceByCoordinates(x + i, y)))
                break;
        }
        for (int i = 1 ; i <= x ; i++) {
            if (checkSpot(x - i, y))
                spotsInRange[x - i][y] = true;
            else
                break;
            if (this.isRival(board.getPieceByCoordinates(x - i, y)))
                break;
        }
        for (int i = 1 ; i < 8 - y ; i++) {
            if (checkSpot(x, y + i))
                spotsInRange[x][y + i] = true;
            else
                break;
            if (this.isRival(board.getPieceByCoordinates(x, y + i)))
                break;
        }
        for (int i = 1 ; i <= y ; i++) {
            if (checkSpot(x, y - i))
                spotsInRange[x][y - i] = true;
            else
                break;
            if (this.isRival(board.getPieceByCoordinates(x, y - i)))
                break;
        }
        for (int i = 1 ; i < 8 - x && i < 8 - y ; i++) {
            if (checkSpot(x + i, y + i))
                spotsInRange[x + i][y + i] = true;
            else
                break;
            if (this.isRival(board.getPieceByCoordinates(x + i, y + i)))
                break;
        }
        for (int i = 1 ; i < 8 - x && i <= y ; i++) {
            if (checkSpot(x + i, y - i))
                spotsInRange[x + i][y - i] = true;
            else
                break;
            if (this.isRival(board.getPieceByCoordinates(x + i, y - i)))
                break;
        }
        for (int i = 1 ; i <= x && i < 8 - y ; i++) {
            if (checkSpot(x - i , y + i))
                spotsInRange[x - i][y + i] = true;
            else
                break;
            if (this.isRival(board.getPieceByCoordinates(x - i, y + i)))
                break;
        }
        for (int i = 1 ; i <= x && i <= y ; i++) {
            if (checkSpot(x - i, y - i))
                spotsInRange[x - i][y - i] = true;
            else
                break;
            if (this.isRival(board.getPieceByCoordinates(x - i, y - i)))
                break;
        }
    }
}
