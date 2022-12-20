package project.chess;

import java.awt.*;
import java.util.ArrayList;

import static project.ui.Display.loadImage;

public class Queen extends ChessPiece {

    private Image whiteQueen = loadImage("./resources/chess_piece_2_black_queen.png");
    private Image blackQueen = loadImage("./resources/chess_piece_2_white_queen.png");

    public Queen(Board b, boolean isWhite, int x, int y) {
        super(b,isWhite,x,y);
    }

    public Image getImage(){
        if (isWhite)
            return whiteQueen;
        return blackQueen;
    }

    @Override
    public ArrayList<Move> getMoves() {
        ArrayList<Move> moves = new ArrayList<Move>();
        moves.addAll(super.getCardinalMoves(b.size()));
        moves.addAll(super.getDiagonalMoves(b.size()));
        return prune(moves);
    }

    public Queen clone () {
        return new Queen(this.b,this.isWhite,this.x,this.y);
    }
}
