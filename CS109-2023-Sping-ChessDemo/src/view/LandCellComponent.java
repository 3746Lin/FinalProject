package view;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LandCellComponent extends CellComponent {
    private File LandFile=new File("CS109-2023-Sping-ChessDemo/resource/Land.png");
    private BufferedImage ImageOfLand;
    public LandCellComponent(Color background, Point location, int size) {
        super(background, location, size);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        try {
            ImageOfLand= ImageIO.read(LandFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        g2.drawImage(ImageOfLand,0,0,getWidth(),getHeight(),null);
        if(isSelected){
            g2.setColor(new Color(255, 255, 255, 100));
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
