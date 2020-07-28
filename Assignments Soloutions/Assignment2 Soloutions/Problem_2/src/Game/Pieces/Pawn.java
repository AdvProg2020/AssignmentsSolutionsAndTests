package Game.Pieces;

import Game.Board;
import Player.Player;

public class Pawn extends Piece {
    public Pawn(Player owner, Board board, int x, int y, boolean isWhite) {
        super(owner, board, x, y, isWhite);
        if (isWhite)
            this.name = "Pw";
        else
            this.name = "Pb";
    }

    @Override
    protected void getSpotsInRange() {
        for (int i = 0 ; i < 8 ; i++)
            for (int j = 0 ; j < 8 ; j++)
                spotsInRange[i][j] = false;
        if (isWhite) {
            if (x <= 6) {
                spotsInRange[x + 1][y] = board.isEmpty(x + 1, y);
                if (y <= 6)
                    spotsInRange[x + 1][y + 1] = this.isRival(board.getPieceByCoordinates(x + 1, y + 1));
                if (y >= 1)
                    spotsInRange[x + 1][y - 1] = this.isRival(board.getPieceByCoordinates(x+1, y-1));
            }
            if (this.getX() == 1)
                spotsInRange[x + 2][y] = board.isEmpty(x + 1, y) && board.isEmpty(x + 2, y);
        }else {
            if (x >= 1) {
                spotsInRange[x - 1][y] = board.isEmpty(x - 1, y);
                if (y <= 6)
                    spotsInRange[x - 1][y + 1] = this.isRival(board.getPieceByCoordinates(x - 1, y + 1));
                if (y >= 1)
                    spotsInRange[x - 1][y - 1] = this.isRival(board.getPieceByCoordinates(x - 1, y - 1));
            }
            if (this.getX() == 6)
                spotsInRange[x - 2][y] = board.isEmpty(x - 1, y) && board.isEmpty(x - 2, y);
        }
    }
}
