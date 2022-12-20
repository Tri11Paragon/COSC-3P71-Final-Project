package chess;

import java.awt.*;
import java.util.ArrayList;

import static ui.Display.loadImage;

public class Rook extends ChessPiece {

    private Image whiteRook = loadImage("./resources/chess_piece_2_white_rook.png");
    private Image blackRook = loadImage("./resources/chess_piece_2_black_rook.png");

    public Rook(Board b, boolean isWhite, int x, int y) {
        super(b,isWhite,x,y);
    }

    public Image getImage(){
        return isWhite ? whiteRook : blackRook;
    }

    @Override
    public ArrayList<Move> getMoves() {
        return prune(super.getCardinalMoves(b.size()));
    }
}
