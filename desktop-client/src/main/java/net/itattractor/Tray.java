package net.itattractor;

import net.itattractor.manager.WindowManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class Tray {
    private SystemTray tray;
    private WindowManager manager;
    private TrayIcon trayIcon;

    void init() {
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
                manager.getFrame().setVisible(true);
            }
        });

        openItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                manager.getFrame().setVisible(true);
            }
        });

        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tray.remove(trayIcon);
                System.exit(0);
            }
        });
    }

    public void setManager(WindowManager manager) {
        this.manager = manager;
    }

    public void removeTrayIcon() {
        tray.remove(trayIcon);
    }
}