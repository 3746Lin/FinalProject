package view;

import model.PlayerColor;

import javax.swing.*;

public class Component extends JComponent {
    protected PlayerColor owner;

    protected boolean selected;
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
