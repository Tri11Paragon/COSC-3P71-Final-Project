package chess;

import java.util.ArrayList;

public class King extends ChessPiece {

    public King(Board b, boolean isWhite, int x, int y) {
        super(b,isWhite,x,y);
    }

    @Override
    public ArrayList<Move> getMoves() {
        ArrayList<Move> moves = new ArrayList<Move>();
        moves.addAll(super.getCardinalMoves(1));
        moves.addAll(super.getDiagonalMoves(1));
        return moves;
    }
}
