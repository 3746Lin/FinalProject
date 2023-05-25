package view;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DensCellComponent extends CellComponent {
    private File DensFile=new File("CS109-2023-Sping-ChessDemo/resource/Dens.png");
    private BufferedImage ImageOfDens;
    public DensCellComponent(Color background, Point location, int size) {
        super(background, location, size);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        try {
            ImageOfDens= ImageIO.read(DensFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        g2.drawImage(ImageOfDens,0,0,getWidth(),getHeight(),null);
        if(isSelected){
            g2.setColor(new Color(255, 255, 255, 100));
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
