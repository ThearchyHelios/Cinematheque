package UI;

import API.API;
import API.APIInterface;
import API.utils;
import Search.SearchMovie;
import Module.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.awt.Color;

import javax.imageio.ImageIO;
import javax.naming.directory.SearchResult;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;


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
    private APIInterface apiInterface;


    DefaultListModel<lesfilms_inlist> listModel = new DefaultListModel<>();
    DefaultListModel<listfilms_search> listModel2 = new DefaultListModel<>();


    public static void mainFrame() {
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
        apiInterface = API.getAPI().create(APIInterface.class);

        listScrollPane.setViewportView(lesfilmsList);
        lesfilmsList.setModel(listModel);
        lesfilmsList.setCellRenderer(new MyListUI());
        File directory = new File("src/main/resources/film.csv");
        String absoultePath = directory.getAbsolutePath();
        List<String> list_film_in_txt = new ArrayList<String>();
        List<String> list_mode_in_txt = new ArrayList<String>();
        List<Integer> list_filmid_in_txt = new ArrayList<Integer>();
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
                list_filmid_in_txt.add(Integer.valueOf(film_string[2]));
            }
            System.out.println(list_film_in_txt);
            System.out.println(list_mode_in_txt);
            buffReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        for (int j = 0; j < list_film_in_txt.size(); j++) {
            listModel.addElement(new lesfilms_inlist(list_film_in_txt.get(j) + " ", list_mode_in_txt.get(j), list_filmid_in_txt.get(j)));
        }


        lesfilmsList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) return;
                lesfilms_inlist film = lesfilmsList.getSelectedValue();
                filmInfoTextArea.setText("Name: " + film.getNomdefilm() + "\n" + "Type: " + film.getModel()+"\n" +film.getFilmID());
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
                            if (textFieldFilmNameAddFilmToTxt.getText().contains("DVD")) {
                                JOptionPane.showMessageDialog(null, "You cannot contains DVD in film name", "ERROR", JOptionPane.PLAIN_MESSAGE);
                                return;
                            } else if (textFieldFilmNameAddFilmToTxt.getText().contains("B-ray")) {
                                JOptionPane.showMessageDialog(null, "You cannot contains B-ray in film name", "ERROR", JOptionPane.PLAIN_MESSAGE);
                                return;
                            } else if (textFieldFilmNameAddFilmToTxt.getText().contains("Digital")) {
                                JOptionPane.showMessageDialog(null, "You cannot contains Digital in film name", "ERROR", JOptionPane.PLAIN_MESSAGE);
                                return;
                            }
                            FileWriter fw = new FileWriter(absoultePath, true);
                            fw.write("\n" + textFieldFilmNameAddFilmToTxt.getText() + "," + comboBoxFilmModeAddFilmToTxt.getSelectedItem().toString() + "," + 0);
                            fw.close();

                            listModel.addElement(new lesfilms_inlist(textFieldFilmNameAddFilmToTxt.getText(), comboBoxFilmModeAddFilmToTxt.getSelectedItem().toString(), 0));
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
                listModel2.removeAllElements();
                String str = textField1.getText();
                Call<SearchMovie> searchMovieCall = apiInterface.get_movie(utils.API_KEY, str);
                searchMovieCall.enqueue(new Callback<SearchMovie>() {
                    @Override
                    public void onResponse(Call<SearchMovie> call, Response<SearchMovie> response) {
                        SearchMovie searchMovie = response.body();
                        if (response.body().getTotal_results() != 0) {
                            JFrame frame_search = new JFrame("Searching");
                            frame_search.setVisible(true);
                            frame_search.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                            frame_search.setSize(700, 500);
                            JList<listfilms_search> list_search = new JList<>();
                            list_search.setModel(listModel2);

                            JScrollPane jScrollPaneSearch = new JScrollPane(list_search);
                            jScrollPaneSearch.setSize(600, 600);

                            JTextPane jTextPaneSearch = new JTextPane();
                            jTextPaneSearch.setSize(400, 300);
                            StyledDocument styledDocumentTextpaneSearch = jTextPaneSearch.getStyledDocument();
                            JScrollPane jScrollPaneTextPaneSearch = new JScrollPane(jTextPaneSearch);

                            JButton jButtonAddToList = new JButton();
                            jButtonAddToList.setText("Add To Library");

                            JLabel jLabelPostImage = new JLabel();


                            JPanel panelSearchCenter = new JPanel();
                            JPanel panelSearchEast = new JPanel();

                            panelSearchCenter.add(jTextPaneSearch);
//                            panelSearchWest.add(jTextPaneSearch);

                            frame_search.add(jScrollPaneSearch, BorderLayout.NORTH);
                            frame_search.add(jTextPaneSearch, BorderLayout.CENTER);
//                            frame_search.add(jScrollPaneTextPaneSearch, BorderLayout.EAST);
                            frame_search.add(jLabelPostImage, BorderLayout.WEST);
                            frame_search.add(jButtonAddToList, BorderLayout.SOUTH);
//                            frame_search.add(panelSearchCenter, BorderLayout.CENTER);

                            List<search_result> search_resultArrayList = new ArrayList<>();


                            for (int j = 0; j < searchMovie.getResults().size(); j++) {
                                List<Movie.movie_detail> movie_detailList = searchMovie.getResults();
                                listModel2.addElement(new listfilms_search(movie_detailList.get(j).getTitle()));
                                search_resultArrayList.add(new search_result(movie_detailList.get(j).getTitle(), movie_detailList.get(j).getId()));

                            }
                            list_search.addListSelectionListener(new ListSelectionListener() {
                                @Override
                                public void valueChanged(ListSelectionEvent e) {
                                    if (e.getValueIsAdjusting()) return;
                                    jTextPaneSearch.setText(null);
                                    jLabelPostImage.setText(null);
                                    jLabelPostImage.setIcon(null);
                                    System.out.println(list_search.getSelectedIndex());
                                    search_result search_result = search_resultArrayList.get(list_search.getSelectedIndex());
                                    int search_result_id = search_result.iddefilm;


                                    Call<Movie.movie_detail> movie_detailCall = apiInterface.get_movie_by_id(search_result_id, utils.API_KEY);

                                    movie_detailCall.enqueue(new Callback<Movie.movie_detail>() {
                                        @Override
                                        public void onResponse(Call<Movie.movie_detail> call, Response<Movie.movie_detail> response) {
                                            Movie.movie_detail movie_detail = response.body();

                                            SimpleAttributeSet attributeSetTitle = new SimpleAttributeSet();
                                            StyleConstants.setBold(attributeSetTitle, true);
                                            StyleConstants.setFontSize(attributeSetTitle, 30);

                                            BufferedImage bufferedImageIcon = null;
                                            try {
                                                bufferedImageIcon = ImageIO.read(new URL("https://image.tmdb.org/t/p/w200" + movie_detail.getPoster_path()));
                                                ImageIcon imageIcon = new ImageIcon(bufferedImageIcon);
                                                jLabelPostImage.setIcon(imageIcon);

                                            } catch (IOException ioException) {
                                                ioException.printStackTrace();
                                                jLabelPostImage.setText("This movie have no post image");
                                            }


//                                            Edit Search Text Pane


                                            try {
                                                styledDocumentTextpaneSearch.insertString(styledDocumentTextpaneSearch.getLength(), movie_detail.getTitle(), attributeSetTitle);
                                                styledDocumentTextpaneSearch.insertString(styledDocumentTextpaneSearch.getLength(), "\nReleaased date: " + movie_detail.getRelease_date() + "\nGenres: ", null);
                                                for (int k = 0; k < movie_detail.getGenres().size(); k++) {
                                                    styledDocumentTextpaneSearch.insertString(styledDocumentTextpaneSearch.getLength(), movie_detail.getGenres().get(k).getName() + "  ", null);
                                                }
                                                styledDocumentTextpaneSearch.insertString(styledDocumentTextpaneSearch.getLength(), "\n\nAbstract:  " + movie_detail.getOverview(), null);
                                                styledDocumentTextpaneSearch.insertString(styledDocumentTextpaneSearch.getLength(), "\n\nRun time: " + movie_detail.getRuntime() + "min", null);
                                                styledDocumentTextpaneSearch.insertString(styledDocumentTextpaneSearch.getLength(), "\nMark: " + movie_detail.getVote_average() + "/10", null);
                                            } catch (BadLocationException badLocationException) {
                                                badLocationException.printStackTrace();
                                            }


                                            jButtonAddToList.addActionListener(new ActionListener() {
                                                @Override
                                                public void actionPerformed(ActionEvent e) {

                                                    JFrame frameAddFilmFromSearch = new JFrame("Add Film From Search");
                                                    frameAddFilmFromSearch.setVisible(true);
                                                    frameAddFilmFromSearch.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

                                                    JPanel jPanel1 = new JPanel();
                                                    JPanel jPanel3 = new JPanel();
                                                    JPanel jPanel2 = new JPanel();


                                                    frameAddFilmFromSearch.setSize(300, 200);

                                                    JLabel label1AddFilmToTxtFrameSearch = new JLabel("Name: ");
                                                    JTextField textFieldFilmNameAddFilmToTxtFrameSearch = new JTextField(10);
                                                    textFieldFilmNameAddFilmToTxtFrameSearch.setText(movie_detail.getTitle());
                                                    JLabel label2AddFilmToTxtFrameSearch = new JLabel("ID:   ");
                                                    JTextField textFieldFilmIdAddFilmToTxtFrameSearch = new JTextField(10);
                                                    textFieldFilmIdAddFilmToTxtFrameSearch.setText(String.valueOf(movie_detail.getId()));

                                                    jPanel1.add(label1AddFilmToTxtFrameSearch);
                                                    jPanel1.add(textFieldFilmNameAddFilmToTxtFrameSearch);
                                                    jPanel2.add(label2AddFilmToTxtFrameSearch);
                                                    jPanel2.add(textFieldFilmIdAddFilmToTxtFrameSearch);

                                                    textFieldFilmNameAddFilmToTxtFrameSearch.setEditable(false);
                                                    textFieldFilmIdAddFilmToTxtFrameSearch.setEditable(false);

                                                    JLabel label3AddFilmToTxtFrameSearch = new JLabel("Type: ");
                                                    JComboBox<String> comboBoxFilmModeAddFilmToTxtFrameSearch = new JComboBox<String>();
                                                    JButton buttonConfirmAddfilmToTextFrameSearch = new JButton("Confirm");
                                                    comboBoxFilmModeAddFilmToTxtFrameSearch.addItem("DVD");
                                                    comboBoxFilmModeAddFilmToTxtFrameSearch.addItem("B-ray");
                                                    comboBoxFilmModeAddFilmToTxtFrameSearch.addItem("Digital");

                                                    jPanel3.add(label3AddFilmToTxtFrameSearch);
                                                    jPanel3.add(comboBoxFilmModeAddFilmToTxtFrameSearch);
                                                    jPanel3.add(buttonConfirmAddfilmToTextFrameSearch);


                                                    frameAddFilmFromSearch.add(jPanel1, BorderLayout.NORTH);
                                                    frameAddFilmFromSearch.add(jPanel2, BorderLayout.CENTER);
                                                    frameAddFilmFromSearch.add(jPanel3, BorderLayout.SOUTH);

                                                    buttonConfirmAddfilmToTextFrameSearch.addActionListener(new ActionListener() {
                                                        @Override
                                                        public void actionPerformed(ActionEvent e) {


                                                            try {
                                                                if (textFieldFilmNameAddFilmToTxtFrameSearch.getText().contains("DVD")) {
                                                                    JOptionPane.showMessageDialog(null, "You cannot contains DVD in film name", "ERROR", JOptionPane.PLAIN_MESSAGE);
                                                                    return;
                                                                } else if (textFieldFilmNameAddFilmToTxtFrameSearch.getText().contains("B-ray")) {
                                                                    JOptionPane.showMessageDialog(null, "You cannot contains B-ray in film name", "ERROR", JOptionPane.PLAIN_MESSAGE);
                                                                    return;
                                                                } else if (textFieldFilmNameAddFilmToTxtFrameSearch.getText().contains("Digital")) {
                                                                    JOptionPane.showMessageDialog(null, "You cannot contains Digital in film name", "ERROR", JOptionPane.PLAIN_MESSAGE);
                                                                    return;
                                                                }
                                                                FileWriter fw = new FileWriter(absoultePath, true);
                                                                fw.write("\n" + textFieldFilmNameAddFilmToTxtFrameSearch.getText() + "," + comboBoxFilmModeAddFilmToTxtFrameSearch.getSelectedItem().toString() + "," + textFieldFilmIdAddFilmToTxtFrameSearch.getText());
                                                                fw.close();

                                                                listModel.addElement(new lesfilms_inlist(textFieldFilmNameAddFilmToTxtFrameSearch.getText(), comboBoxFilmModeAddFilmToTxtFrameSearch.getSelectedItem().toString(), Integer.valueOf(textFieldFilmIdAddFilmToTxtFrameSearch.getText())));
                                                                frameAddFilmFromSearch.setVisible(false);

                                                            } catch (IOException ioException) {
                                                                ioException.printStackTrace();
                                                            }

                                                        }
                                                    });

                                                }
                                            });


                                        }


                                        @Override
                                        public void onFailure(Call<Movie.movie_detail> call, Throwable throwable) {

                                        }
                                    });
                                }
                            });
                        }
                    }


                    @Override
                    public void onFailure(Call<SearchMovie> call, Throwable throwable) {
                        throwable.getMessage();

                    }
                });


                // TODO search之后，将左侧list改变为search结果，其实就是改变listModel的值
            }
        });
    }


    public class lesfilms_inlist {
        String nomdefilm;
        String model;
        int filmID; //This model here is to define if the movir is stocked in DVD or B-ray


        // TODO: Change model after when other disagree......
        public lesfilms_inlist(String nomdefilm, String model, int filmID) {
            this.nomdefilm = nomdefilm;
            this.model = model;
            this.filmID = filmID;
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

        public int getFilmID() {
            return filmID;
        }

        public void setFilmID(int filmID) {
            this.filmID = filmID;
        }

        @Override
        public String toString() {
            return nomdefilm + "\n" + model;
        }

    }


    public class listfilms_search {
        String nomdefilm;


        public listfilms_search(String nomdefilm) {
            this.nomdefilm = nomdefilm;

        }

        public String getNomdefilm() {
            return nomdefilm;
        }

        public void setNomdefilm(String nomdefilm) {
            this.nomdefilm = nomdefilm;
        }


        @Override
        public String toString() {
            return nomdefilm;
        }
    }


    public class search_result {
        String nomdefilm;
        int iddefilm;

        public search_result(String nomdefilm, int iddefilm) {
            this.nomdefilm = nomdefilm;
            this.iddefilm = iddefilm;
        }

        public String getNomdefilm() {
            return nomdefilm;
        }

        public void setNomdefilm(String nomdefilm) {
            this.nomdefilm = nomdefilm;
        }

        public int getIddefilm() {
            return iddefilm;
        }

        public void setIddefilm(int iddefilm) {
            this.iddefilm = iddefilm;
        }

        @Override
        public String toString() {
            return nomdefilm + '\n' + iddefilm;
        }
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

    }
}
