package chess;

import java.util.ArrayList;

public class Bishop extends ChessPiece {

    public Bishop(Board b, boolean isWhite, int x, int y) {
        super(b,isWhite,x,y);
    }

    @Override
    public ArrayList<Move> getMoves() {
        return new ArrayList<Move>(super.getDiagonalMoves(b.size()));
    }
}
