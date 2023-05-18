package view;


import model.PlayerColor;
import controller.GameController;
import model.Chessboard;
import model.*;

import javax.swing.*;
import java.awt.*;

/**
 * This is the equivalent of the ChessPiece class,
 * but this class only cares how to draw Chess on ChessboardComponent
 */
public class ElephantChessComponent extends Component {
    public ElephantChessComponent(PlayerColor owner, int size) {
        this.owner = owner;
        this.selected = false;
        setSize(size/2, size/2);
        setLocation(0,0);
        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Font font = new Font("楷体", Font.PLAIN, getWidth() / 2);
        g2.setFont(font);
        g2.setColor(owner.getColor());
        g2.drawString("象", getWidth() / 4, getHeight() * 5 / 8); // FIXME: Use library to find the correct offset.
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(5, 5, getWidth() - 10, getHeight() - 10);
        }
    }
    public ImageIcon getImage(){
        if (this.owner.getColor()==Color.red){
            ImageIcon image;
            image = new ImageIcon("D:/ljz/Java/FinalProject/CS109-2023-Sping-ChessDemo/resource/Elephant-red.jpg");
            return image;
        }else {
            ImageIcon image;
            image = new ImageIcon("D:/ljz/Java/FinalProject/CS109-2023-Sping-ChessDemo/resource/Elephant-blue.jpg");
            return image;
        }
    }
}
