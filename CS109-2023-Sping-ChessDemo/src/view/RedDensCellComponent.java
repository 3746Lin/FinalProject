package view;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class RedDensCellComponent extends CellComponent {
    private File DensFile1=new File("resource/Dens.png");
    private File DensFile2=new File("resource/狼堡.png");
    private BufferedImage ImageOfDens;
    private int style=1;
    public RedDensCellComponent(Color background, Point location, int size) {
        super(background, location, size);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        try {
            if (style==1) {
                ImageOfDens = ImageIO.read(DensFile1);
            }else if (style==2){
                ImageOfDens = ImageIO.read(DensFile2);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        g2.drawImage(ImageOfDens,0,0,getWidth(),getHeight(),null);
        if(isSelected){
            g2.setColor(new Color(255, 255, 255, 100));
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
    }
    public void setStyle(int style){
        this.style=style;
    }
}
