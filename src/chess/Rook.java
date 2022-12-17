package chess;

import java.util.ArrayList;

public class Rook extends ChessPiece {

    public Rook(Board b, boolean isWhite, int x, int y) {
        super(b,isWhite,x,y);
    }

    @Override
    public ArrayList<Move> getMoves() {
        return new ArrayList<Move>(super.getCardinalMoves(b.size()));
    }
}
