package view;

import javax.swing.*;
import java.awt.*;

/**
 * This is the equivalent of the Cell class,
 * but this class only cares how to draw Cells on ChessboardComponent
 */

public class CellComponent extends JPanel {
    protected Color background;
    protected boolean isSelected;
    private int style=1;

    public CellComponent(Color background, Point location, int size) {
        setLayout(new GridLayout(1,1));
        setLocation(location);
        setSize(size, size);
        this.background = background;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
        g.setColor(background);
        g.fillRect(1, 1, this.getWidth()-1, this.getHeight()-1);
        if(isSelected){
            g.setColor(new Color(255, 255, 255, 128));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
    public void setSelected(boolean b){
        this.isSelected=b;
    }
    public void setStyle(int style){
        this.style=style;
    }
}
