package editor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ElementsFactory {

    public static JButton getButton(String name) {
        JButton button = new JButton();
        ImageIcon icon = new ImageIcon (Constants.iconsPaths.get(name));
        button.setMargin(new Insets(0,0,0,0));
        button.setName(name);
        button.setIcon(icon);
        return button;
    }

    public static JButton getButton(String name, ActionListener actionListener) {
        JButton button = getButton(name);
        button.addActionListener(actionListener);
        return button;
    }

    public static JMenuItem getMenuItem(String name, ActionListener actionListener ) {
        JMenuItem menuItem = new JMenuItem(Constants.menuTitles.get(name));
        menuItem.setName(name);
        menuItem.addActionListener(actionListener);
        return menuItem;
    }

    public static JMenu getMenu(String name, int key) {
        JMenu menu = new JMenu(Constants.menuTitles.get(name));
        menu.setName(name);
        menu.setMnemonic(key);
        return menu;
    }
}