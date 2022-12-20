package chess;

import java.awt.*;
import java.util.ArrayList;

import static ui.Display.loadImage;

public class King extends ChessPiece {

    private final Image whiteKing = loadImage("./resources/chess_piece_2_white_king.png");
    private final Image blackKing = loadImage("./resources/chess_piece_2_black_king.png");

    public King(Board b, boolean isWhite, int x, int y) {
        super(b,isWhite,x,y);
    }

    public Image getImage(){
        if (isWhite)
            return whiteKing;
        return blackKing;
    }

    @Override
    public ArrayList<Move> getMoves() {
        ArrayList<Move> moves = new ArrayList<Move>();
        moves.addAll(super.getCardinalMoves(1));
        moves.addAll(super.getDiagonalMoves(1));
        if (isFirstMove()){
            ChessPiece rook = null;
            // castling
            if (isWhite){
                if ((rook = b.get(0, 0)) != null && checkIfRookValid(rook))
                    moves.add(b.checkIfMoveValid(new Move(2, 0, Move.SpecialConditions.leftCastle), isWhite));
                if ((rook = b.get(b.size() - 1, 0)) != null && checkIfRookValid(rook))
                    moves.add(b.checkIfMoveValid(new Move(b.size() - 2, 0, Move.SpecialConditions.rightCastle), isWhite));
            } else {
                if ((rook = b.get(0, b.size()-1)) != null && checkIfRookValid(rook))
                    moves.add(b.checkIfMoveValid(new Move(2, b.size()-1, Move.SpecialConditions.leftCastle), isWhite));
                if ((rook = b.get(b.size() - 1, b.size()-1)) != null && checkIfRookValid(rook))
                    moves.add(b.checkIfMoveValid(new Move(b.size() - 2, b.size()-1, Move.SpecialConditions.rightCastle), isWhite));
            }
        }
        return prune(moves);
    }

    @Override
    public void applySpecialMove(Move moveWithSpecial){
        var specialCondition = moveWithSpecial.getSpecialCondition();
        if(specialCondition == Move.SpecialConditions.leftCastle)
            castleLeft();
        else if (specialCondition == Move.SpecialConditions.rightCastle)
            castleRight();
    }

    private void castleRight(){
        // casting has to move the rook on the right size of the king from white's perspective
        if (this.isWhite)
            b.movePiece(b.size() - 1, 0, b.size() - 3, 0);
        else
            b.movePiece(b.size()-1, b.size()-1, b.size()-3, b.size()-1)
            b.set(b.size()-3, b.size()-1, b.get(b.size()-1, b.size()-1));
    }

    private boolean checkIfRookValid(ChessPiece piece){
        return piece.isFirstMove() && piece instanceof Rook;
    }

    private void castleLeft(){
        // casting has to move the rook on the left size of the king from white's perspective
        if (this.isWhite)
            b.set(3, 0, b.get(0, 0));
        else
            b.set(3, b.size()-1, b.get(0, b.size()-1));
    }
}
