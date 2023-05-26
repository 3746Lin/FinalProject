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
public class LeopardChessComponent extends Component {
    private File RedLeopardFile1=new File("CS109-2023-Sping-ChessDemo/resource/RedLeopard.png");
    private File BlueLeopardFile1=new File("CS109-2023-Sping-ChessDemo/resource/BlueLeopard.png");
    private File RedLeopardFile2=new File("CS109-2023-Sping-ChessDemo/resource/夜太狼.png");
    private File BlueLeopardFile2=new File("CS109-2023-Sping-ChessDemo/resource/慢羊羊.png");
    private BufferedImage ImageOfLeopard;
    private int style=1;
    public LeopardChessComponent(PlayerColor owner, int size) {
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
                    ImageOfLeopard = ImageIO.read(RedLeopardFile1);
                }else if (style==2){
                    ImageOfLeopard = ImageIO.read(RedLeopardFile2);
                }
            }else {
                if (style==1) {
                    ImageOfLeopard = ImageIO.read(BlueLeopardFile1);
                }else if (style==2){
                    ImageOfLeopard = ImageIO.read(BlueLeopardFile2);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        g2.drawImage(ImageOfLeopard,0,0,getWidth(),getHeight(),null);
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