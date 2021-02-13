import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


// Yilun JIANG API Key: 89e5521b3e8381cf6adc8f4c8432e07d
// Author: JIANG WANG KANG

public class IHMMain extends JFrame {
    private JPanel main_interface;
    private JButton searchButton;
    private JTextField textField1;
    private JButton sortButton;
    private JButton addFilmButton;
    private JLabel lesFilmsLabel;
    private JList<lesfilms_inlist> lesfilmsList;
    private JTextArea filmInfoTextArea;
    private JLabel nameOfFilmLabel;
    private JScrollPane listScrollPane;

    DefaultListModel<lesfilms_inlist> listModel = new DefaultListModel<>();


    static void mainFrame() {
        JFrame frame = new JFrame("IHMMain");
        frame.setContentPane(new IHMMain().main_interface);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


    }

    public static void main(String[] args) {
        mainFrame();
    }


    public IHMMain() {
//        Tmdb tmdb = new Tmdb("89e5521b3e8381cf6adc8f4c8432e07d"); /* This is TMDB API Key */


        listScrollPane.setViewportView(lesfilmsList);
        lesfilmsList.setModel(listModel);
        lesfilmsList.setCellRenderer(new MyListUI());
        File directory = new File("src/main/resources/film.txt");
        String absoultePath = directory.getAbsolutePath();
        List<String> list_film_in_txt = new ArrayList<String>();
        List<String> list_mode_in_txt = new ArrayList<String>();
        String line = "";
        try {
            FileInputStream fin = new FileInputStream(absoultePath);
            InputStreamReader reader = new InputStreamReader(fin);
            BufferedReader buffReader = new BufferedReader(reader);
            StringBuffer stringBuffer = new StringBuffer();
            while ((line = buffReader.readLine()) != null) {
                System.out.println(line);
                String[] film_string = line.split(",");
                list_film_in_txt.add(film_string[0]);
                list_mode_in_txt.add(film_string[1]);
            }
            System.out.println(list_film_in_txt);
            System.out.println(list_mode_in_txt);
            buffReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        for (int j = 0; j < list_film_in_txt.size(); j++) {
            listModel.addElement(new lesfilms_inlist(list_film_in_txt.get(j) + " ",list_mode_in_txt.get(j)));
        }


        lesfilmsList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                lesfilms_inlist film = lesfilmsList.getSelectedValue();
                filmInfoTextArea.setText("Name: " + film.getNomdefilm() + "\n" + "Type: " + film.getModel());
                nameOfFilmLabel.setText(film.getNomdefilm());
            }
        });


        addFilmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame frameAddFilmToTxt = new JFrame("Add films");
                frameAddFilmToTxt.setVisible(true);
                frameAddFilmToTxt.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

                JPanel jPanel1 = new JPanel();
                JPanel jPanel2 = new JPanel();


                frameAddFilmToTxt.setSize(300, 100);

                JLabel label1AddFilmToTxt = new JLabel("Name: ");
                JTextField textFieldFilmNameAddFilmToTxt = new JTextField(10);
                jPanel1.add(label1AddFilmToTxt);
                jPanel1.add(textFieldFilmNameAddFilmToTxt);

                JLabel label2AddFilmToTxt = new JLabel("Type: ");
                JComboBox<String> comboBoxFilmModeAddFilmToTxt = new JComboBox<String>();
                JButton buttonConfirmAddfilmToText = new JButton("Confirm");
                comboBoxFilmModeAddFilmToTxt.addItem("DVD");
                comboBoxFilmModeAddFilmToTxt.addItem("B-ray");
                comboBoxFilmModeAddFilmToTxt.addItem("Digital");
                jPanel2.add(label2AddFilmToTxt);
                jPanel2.add(comboBoxFilmModeAddFilmToTxt);
                jPanel2.add(buttonConfirmAddfilmToText);


                frameAddFilmToTxt.add(jPanel1, BorderLayout.NORTH);

                frameAddFilmToTxt.add(jPanel2, BorderLayout.CENTER);


                buttonConfirmAddfilmToText.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            if (textFieldFilmNameAddFilmToTxt.getText().contains("DVD")){
                                JOptionPane.showMessageDialog(null, "You cannot contains DVD in film name", "ERROR", JOptionPane.PLAIN_MESSAGE);
                                return;
                            }
                            else if (textFieldFilmNameAddFilmToTxt.getText().contains("B-ray")){
                                JOptionPane.showMessageDialog(null, "You cannot contains B-ray in film name", "ERROR", JOptionPane.PLAIN_MESSAGE);
                                return;
                            }
                            else if (textFieldFilmNameAddFilmToTxt.getText().contains("Digital")){
                                JOptionPane.showMessageDialog(null, "You cannot contains Digital in film name", "ERROR", JOptionPane.PLAIN_MESSAGE);
                                return;
                            }
                            FileWriter fw = new FileWriter(absoultePath, true);
                            fw.write("\n" + textFieldFilmNameAddFilmToTxt.getText() + "," + comboBoxFilmModeAddFilmToTxt.getSelectedItem().toString());
                            fw.close();

                            listModel.addElement(new lesfilms_inlist(textFieldFilmNameAddFilmToTxt.getText(), comboBoxFilmModeAddFilmToTxt.getSelectedItem().toString()));
                            frameAddFilmToTxt.setVisible(false);

                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                });

            }
        });


        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String theSearcher1 = textField1.getText().toString();
            }
        });
    }


    public class lesfilms_inlist {
        String nomdefilm;
        String model; //This model here is to define if the movir is stocked in DVD or B-ray

        // TODO: Change model after when other disagree......
        public lesfilms_inlist(String nomdefilm, String model) {
            this.nomdefilm = nomdefilm;
            this.model = model;
        }

        public String getNomdefilm() {
            return nomdefilm;
        }

        public void setNomdefilm(String nomdefilm) {
            this.nomdefilm = nomdefilm;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        @Override
        public String toString() {
            return nomdefilm + "\n" + model;
        }

    }


    public class lesfilms_richdetails{
        String nomdefilm;
        String model;
        String genre;
        String directeur;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

    }
}
