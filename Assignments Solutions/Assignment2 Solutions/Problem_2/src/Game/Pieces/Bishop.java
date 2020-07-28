package Game.Pieces;

import Game.Board;
import Player.Player;

public class Bishop extends Piece {
    public Bishop(Player owner, Board board, int x, int y, boolean isWhite) {
        super(owner,board, x, y, isWhite);
        if (isWhite)
            this.name = "Bw";
        else
            this.name = "Bb";
    }

    @Override
    protected void getSpotsInRange() {
        for (int i = 0 ; i < 8 ; i++)
            for (int j = 0 ; j < 8 ; j++)
                spotsInRange[i][j] = false;

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
