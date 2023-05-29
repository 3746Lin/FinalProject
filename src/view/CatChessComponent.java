package view;


import model.PlayerColor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This is the equivalent of the ChessPiece class,
 * but this class only cares how to draw Chess on ChessboardComponent
 */
public class CatChessComponent extends Component {
    private File RedCatFile1=new File("resource/RedCat.png");
    private File BlueCatFile1=new File("resource/BlueCat.png");
    private File RedCatFile2=new File("resource/蕉太狼.png");
    private File BlueCatFile2=new File("resource/美羊羊.png");
    private BufferedImage ImageOfCat;
    private int style=1;
    public CatChessComponent(PlayerColor owner, int size) {
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
                    ImageOfCat = ImageIO.read(RedCatFile1);
                }else if (style==2){
                    ImageOfCat = ImageIO.read(RedCatFile2);
                }
            }else {
                if (style==1) {
                    ImageOfCat = ImageIO.read(BlueCatFile1);
                }else if (style==2){
                    ImageOfCat = ImageIO.read(BlueCatFile2);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        g2.drawImage(ImageOfCat,0,0,getWidth(),getHeight(),null);
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
