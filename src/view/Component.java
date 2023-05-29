package view;

import model.PlayerColor;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Component extends JComponent {
    protected PlayerColor owner;

    protected boolean selected;
    protected boolean canBeSelected;
    private boolean isMouseOnComponent=false;
    private int style=1;
    public boolean isSelected() {
        return selected;
    }
    public boolean isCanBeSelected() {
        return canBeSelected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    public void setCanBeSelected(boolean canBeSelected) {
        this.canBeSelected = canBeSelected;
    }
    public void setStyle(int style){
        this.style=style;
    }
    public boolean getMouseOnComponent(){
        return isMouseOnComponent;
    }
    public void setMouseOnComponent(boolean b){
        this.isMouseOnComponent=true;
    }
}
