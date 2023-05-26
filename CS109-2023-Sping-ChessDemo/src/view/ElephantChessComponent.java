package view;


import model.PlayerColor;
import controller.GameController;
import model.Chessboard;
import model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This is the equivalent of the ChessPiece class,
 * but this class only cares how to draw Chess on ChessboardComponent
 */
public class ElephantChessComponent extends Component {
    private File RedElephantFile1=new File("CS109-2023-Sping-ChessDemo/resource/RedElephant.png");
    private File BlueElephantFile1=new File("CS109-2023-Sping-ChessDemo/resource/BlueElephant.png");
    private File RedElephantFile2=new File("CS109-2023-Sping-ChessDemo/resource/黑大帅.jpg");
    private File BlueElephantFile2=new File("CS109-2023-Sping-ChessDemo/resource/包包大人.png");
    private BufferedImage ImageOfElephant;
    private int style=1;
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
        try {
            if (this.owner.getColor()==PlayerColor.RED.getColor()) {
                if (style==1) {
                    ImageOfElephant = ImageIO.read(RedElephantFile1);
                }else if (style==2){
                    ImageOfElephant = ImageIO.read(RedElephantFile2);
                }
            }else {
                if (style==1) {
                    ImageOfElephant = ImageIO.read(BlueElephantFile1);
                }else if (style==2){
                    ImageOfElephant = ImageIO.read(BlueElephantFile2);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        g2.drawImage(ImageOfElephant,0,0,getWidth(),getHeight(),null);
        if (isSelected()) { // Highlights the model if selected.
            g2.setColor(new Color(255, 255, 0, 128));
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
        if (isCanBeSelected()) { // Highlights the model if selected.
            g2.setColor(new Color(0, 255, 0, 128));
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
    }
    public void setStyle(int style){
        this.style=style;
    }
}
