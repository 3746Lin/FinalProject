package view;


import model.PlayerColor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This is the equivalent of the ChessPiece class,
 * but this class only cares how to draw Chess on ChessboardComponent
 */
public class LionChessComponent extends Component {
    private File RedLionFile1=new File("CS109-2023-Sping-ChessDemo/resource/RedLion.png");
    private File BlueLionFile1=new File("CS109-2023-Sping-ChessDemo/resource/BlueLion.png");
    private File RedLionFile2=new File("CS109-2023-Sping-ChessDemo/resource/灰太狼.png");
    private File BlueLionFile2=new File("CS109-2023-Sping-ChessDemo/resource/喜羊羊.png");
    private BufferedImage ImageOfLion;
    private int style=1;
    public LionChessComponent(PlayerColor owner, int size) {
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
                    ImageOfLion = ImageIO.read(RedLionFile1);
                }else if (style==2){
                    ImageOfLion = ImageIO.read(RedLionFile2);
                }
            }else {
                if (style==1) {
                    ImageOfLion = ImageIO.read(BlueLionFile1);
                }else if (style==2){
                    ImageOfLion = ImageIO.read(BlueLionFile2);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        g2.drawImage(ImageOfLion,0,0,getWidth(),getHeight(),null);
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
