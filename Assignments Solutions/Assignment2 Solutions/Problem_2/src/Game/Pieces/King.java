package Game.Pieces;

import Game.Board;
import Player.Player;

public class King extends Piece {
    public King(Player owner, Board board, int x, int y, boolean isWhite) {
        super(owner,board, x, y, isWhite);
        if (isWhite)
            this.name = "Kw";
        else
            this.name = "Kb";
    }

    @Override
    protected void getSpotsInRange() {
        for (int i = 0 ; i < 8 ; i++)
            for (int j = 0 ; j < 8 ; j++)
                spotsInRange[i][j] = false;

        int[][] changes = {{1,0},{1,1},{1,-1},{-1,0},{-1,1},{-1,-1},{0,1},{0,-1}};
        for (int i = 0 ; i < 8 ; i++) {
            int X = x + changes[i][0];
            int Y = y + changes[i][1];
            if (X >= 0 && X <= 7 && Y >= 0 && Y <= 7) {
            if (checkSpot(X, Y))
                spotsInRange[X][Y] = true;
            }
        }
    }
}
