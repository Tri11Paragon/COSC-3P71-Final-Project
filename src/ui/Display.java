package ui;

import chess.Board;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.AttributeSet;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.AttributedString;

public class Display extends JFrame {

    private Board b;

    public Display(Board b){
        this.b = b;
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(800, 800);
        this.setVisible(true);
        this.setEnabled(true);
        this.setTitle("Ratatta");
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int i = 0; i < b.size(); i++){
            for (int j = 0; j < b.size(); j++){
                if (b.get(i,j) != null)
                    g.drawImage(b.get(i,j).getImage(), (i+2) * 64, (j+2) * 64, null);
            }
        }
    }

    public boolean update(){
        return true;
    }

    public static Image loadImage(String path){
        try {
            return new ImageIcon(ImageIO.read(new File(path))).getImage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
