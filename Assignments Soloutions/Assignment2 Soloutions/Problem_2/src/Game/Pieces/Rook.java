package Game.Pieces;

import Game.Board;
import Player.Player;

public class Rook extends Piece {
    public Rook(Player owner, Board board, int x, int y, boolean isWhite) {
        super(owner, board, x, y, isWhite);
        if (isWhite)
            this.name = "Rw";
        else
            this.name = "Rb";
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
    }
}
