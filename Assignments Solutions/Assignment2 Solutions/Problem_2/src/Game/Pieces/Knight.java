package Game.Pieces;

import Game.Board;
import Player.Player;

public class Knight extends Piece {
    public Knight(Player owner, Board board, int x, int y, boolean isWhite) {
        super(owner,board, x, y, isWhite);
        if (isWhite)
            this.name = "Nw";
        else
            this.name = "Nb";
    }

    @Override
    protected void getSpotsInRange() {
        for (int i = 0 ; i < 8 ; i++)
            for (int j = 0 ; j < 8 ; j++)
                spotsInRange[i][j] = false;

        int[][] changes ={{2,-1},{2,1},{1,2},{-1,2},{-2,1},{-2,-1},{1,-2},{-1,-2}};
        for (int i = 0 ; i < 8 ; i++) {
            int X = x + changes[i][0];
            int Y = y + changes[i][1];
            if (board.spotInBoardRange(X, Y)) {
                if (checkSpot(X, Y))
                    spotsInRange[X][Y] = true;
            }
        }
    }
}
