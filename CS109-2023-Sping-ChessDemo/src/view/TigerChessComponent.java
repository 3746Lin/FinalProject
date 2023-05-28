package view;


import model.PlayerColor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This is the equivalent of the ChessPiece class,
 * but this class only cares how to draw Chess on ChessboardComponent
 */
public class TigerChessComponent extends Component {
    private File RedTigerFile1=new File("CS109-2023-Sping-ChessDemo/resource/RedTiger.png");
    private File BlueTigerFile1=new File("CS109-2023-Sping-ChessDemo/resource/BlueTiger.png");
    private File RedTigerFile2=new File("CS109-2023-Sping-ChessDemo/resource/红太狼.png");
    private File BlueTigerFile2=new File("CS109-2023-Sping-ChessDemo/resource/沸羊羊.png");
    private BufferedImage ImageOfTiger;
    private int style=1;
    public TigerChessComponent(PlayerColor owner, int size) {
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
                    ImageOfTiger = ImageIO.read(RedTigerFile1);
                }else if (style==2){
                    ImageOfTiger = ImageIO.read(RedTigerFile2);
                }
            }else {
                if (style==1) {
                    ImageOfTiger = ImageIO.read(BlueTigerFile1);
                }else if (style==2){
                    ImageOfTiger = ImageIO.read(BlueTigerFile2);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        g2.drawImage(ImageOfTiger,0,0,getWidth(),getHeight(),null);
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
