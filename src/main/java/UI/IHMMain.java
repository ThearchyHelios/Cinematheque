package UI;

import API.API;
import API.APIInterface;
import API.utils;
import Search.SearchMovie;
import Module.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.imageio.ImageIO;
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
import java.util.*;
import java.util.List;


// Yilun JIANG API Key: 89e5521b3e8381cf6adc8f4c8432e07d
// Author: JIANG WANG KANG

public class IHMMain extends JFrame {
    private JPanel main_interface;
    private JButton buttonSearch;
    private JTextField textField1;
    private JButton buttonAddFilm;
    private JLabel labelLesFilms;
    private JList<listmodel_addelement> lesfilmsList;
    private JTextArea textAreafilmInfo;
    private JLabel labelNomDeFilm;
    private JScrollPane scrollPaneListDeFilm;
    private JTextPane textPaneMovieDetail;
    private JScrollPane scrollPaneTextPane;
    private JLabel labelFilmImage;
    private JComboBox comboBoxSort;
    private APIInterface apiInterface;


    DefaultListModel<listmodel_addelement> listModel = new DefaultListModel<>();
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
        apiInterface = API.getAPI().create(APIInterface.class); // Create template of URL with API Key

        scrollPaneListDeFilm.setViewportView(lesfilmsList);
        scrollPaneTextPane.setViewportView(textPaneMovieDetail);
        lesfilmsList.setModel(listModel);
        lesfilmsList.setCellRenderer(new MyListUI());
        StyledDocument styledDocumentTextPane = textPaneMovieDetail.getStyledDocument();

