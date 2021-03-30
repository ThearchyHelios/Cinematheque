package ui;

import javax.swing.*;
import java.awt.*;
import java.io.File;


public class MyListUI extends DefaultListCellRenderer {
    public Component getListCellRendererComponent(JList<? extends Object> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        setText(value.toString());
        File DVD_png = new File("src/main/resources/DVD.png");
        File Bray_png = new File("src/main/resources/Bray.png");
        File Digital_png = new File("src/main/resources/Digital.png");
        if (value.toString().contains("DVD")) {
            String absolutePath_DVD = DVD_png.getAbsolutePath();
            ImageIcon ico = new ImageIcon(absolutePath_DVD);
            Image img = ico.getImage();
            img = img.getScaledInstance(50, 25, Image.SCALE_DEFAULT);
            ico.setImage(img);
            setIcon(ico);

        } else if (value.toString().contains("B-ray")) {
            String absolutePath_Bery = Bray_png.getAbsolutePath();
            ImageIcon ico = new ImageIcon(absolutePath_Bery);
            Image img = ico.getImage();
            img = img.getScaledInstance(50, 25, Image.SCALE_DEFAULT);
            ico.setImage(img);
            setIcon(ico);

        } else if (value.toString().contains("Digital")) {
            String absolutePath_Digital = Digital_png.getAbsolutePath();
            ImageIcon ico = new ImageIcon(absolutePath_Digital);
            Image img = ico.getImage();
            img = img.getScaledInstance(50, 25, Image.SCALE_DEFAULT);
            ico.setImage(img);
            setIcon(ico);


        }
        if (isSelected) {
            setForeground(Color.WHITE);
            setBackground(Color.BLUE);
            System.out.println("No." + index + " has been selected");
        } else {
            setForeground(Color.BLACK);
            setBackground(Color.WHITE);
        }
        value = value.toString().replace("DVD", "");
        value = value.toString().replace("B-ray", "");
        value = value.toString().replace("Digital", "");
        return this;
    }

}
