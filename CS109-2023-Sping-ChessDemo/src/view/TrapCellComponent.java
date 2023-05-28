package view;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TrapCellComponent extends CellComponent {
    private File TrapFile1=new File("resource/Trap.png");
    private File TrapFile2=new File("resource/陷阱.png");
    private BufferedImage ImageOfTrap;
    private int style=1;
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
            if (style==1) {
                ImageOfTrap = ImageIO.read(TrapFile1);
            }else if (style==2){
                ImageOfTrap = ImageIO.read(TrapFile2);
            }
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
    public void setStyle(int style){
        this.style=style;
    }
}
