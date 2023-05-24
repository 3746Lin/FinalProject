package view;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TrapCellComponent extends CellComponent {
    private File TrapFile=new File("CS109-2023-Sping-ChessDemo/resource/Trap.png");
    private BufferedImage ImageOfTrap;
    private boolean hasWorked;
    public TrapCellComponent(Color background, Point location, int size) {
        super(background, location, size);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        try {
            ImageOfTrap= ImageIO.read(TrapFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        g2.drawImage(ImageOfTrap,0,0,getWidth(),getHeight(),null);
        if(isSelected){
            g2.setColor(new Color(255, 255, 255, 100));
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
        if(hasWorked){
            g2.setColor(new Color(0, 255, 0, 100));
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
