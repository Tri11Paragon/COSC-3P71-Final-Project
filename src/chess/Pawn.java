package chess;

import ui.Display;

import java.awt.*;
import java.util.ArrayList;

public class Pawn extends ChessPiece {

    private Image white = Display.loadImage("./resources/chess_piece_2_white_pawn.png");
    private Image black = Display.loadImage("./resources/chess_piece_2_black_pawn.png");

    public Pawn(Board b, boolean isWhite, int x, int y) {
        super(b,isWhite,x,y);
    }

    public Image getImage(){
        if (isWhite)
            return white;
        return black;
    }

    @Override
    public ArrayList<Move> getMoves() {
        ArrayList<Move> moves = new ArrayList<Move>();
        if (isWhite) {
            moves.add(b.checkIfMoveValid(pawnForwardMoveValid(new Move(x, y + 1)), isWhite));
            moves.add(pawnAttack(new Move(x + 1, y + 1)));
            moves.add(pawnAttack(new Move(x - 1, y + 1)));
            if (isFirstMove())
                moves.add(b.checkIfMoveValid(new Move(x, y + 2), isWhite));
        } else {
            moves.add(b.checkIfMoveValid(pawnForwardMoveValid(new Move(x, y - 1)), isWhite));
            moves.add(pawnAttack(new Move(x + 1, y - 1)));
            moves.add(pawnAttack(new Move(x - 1, y - 1)));
            if (isFirstMove())
                moves.add(b.checkIfMoveValid(new Move(x, y - 2), isWhite));
        }
        ChessPiece neighbour = null;

        if (isWhite){
            // En passant
            if ((neighbour = b.get(x-1, y)) != null && checkNeighbourEnPassant(neighbour))
                moves.add(b.checkIfMoveValid(pawnForwardMoveValid(new Move(x-1, y + 1, Move.SpecialConditions.leftEnPassant)), isWhite));
            // En passant
            if ((neighbour = b.get(x+1, y)) != null && checkNeighbourEnPassant(neighbour))
                moves.add(b.checkIfMoveValid(pawnForwardMoveValid(new Move(x+1, + 1, Move.SpecialConditions.rightEnPassant)), isWhite));
        } else {
            // unfortunately have to flip the direction depending on player type
            // En passant
            if ((neighbour = b.get(x-1, y)) != null && checkNeighbourEnPassant(neighbour))
                moves.add(b.checkIfMoveValid(pawnForwardMoveValid(new Move(x-1, y - 1, Move.SpecialConditions.leftEnPassant)), isWhite));
            // En passant
            if ((neighbour = b.get(x+1, y)) != null && checkNeighbourEnPassant(neighbour))
                moves.add(b.checkIfMoveValid(pawnForwardMoveValid(new Move(x+1, - 1, Move.SpecialConditions.rightEnPassant)), isWhite));
        }

        return prune(moves);
    }

    private Move pawnAttack(Move m){
        if (b.get(m) != null && b.get(m).isWhite != isWhite)
            return m;
        return null;
    }

    private Move pawnForwardMoveValid(Move m){
        // basically prevent a pawn from moving into the enemy head on.
        if (b.get(m) != null)
            return null;
        return m;
    }

    private boolean checkNeighbourEnPassant(ChessPiece neighbour){
        return neighbour instanceof Pawn && neighbour.isSecondMove() && neighbour.isWhite != this.isWhite;
    }

    @Override
    public void applySpecialMove(Move moveWithSpecial){
        var specialCondition = moveWithSpecial.getSpecialCondition();
        if(specialCondition == Move.SpecialConditions.leftEnPassant)
            enPassantLeft();
        else if (specialCondition == Move.SpecialConditions.rightEnPassant)
            enPassantRight();
    }

    private void enPassantLeft(){
        if (isWhite)
            b.set(x, y-1, null);
        else
            b.set(x, y+1, null);
    }

    private void enPassantRight(){
        if (isWhite)
            System.out.println(b.set(x, y-1, null));
        else
            b.set(x, y-1, null);
    }
}
