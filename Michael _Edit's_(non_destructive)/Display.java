package project.ui;

import project.chess.Board;
import project.chess.ChessPiece;
import project.chess.Move;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.AttributeSet;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.text.AttributedString;
import java.util.ArrayList;

public class Display extends JFrame implements MouseListener {

    private static int xOffset = 128, yOffset = 128;
    private static int width = 64, height = 64;
    private int movingPointX = -1, movingPointY = -1;
    private int selectionPointX = -1, selectionPointY = -1;

    private Board b;

    public Display(Board b){
        this.b = b;
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(800, 800);
        this.setVisible(true);
        this.setEnabled(true);
        this.setTitle("Ratatta");
        this.addMouseListener(this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int i = 0; i < b.size(); i++){
            for (int j = 0; j < b.size(); j++){
                ChessPiece p;
                p = b.get(i,j);
                if (p != null)
                    g.drawImage(p.getImage(), xOffset + (i) * width, yOffset + (j) * height, null);
            }
        }
        if (movingPointX != -1 && movingPointY != -1) {
            ChessPiece piece;
            piece = b.get(movingPointX, movingPointY);
            if (piece != null) {
                ArrayList<Move> moves;
                moves = piece.getMoves();
                for (Move m : moves) {
                    drawSelectionRect(g, m);
                }
                if (selectionPointX != -1 && selectionPointY != -1) {
                    int localPointX = (selectionPointX - xOffset) / width;
                    int localPointY = (b.size() - 1) - ((selectionPointY - yOffset) / height);
                    for (Move m : moves){
                        if (m.getX() == localPointX && m.getY() == localPointY){
                            b.movePiece(piece.getPosition(), new Move(localPointX, localPointY));
                            selectionPointX = -1;
                            selectionPointY = -1;
                            movingPointX = -1;
                            movingPointY = -1;
                            return;
                        }
                    }
                }
            }
        }
        // handle user input (mouse clicking)
        if (selectionPointX != -1 && selectionPointY != -1){
            int localPointX = (selectionPointX - xOffset) / width;
            int localPointY = (b.size()-1) - ((selectionPointY - yOffset) / height);
            ChessPiece piece;
            piece = b.get(localPointX, localPointY);
            if (piece != null) {
                movingPointX = localPointX;
                movingPointY = localPointY;
            }
            selectionPointY = -1;
            selectionPointX = -1;
        }
    }

    private void drawSelectionRect(Graphics g, Move m){
        g.drawRect(xOffset + (m.getX() * width), yOffset + ((b.size()-1 - m.getY()) * height), width, height);
    }

    public boolean update(Board newerBoard){
        this.b = newerBoard;
        return true;
    }

    public static Image loadImage(String path){
        try {
            return new ImageIcon(ImageIO.read(new File(path))).getImage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        this.selectionPointX = mouseEvent.getX();
        this.selectionPointY = mouseEvent.getY();
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}