package view;

import model.PlayerColor;

import javax.swing.*;

public class Component extends JComponent {
    protected PlayerColor owner;

    protected boolean selected;
    protected boolean canBeSelected;
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
}
