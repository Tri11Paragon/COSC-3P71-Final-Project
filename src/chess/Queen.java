package chess;

import java.util.ArrayList;

public class Queen extends ChessPiece {

    public Queen(Board b, boolean isWhite, int x, int y) {
        super(b,isWhite,x,y);
    }

    @Override
    public ArrayList<Move> getMoves() {
        ArrayList<Move> moves = new ArrayList<Move>();
        moves.addAll(super.getCardinalMoves(b.size()));
        moves.addAll(super.getDiagonalMoves(b.size()));
        return moves;
    }
}
