package net.itattractor.forms.tray;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class Tray {
    private SystemTray tray;
    private TrayIcon trayIcon;
    private TrayActionListener actionListener;

    public Tray() {
        PopupMenu popup = new PopupMenu();
        URL imageURL = ClassLoader.getSystemClassLoader().getResource("icon.png");
        Image image = Toolkit.getDefaultToolkit().getImage(imageURL);

        trayIcon = new TrayIcon(image);
        trayIcon.setImageAutoSize(true);
        tray = SystemTray.getSystemTray();

        MenuItem openItem = new MenuItem("Open");
        MenuItem exitItem = new MenuItem("Exit");

        popup.add(openItem);
        popup.addSeparator();
        popup.add(exitItem);

        trayIcon.setPopupMenu(popup);
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }

        trayIcon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(actionListener != null){
                    actionListener.doubleClicked();
                }
            }
        });

        openItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(actionListener != null){
                    actionListener.openPressed();
                }
            }
        });

        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tray.remove(trayIcon);
                if(actionListener != null){
                    actionListener.exitPressed();
                }
            }
        });
    }

    public void setActionListener(TrayActionListener actionListener) {
        this.actionListener = actionListener;
    }
}