        File directory = new File("src/main/resources/film.csv");
        String absoultePath = directory.getAbsolutePath();
        List<String> list_film_in_txt = new ArrayList<String>();
        List<String> list_mode_in_txt = new ArrayList<String>();
        List<Integer> list_filmid_in_txt = new ArrayList<Integer>();
        List<String> list_year_in_txt = new ArrayList<String>();

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
                list_year_in_txt.add(film_string[3]);
            }
            System.out.println(list_film_in_txt);
            System.out.println(list_mode_in_txt);
            buffReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<lesfilms_inlist> lesfilms_inlist_list = new ArrayList<>();

        for (int j = 0; j < list_film_in_txt.size(); j++) {
            listModel.addElement(new listmodel_addelement(list_film_in_txt.get(j) + " ", list_mode_in_txt.get(j)));
            lesfilms_inlist_list.add(new lesfilms_inlist(list_film_in_txt.get(j), list_mode_in_txt.get(j), list_filmid_in_txt.get(j), list_year_in_txt.get(j)));
        }


        comboBoxSort.addItem("Sort");
        comboBoxSort.addItem("Name");
        comboBoxSort.addItem("Model");
        comboBoxSort.addItem("Year");

        comboBoxSort.addActionListener(new ActionListener() { //Add Sort Combo Box Action listener
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboBoxSort.getSelectedItem() == "Sort") {
                    File directory = new File("src/main/resources/film.csv");
                    String absoultePath = directory.getAbsolutePath();
                    List<String> list_film_in_txt = new ArrayList<String>();
                    List<String> list_mode_in_txt = new ArrayList<String>();
                    List<Integer> list_filmid_in_txt = new ArrayList<Integer>();
                    List<String> list_year_in_txt = new ArrayList<String>();

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
                            list_year_in_txt.add(film_string[3]);
                        }
                        System.out.println(list_film_in_txt);
                        System.out.println(list_mode_in_txt);
                        buffReader.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    for (int j = 0; j < list_film_in_txt.size(); j++) {
                        listModel.set(j, new listmodel_addelement(list_film_in_txt.get(j) + " ", list_mode_in_txt.get(j)));
                        lesfilms_inlist_list.set(j, new lesfilms_inlist(list_film_in_txt.get(j), list_mode_in_txt.get(j), list_filmid_in_txt.get(j), list_year_in_txt.get(j)));
                    }
                } else if (comboBoxSort.getSelectedItem() == "Model") {
                    Collections.sort(lesfilms_inlist_list, FilmModelComparator);
                    for (int i=0; i<lesfilms_inlist_list.size(); i++){
                        listModel.set(i, new listmodel_addelement(lesfilms_inlist_list.get(i).getNomdefilm(), lesfilms_inlist_list.get(i).getModel()));
                    }
                    lesfilmsList.setModel(listModel);
                } else if(comboBoxSort.getSelectedItem() == "Name"){
//                    List<lesfilms_inlist> lesfilms_inlists_sortedname = new ArrayList<>(lesfilms_inlist_list);
//                    DefaultListModel<listmodel_addelement> listModelSortName = new DefaultListModel<>();
//                    Collections.sort(lesfilms_inlists_sortedname, FilmNameComparator);
//                    for (int i=0;i<lesfilms_inlists_sortedname.size();i++){
//                        listModelSortName.addElement(new listmodel_addelement(lesfilms_inlists_sortedname.get(i).getNomdefilm(), lesfilms_inlists_sortedname.get(i).getModel()));
//                    }
//                    lesfilmsList.setModel(listModelSortName);

                    Collections.sort(lesfilms_inlist_list, FilmNameComparator);
                    for (int i=0;i<lesfilms_inlist_list.size();i++){
                        listModel.set(i, new listmodel_addelement(lesfilms_inlist_list.get(i).getNomdefilm(), lesfilms_inlist_list.get(i).getModel()));
                    }
                    lesfilmsList.setModel(listModel);

                    return;
                } else if (comboBoxSort.getSelectedItem() == "Year"){
                    Collections.sort(lesfilms_inlist_list, FilmYearComparator);
                    for (int i=0; i<lesfilms_inlist_list.size(); i++){
                        listModel.set(i, new listmodel_addelement(lesfilms_inlist_list.get(i).getNomdefilm(), lesfilms_inlist_list.get(i).getModel()));
                    }
                    lesfilmsList.setModel(listModel);
                }

            }
        });


        lesfilmsList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // TODO Frame
                if (e.getValueIsAdjusting()) return;
                labelFilmImage.setText(null);
                labelFilmImage.setIcon(null);
                textPaneMovieDetail.setText(null);

                System.out.println(lesfilmsList.getSelectedIndex());
                lesfilms_inlist lesfilms_inlist_selected = lesfilms_inlist_list.get(lesfilmsList.getSelectedIndex());
                int film_id = lesfilms_inlist_selected.getFilmID();

                Call<Movie.movie_detail> movie_detailCall = apiInterface.get_movie_by_id(film_id, utils.API_KEY);

                movie_detailCall.enqueue(new Callback<Movie.movie_detail>() {
                    @Override
                    public void onResponse(Call<Movie.movie_detail> call, Response<Movie.movie_detail> response) {
                        Movie.movie_detail movie_detail = response.body();
                        SimpleAttributeSet attributeSetTitle = new SimpleAttributeSet();
                        StyleConstants.setBold(attributeSetTitle, true);
                        StyleConstants.setFontSize(attributeSetTitle, 30);

                        BufferedImage bufferedImageIcon = null;

                        try {
                            bufferedImageIcon = ImageIO.read(new URL("https://image.tmdb.org/t/p/w500" + movie_detail.getPoster_path()));
                            ImageIcon imageIcon = new ImageIcon(bufferedImageIcon);
                            labelFilmImage.setIcon(imageIcon);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                            labelFilmImage.setText("NO IMAGE ");
                        } catch (NullPointerException nullPointerException) {
                            nullPointerException.printStackTrace();
                            labelFilmImage.setText("This movie has been added by yourself");
                        }


                        //                    Edit Text Pane
                        try {
                            styledDocumentTextPane.insertString(styledDocumentTextPane.getLength(), movie_detail.getTitle(), attributeSetTitle);
                            styledDocumentTextPane.insertString(styledDocumentTextPane.getLength(), "\nReleaased date: " + movie_detail.getRelease_date() + "\nGenre: ", null);
                            for (int k = 0; k < movie_detail.getGenres().size(); k++) {
                                styledDocumentTextPane.insertString(styledDocumentTextPane.getLength(), movie_detail.getGenres().get(k).getName() + "  ", null);
                            }
                            styledDocumentTextPane.insertString(styledDocumentTextPane.getLength(), "\n\nAbstract:  " + movie_detail.getOverview(), null);
                            styledDocumentTextPane.insertString(styledDocumentTextPane.getLength(), "\n\nRun time: " + movie_detail.getRuntime() + "min", null);
                            styledDocumentTextPane.insertString(styledDocumentTextPane.getLength(), "\nMark: " + movie_detail.getVote_average() + "/10", null);
                        } catch (BadLocationException badLocationException) {
                            badLocationException.printStackTrace();
                        } catch (NullPointerException nullPointerException) {
                            try {
                                styledDocumentTextPane.insertString(styledDocumentTextPane.getLength(), "This movie has no detail or you have set Film ID in ERROR", null);
                            } catch (BadLocationException badLocationException) {
                                badLocationException.printStackTrace();
                            }

                        }
                    }


                    @Override
                    public void onFailure(Call<Movie.movie_detail> call, Throwable throwable) {

                    }
                });

            }
        });


        buttonAddFilm.addActionListener(new ActionListener() {
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
                            fw.write("\n" + textFieldFilmNameAddFilmToTxt.getText() + "," + comboBoxFilmModeAddFilmToTxt.getSelectedItem().toString() + "," + 0 + "," + 0);
                            fw.close();

                            listModel.addElement(new listmodel_addelement(textFieldFilmNameAddFilmToTxt.getText(), comboBoxFilmModeAddFilmToTxt.getSelectedItem().toString()));
                            lesfilms_inlist_list.add(new lesfilms_inlist(textFieldFilmNameAddFilmToTxt.getText(), comboBoxFilmModeAddFilmToTxt.getSelectedItem().toString(), 0, "0"));
                            frameAddFilmToTxt.setVisible(false);

                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                });

            }
        });


        buttonSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listModel2.removeAllElements();
                String str = textField1.getText();
                List<search_result> search_resultArrayList = new ArrayList<>();
                Call<SearchMovie> searchMovieCall = apiInterface.get_movie(utils.API_KEY, str);
                searchMovieCall.enqueue(new Callback<SearchMovie>() {
                    @Override
                    public void onResponse(Call<SearchMovie> call, Response<SearchMovie> response) {
                        SearchMovie searchMovie = response.body();
                        if (response.body().getTotal_results() != 0) {
                            JFrame frame_search = new JFrame("Search Results");
                            frame_search.setVisible(true);
                            frame_search.setDefaultCloseOperation(HIDE_ON_CLOSE);
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

                            for (int j = 0; j < searchMovie.getResults().size(); j++) {
                                List<Movie.movie_detail> movie_detailList = searchMovie.getResults();
                                listModel2.addElement(new listfilms_search(movie_detailList.get(j).getTitle()));
                                search_resultArrayList.add(new search_result(movie_detailList.get(j).getTitle(), movie_detailList.get(j).getId()));
                            }


                            list_search.addListSelectionListener(new ListSelectionListener() { // TODO Bug  1
                                @Override
                                public void valueChanged(ListSelectionEvent e) {
                                    if (e.getValueIsAdjusting()) return;
                                    jTextPaneSearch.setText(null);
                                    jLabelPostImage.setText(null);
                                    jLabelPostImage.setIcon(null);
                                    System.out.println(list_search.getSelectedIndex());
//                                    search_result search_result_index = search_resultArrayList.get(list_search.getSelectedIndex());
                                    search_result search_result_index = search_resultArrayList.get(0);
                                    try {
                                        search_result_index = search_resultArrayList.get(list_search.getSelectedIndex());
                                    } catch (Exception exception) {
                                        exception.printStackTrace();
                                    }

                                    int search_result_id = search_result_index.iddefilm;


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
//                                                ioException.printStackTrace();
                                                jLabelPostImage.setText("This movie has no post image");
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


                                                    frameAddFilmFromSearch.setSize(500, 200);

                                                    JLabel label1AddFilmToTxtFrameSearch = new JLabel("Name: ");
                                                    JTextField textFieldFilmNameAddFilmToTxtFrameSearch = new JTextField(10);
                                                    textFieldFilmNameAddFilmToTxtFrameSearch.setText(movie_detail.getTitle());
                                                    JLabel label2AddFilmToTxtFrameSearch = new JLabel("ID:   ");
                                                    JTextField textFieldFilmIdAddFilmToTxtFrameSearch = new JTextField(10);
                                                    textFieldFilmIdAddFilmToTxtFrameSearch.setText(String.valueOf(movie_detail.getId()));
                                                    textFieldFilmNameAddFilmToTxtFrameSearch.setSize(300, -1);


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
                                                            for (int i = 0; i < lesfilms_inlist_list.size(); i++) {
                                                                if (lesfilms_inlist_list.get(i).getFilmID() == Integer.valueOf(textFieldFilmIdAddFilmToTxtFrameSearch.getText())) {
                                                                    JOptionPane.showConfirmDialog(null, "This movie is already existe!", "Error", JOptionPane.PLAIN_MESSAGE);
                                                                    frameAddFilmFromSearch.setVisible(false);
                                                                    return;
                                                                }
                                                            }
                                                            listModel.addElement(new listmodel_addelement(textFieldFilmNameAddFilmToTxtFrameSearch.getText(), comboBoxFilmModeAddFilmToTxtFrameSearch.getSelectedItem().toString()));
                                                            lesfilms_inlist_list.add(new lesfilms_inlist(textFieldFilmIdAddFilmToTxtFrameSearch.getText(), comboBoxFilmModeAddFilmToTxtFrameSearch.getSelectedItem().toString(), Integer.valueOf(textFieldFilmIdAddFilmToTxtFrameSearch.getText()), movie_detail.getRelease_date()));
                                                            frameAddFilmFromSearch.setVisible(false);


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
                                                                fw.write("\n" + textFieldFilmNameAddFilmToTxtFrameSearch.getText() + "," + comboBoxFilmModeAddFilmToTxtFrameSearch.getSelectedItem().toString() + "," + textFieldFilmIdAddFilmToTxtFrameSearch.getText() + "," + movie_detail.getRelease_date());
                                                                fw.close();


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
                        } else if (response.body().getTotal_results() == 0) {
                            JOptionPane.showMessageDialog(null, "No result!");
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
        int filmID;
        String year;


        // TODO: Change model after when other disagree......
        public lesfilms_inlist(String nomdefilm, String model, int filmID, String year) {
            this.nomdefilm = nomdefilm;
            this.model = model;
            this.filmID = filmID;
            this.year = year;
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

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

    }

    public class listmodel_addelement {
        String nom;
        String model;

        public listmodel_addelement(String nom, String model) {
            this.nom = nom;
            this.model = model;
        }

        public String getNom() {
            return nom;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        @Override
        public String toString() {
            return nom + '\n' + model;
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
    public static Comparator<lesfilms_inlist> FilmNameComparator = new Comparator<lesfilms_inlist>() {
        @Override
        public int compare(lesfilms_inlist o1, lesfilms_inlist o2) {
            String FilmName1 = o1.getNomdefilm().toUpperCase();
            String FilmName2 = o2.getNomdefilm().toUpperCase();
            return FilmName1.compareTo(FilmName2);
        }
    };
    public static Comparator<lesfilms_inlist> FilmModelComparator = new Comparator<lesfilms_inlist>() {
        @Override
        public int compare(lesfilms_inlist o1, lesfilms_inlist o2) {
            String FilmModel1 = o1.getModel().toUpperCase();
            String FilmModel2 = o2.getModel().toUpperCase();
            return FilmModel1.compareTo(FilmModel2);
        }
    };
    public static Comparator<lesfilms_inlist> FilmYearComparator = new Comparator<lesfilms_inlist>() {
        @Override
        public int compare(lesfilms_inlist o1, lesfilms_inlist o2) {
            String FilmYear1 = o1.getYear().toUpperCase();
            String FilmYear2 = o2.getYear().toUpperCase();
            return FilmYear1.compareTo(FilmYear2);
        }
    };
}
