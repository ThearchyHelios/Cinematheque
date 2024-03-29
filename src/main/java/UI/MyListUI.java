package UI;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MyListUI extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<? extends Object> list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        // Get the text information of each element and display it.
        setText(value.toString());

        // Instantiate three PNG objects.
        File DVD_png = new File("src/main/resources/DVD.png");
        File Bray_png = new File("src/main/resources/Bray.png");
        File Digital_png = new File("src/main/resources/Digital.png");

        // Determine the model from value.

        String model_string_spilt[] = value.toString().split("\n", -1);
        String model = model_string_spilt[1];

        if (model.contains("DVD")) {

            // Considering the differences in path expression in operating systems, we use the method getAbsolutePath.
            String absolutePath_DVD = DVD_png.getAbsolutePath();

            // Instantiate an ImageIcon object.
            ImageIcon ico = new ImageIcon(absolutePath_DVD);

            // Instantiate an Image object to get the contents of the ico object.
            Image img = ico.getImage();

            // Scale Image to 50, 25
            img = img.getScaledInstance(50, 25, Image.SCALE_DEFAULT);

            // ImageIcon object regains the scaled icon
            ico.setImage(img);

            // Set Image
            setIcon(ico);

        } else if (model.contains("B-ray")) {
            String absolutePath_Bray = Bray_png.getAbsolutePath();
            ImageIcon ico = new ImageIcon(absolutePath_Bray);
            Image img = ico.getImage();
            img = img.getScaledInstance(50, 25, Image.SCALE_DEFAULT);
            ico.setImage(img);
            setIcon(ico);

        } else if (model.contains("Digital")) {
            String absolutePath_Digital = Digital_png.getAbsolutePath();
            ImageIcon ico = new ImageIcon(absolutePath_Digital);
            Image img = ico.getImage();
            img = img.getScaledInstance(50, 25, Image.SCALE_DEFAULT);
            ico.setImage(img);
            setIcon(ico);


        }

        // Set when is been selected.
        if (isSelected) {

            // Set the text color to white.
            setForeground(Color.WHITE);

            // Set the background color to blue
            setBackground(Color.BLUE);

            // Set when is not been selected.
        } else {

            // Set the text color to black.
            setForeground(Color.BLACK);

            // Set the background color to white.
            setBackground(Color.WHITE);
        }

        // Init Text of object Value
        value = value.toString().replace("DVD", "");
        value = value.toString().replace("B-ray", "");
        value = value.toString().replace("Digital", "");

        return this;
    }

}
