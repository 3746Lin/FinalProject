package view;


import model.PlayerColor;

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
public class WolfChessComponent extends Component {
    private File RedWolfFile1=new File("CS109-2023-Sping-ChessDemo/resource/RedWolf.png");
    private File BlueWolfFile1=new File("CS109-2023-Sping-ChessDemo/resource/BlueWolf.png");
    private File RedWolfFile2=new File("CS109-2023-Sping-ChessDemo/resource/灰二太太狼.png");
    private File BlueWolfFile2=new File("CS109-2023-Sping-ChessDemo/resource/懒羊羊.png");
    private BufferedImage ImageOfWolf;
    private int style=1;
    public WolfChessComponent(PlayerColor owner, int size) {
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
                    ImageOfWolf = ImageIO.read(RedWolfFile1);
                }else if (style==2){
                    ImageOfWolf = ImageIO.read(RedWolfFile2);
                }
            }else {
                if (style==1) {
                    ImageOfWolf = ImageIO.read(BlueWolfFile1);
                }else if (style==2){
                    ImageOfWolf = ImageIO.read(BlueWolfFile2);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        g2.drawImage(ImageOfWolf,0,0,getWidth(),getHeight(),null);
        if (isSelected()) { // Highlights the model if selected.
            g2.setColor(new Color(255, 255, 0, 100));
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
