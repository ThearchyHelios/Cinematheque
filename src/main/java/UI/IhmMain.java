package UI;

import API.API;
import API.APIInterface;
import API.utils;
import Module.Movie;
import Search.SearchMovie;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


// Yilun JIANG API Key: 89e5521b3e8381cf6adc8f4c8432e07d
// Author: JIANG WANG KANG

public class IhmMain extends JFrame {
    private JPanel mainInterface;
    private JButton buttonSearch;
    private JTextField textField1;
    private JButton buttonAddFilm;
    private JLabel labelLesFilms;
    private JList<ListModelElement> listLesFilm;
    private JTextArea textAreafilmInfo;
    private JLabel labelNomDeFilm;
    private JScrollPane scrollPaneListDeFilm;
    private JTextPane textPaneMovieDetail;
    private JScrollPane scrollPaneTextPane;
    private JLabel labelFilmImage;
    private JComboBox <String> comboBoxSort;
    private JButton buttonDelFilm;
    private APIInterface apiInterface;


    // Define listModel with model ListModelElement to stock films with Nom et Model.
    DefaultListModel<ListModelElement> listModel = new DefaultListModel<>();

    // Define listModel2 with model ListModelSearch to stock films with Nom.
    DefaultListModel<ListFilmSearch> listModel2 = new DefaultListModel<>();

    /**
     * Create main frame {@code IhmMain}.
     */
    public static void mainFrame() {
        JFrame frame = new JFrame("IhmMain");
        frame.setContentPane(new IhmMain().mainInterface);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        mainFrame();
    }


    public IhmMain() {

        // Create template of URL with API Key from {@link API.getAPI}
        apiInterface = API.getAPI().create(APIInterface.class);

        // Set a ScrollPane List so we can use scroll pane to scroll the film list, inside the scroll pane
        // we use JList to stocks every films inside TXT.
        scrollPaneListDeFilm.setViewportView(listLesFilm);

        // Set a ScrollPane Text so we can use scroll pane to scroll the film detail, inside the scroll pane
        // we use JText to show the movie detail.
        scrollPaneTextPane.setViewportView(textPaneMovieDetail);

        // Set the value of JList in Nom, Model
        listLesFilm.setModel(listModel);

        // Show a PNG of "Blue-ray" or "DVD" or "Digital" so that users can find the model of movies more conveniently.
        // To show more inforfation, go to file ui.MyListUI.
        listLesFilm.setCellRenderer(new MyListUI());

        // Set TextPane as a generic styled document. (To show movie details in style)
        StyledDocument styledDocumentTextPane = textPaneMovieDetail.getStyledDocument();

        // Read films details from file.
        File directory = new File("src/main/resources/film.csv");
        String absoultePath = directory.getAbsolutePath();

        // Create 4 List to store film details, including film name, model, film id and released year.
        // These 4 lists is only used as buffers after reading file.
        List<String> listFilmInTxt = new ArrayList<String>();
        List<String> listModeInTxt = new ArrayList<String>();
        List<Integer> listFilmIdInTxt = new ArrayList<Integer>();
        List<String> listYearInTxt = new ArrayList<String>();

        String line = "";
        try {
            FileInputStream fin = new FileInputStream(absoultePath);

            // Use InputStreamReader to read bytes and decodes them into characters
            InputStreamReader reader = new InputStreamReader(fin);

            // Reads text from a character-input stream, buffering characters so as to provide for the
            // efficient reading of characters, arrays, and lines.
            BufferedReader buffReader = new BufferedReader(reader);

            // Read each line till last line.
            while ((line = buffReader.readLine()) != null) {
                System.out.println(line);

                // Spilt each line with ',' to "Name", "Mode", "ID", "Year".
                String[] stringFilm = line.split(",");

                // Add each name to list.
                listFilmInTxt.add(stringFilm[0]);

                // Add each mode to list.
                listModeInTxt.add(stringFilm[1]);

                // Add each ID to list.

                listFilmIdInTxt.add(Integer.valueOf(stringFilm[2]));

                // Add each year to list.
                listYearInTxt.add(stringFilm[3]);
            }
            buffReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Instantiate a stable list with using model <Name, Model, ID, Year> to store all film information.
        List<LesFilmsInList> listFilmInList = new ArrayList<>();


        for (int j = 0; j < listFilmInTxt.size(); j++) {

            // Import film information from buffer lists, to be shown in List of Main Interface.
            listModel.addElement(new ListModelElement(listFilmInTxt.get(j) + " ", listModeInTxt.get(j)));

            // Import film information from buffer lists.
            listFilmInList.add(new LesFilmsInList(listFilmInTxt.get(j), listModeInTxt.get(j), listFilmIdInTxt.get(j), listYearInTxt.get(j)));
        }

        // Add 4 sort model to combobox.
        comboBoxSort.addItem("Sort");
        comboBoxSort.addItem("Name");
        comboBoxSort.addItem("Model");
        comboBoxSort.addItem("Year");

        
        // Instantiate a action listener to check if the context of combobox is selected.
        comboBoxSort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Check if the selected item is "Sort", the order of films will be the same order as txt file.
                if (comboBoxSort.getSelectedItem() == "Sort") {

                    // Instantiate a new buffer file reader to read the film detail.
                    File directory = new File("src/main/resources/film.csv");
                    String absolutePath = directory.getAbsolutePath();
                    List<String> listFilmInTxt = new ArrayList<String>();
                    List<String> listModeInTxt = new ArrayList<String>();
                    List<Integer> listFilmIdInTxt = new ArrayList<Integer>();
                    List<String> listYearInTxt = new ArrayList<String>();

                    String line = "";
                    try {
                        FileInputStream fin = new FileInputStream(absolutePath);
                        InputStreamReader reader = new InputStreamReader(fin);
                        BufferedReader buffReader = new BufferedReader(reader);
                        while ((line = buffReader.readLine()) != null) {
                            System.out.println(line);
                            String[] stringFilm = line.split(",");
                            listFilmInTxt.add(stringFilm[0]);
                            listModeInTxt.add(stringFilm[1]);
                            listFilmIdInTxt.add(Integer.valueOf(stringFilm[2]));
                            listYearInTxt.add(stringFilm[3]);
                        }
                        System.out.println(listFilmInTxt);
                        System.out.println(listModeInTxt);
                        buffReader.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    for (int j = 0; j < listFilmInTxt.size(); j++) {
                        // Import film information from buffer lists, to be shown in List of Main Interface.
                        listModel.set(j, new ListModelElement(listFilmInTxt.get(j) + " ", listModeInTxt.get(j)));

                        // Import film information from buffer lists.
                        listFilmInList.set(j, new LesFilmsInList(listFilmInTxt.get(j), listModeInTxt.get(j), listFilmIdInTxt.get(j), listYearInTxt.get(j)));
                    }

                // Check if the selected item is "Model", the order of films will be sorted by model.
                } else if (comboBoxSort.getSelectedItem() == "Model") {

                    // Compare models from list, then sort models.
                    Collections.sort(listFilmInList, FilmModelComparator);

                    // Reset listmodel from list.
                    for (int i = 0; i < listFilmInList.size(); i++) {
                        listModel.set(i, new ListModelElement(listFilmInList.get(i).getNomdefilm(), listFilmInList.get(i).getModel()));
                    }

                    // Refresh list.
                    listLesFilm.setModel(listModel);

                // Check if the selected item is "Name", the order of films will be sorted by name.
                } else if (comboBoxSort.getSelectedItem() == "Name") {

                    // Compare names from list, then sort names.
                    Collections.sort(listFilmInList, FilmNameComparator);

                    // Reset listmodel from list.
                    for (int i = 0; i < listFilmInList.size(); i++) {
                        listModel.set(i, new ListModelElement(listFilmInList.get(i).getNomdefilm(), listFilmInList.get(i).getModel()));
                    }

                    // Refresh list.
                    listLesFilm.setModel(listModel);

                // Check if the selected item is "Year", the order of films will be sorted by release date.
                } else if (comboBoxSort.getSelectedItem() == "Year") {

                    // Compare names from list, then sort year.
                    Collections.sort(listFilmInList, FilmYearComparator);

                    // Reset listmodel from list.
                    for (int i = 0; i < listFilmInList.size(); i++) {
                        listModel.set(i, new ListModelElement(listFilmInList.get(i).getNomdefilm(), listFilmInList.get(i).getModel()));
                    }

                    // Refresh list
                    listLesFilm.setModel(listModel);
                }

            }
        });

        // Add selection listener to catch if the list is selected.
        listLesFilm.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                // Get the value only when the left key is released.
                if (e.getValueIsAdjusting()) {
                    return;
                }

                // Eliminate the problem when the pointer is at -1
                if (listLesFilm.getSelectedIndex() == -1) {
                    return;
                }

                // Initialize the value of the picture
                labelFilmImage.setText(null);
                labelFilmImage.setIcon(null);

                // Initialize the value of pane.
                textPaneMovieDetail.setText(null);

                System.out.println(listLesFilm.getSelectedIndex());

                // Instantiate the value to the selected film.
                LesFilmsInList lesFilmsInListSelected = listFilmInList.get(listLesFilm.getSelectedIndex());

                // Instantiate and get Film ID.
                int filmId = lesFilmsInListSelected.getFilmId();

                // We use the movie ID to get movie details.
                // See the api from website below:
                // https://developers.themoviedb.org/3/movies/get-movie-details
                Call<Movie.movie_detail> callMovieDetail = apiInterface.get_movie_by_id(filmId, utils.API_KEY);

                callMovieDetail.enqueue(new Callback<Movie.movie_detail>() {
                    @Override
                    public void onResponse(Call<Movie.movie_detail> call, Response<Movie.movie_detail> response) {

                        // Get movie detail by using model Movie.movie_detail from the response which we get from
                        // website.
                        Movie.movie_detail movieDetail = response.body();

                        // Instantiate a new attribute set for setting the title.
                        SimpleAttributeSet attributeSetTitle = new SimpleAttributeSet();

                        // Set title to Bold.
                        StyleConstants.setBold(attributeSetTitle, true);

                        // Set font size to 30.
                        StyleConstants.setFontSize(attributeSetTitle, 30);

                        // Instantiate a buffered image to stock Poster Image.
                        BufferedImage bufferedImageIcon = null;

                        try {

                            // Set buffered image as Poster from URL.
                            bufferedImageIcon = ImageIO.read(new URL("https://image.tmdb.org/t/p/w500" + movieDetail.getPoster_path()));

                            // Instantiate a icon interface to paint icons from image.
                            ImageIcon imageIcon = new ImageIcon(bufferedImageIcon);
                            labelFilmImage.setIcon(imageIcon);


                        } catch (IOException ioException) {

                            // If we cannot read image from website then return No Image.
                            ioException.printStackTrace();
                            labelFilmImage.setText("NO IMAGE ");
                        } catch (NullPointerException nullPointerException) {

                            // If we cannot get poster path which means this movie is added by ourselves, then
                            // return This movie is added by myself.
                            nullPointerException.printStackTrace();
                            labelFilmImage.setText("This movie has been added by yourself");
                        }


                        // Edit Text Pane
                        try {
                            styledDocumentTextPane.insertString(styledDocumentTextPane.getLength(), movieDetail.getTitle(), attributeSetTitle);
                            styledDocumentTextPane.insertString(styledDocumentTextPane.getLength(), "\nReleased date: " + movieDetail.getRelease_date() + "\nGenre: ", null);
                            for (int k = 0; k < movieDetail.getGenres().size(); k++) {
                                styledDocumentTextPane.insertString(styledDocumentTextPane.getLength(), movieDetail.getGenres().get(k).getName() + "  ", null);
                            }
                            styledDocumentTextPane.insertString(styledDocumentTextPane.getLength(), "\n\nAbstract:  " + movieDetail.getOverview(), null);
                            styledDocumentTextPane.insertString(styledDocumentTextPane.getLength(), "\n\nRun time: " + movieDetail.getRuntime() + "min", null);
                            styledDocumentTextPane.insertString(styledDocumentTextPane.getLength(), "\nMark: " + movieDetail.getVote_average() + "/10", null);
                        } catch (BadLocationException badLocationException) {
                            badLocationException.printStackTrace();
                        } catch (NullPointerException nullPointerException) {
                            try {
                                styledDocumentTextPane.insertString(styledDocumentTextPane.getLength(), "This movie has no detail", null);
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

                            listModel.addElement(new ListModelElement(textFieldFilmNameAddFilmToTxt.getText(), comboBoxFilmModeAddFilmToTxt.getSelectedItem().toString()));
                            listFilmInList.add(new LesFilmsInList(textFieldFilmNameAddFilmToTxt.getText(), comboBoxFilmModeAddFilmToTxt.getSelectedItem().toString(), 0, "0"));
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
                List<SearchResult> searchResultArrayList = new ArrayList<>();
                Call<SearchMovie> searchMovieCall = apiInterface.get_movie(utils.API_KEY, str);
                searchMovieCall.enqueue(new Callback<SearchMovie>() {
                    @Override
                    public void onResponse(Call<SearchMovie> call, Response<SearchMovie> response) {
                        SearchMovie searchMovie = response.body();
                        if (response.body().getTotal_results() != 0) {
                            JFrame frameSearch = new JFrame("Search Results");
                            frameSearch.setVisible(true);
                            frameSearch.setDefaultCloseOperation(HIDE_ON_CLOSE);
                            frameSearch.setSize(700, 500);
                            JList<ListFilmSearch> listSearch = new JList<>();
                            listSearch.setModel(listModel2);


                            JScrollPane jScrollPaneSearch = new JScrollPane(listSearch);
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

                            frameSearch.add(jScrollPaneSearch, BorderLayout.NORTH);
                            frameSearch.add(jTextPaneSearch, BorderLayout.CENTER);
//                            frame_search.add(jScrollPaneTextPaneSearch, BorderLayout.EAST);
                            frameSearch.add(jLabelPostImage, BorderLayout.WEST);
                            frameSearch.add(jButtonAddToList, BorderLayout.SOUTH);
//                            frame_search.add(panelSearchCenter, BorderLayout.CENTER);

                            for (int j = 0; j < searchMovie.getResults().size(); j++) {
                                List<Movie.movie_detail> listMovieDetail = searchMovie.getResults();
                                listModel2.addElement(new ListFilmSearch(listMovieDetail.get(j).getTitle()));
                                searchResultArrayList.add(new SearchResult(listMovieDetail.get(j).getTitle(), listMovieDetail.get(j).getId()));
                            }


                            listSearch.addListSelectionListener(new ListSelectionListener() { // TODO Bug  1
                                @Override
                                public void valueChanged(ListSelectionEvent e) {
                                    if (e.getValueIsAdjusting()) {
                                        return;
                                    }
                                    jTextPaneSearch.setText(null);
                                    jLabelPostImage.setText(null);
                                    jLabelPostImage.setIcon(null);
                                    System.out.println(listSearch.getSelectedIndex());
//                                    search_result search_result_index = search_resultArrayList.get(list_search.getSelectedIndex());
                                    SearchResult searchResultIndex = searchResultArrayList.get(0);
                                    try {
                                        searchResultIndex = searchResultArrayList.get(listSearch.getSelectedIndex());
                                    } catch (Exception exception) {
                                        exception.printStackTrace();
                                    }

                                    int searchResultFilmId = searchResultIndex.iddefilm;


                                    Call<Movie.movie_detail> callMovieDetail = apiInterface.get_movie_by_id(searchResultFilmId, utils.API_KEY);

                                    callMovieDetail.enqueue(new Callback<Movie.movie_detail>() {
                                        @Override
                                        public void onResponse(Call<Movie.movie_detail> call, Response<Movie.movie_detail> response) {
                                            Movie.movie_detail movieDetail = response.body();

                                            SimpleAttributeSet attributeSetTitle = new SimpleAttributeSet();
                                            StyleConstants.setBold(attributeSetTitle, true);
                                            StyleConstants.setFontSize(attributeSetTitle, 30);

                                            BufferedImage bufferedImageIcon = null;
                                            try {
                                                bufferedImageIcon = ImageIO.read(new URL("https://image.tmdb.org/t/p/w200" + movieDetail.getPoster_path()));
                                                ImageIcon imageIcon = new ImageIcon(bufferedImageIcon);
                                                jLabelPostImage.setIcon(imageIcon);

                                            } catch (IOException ioException) {
//                                                ioException.printStackTrace();
                                                jLabelPostImage.setText("This movie has no post image");
                                            }


//                                            Edit Search Text Pane


                                            try {
                                                styledDocumentTextpaneSearch.insertString(styledDocumentTextpaneSearch.getLength(), movieDetail.getTitle(), attributeSetTitle);
                                                styledDocumentTextpaneSearch.insertString(styledDocumentTextpaneSearch.getLength(), "\nReleased date: " + movieDetail.getRelease_date() + "\nGenres: ", null);
                                                for (int k = 0; k < movieDetail.getGenres().size(); k++) {
                                                    styledDocumentTextpaneSearch.insertString(styledDocumentTextpaneSearch.getLength(), movieDetail.getGenres().get(k).getName() + "  ", null);
                                                }
                                                styledDocumentTextpaneSearch.insertString(styledDocumentTextpaneSearch.getLength(), "\n\nAbstract:  " + movieDetail.getOverview(), null);
                                                styledDocumentTextpaneSearch.insertString(styledDocumentTextpaneSearch.getLength(), "\n\nRun time: " + movieDetail.getRuntime() + "min", null);
                                                styledDocumentTextpaneSearch.insertString(styledDocumentTextpaneSearch.getLength(), "\nMark: " + movieDetail.getVote_average() + "/10", null);
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
                                                    textFieldFilmNameAddFilmToTxtFrameSearch.setText(movieDetail.getTitle());
                                                    JLabel label2AddFilmToTxtFrameSearch = new JLabel("ID:   ");
                                                    JTextField textFieldFilmIdAddFilmToTxtFrameSearch = new JTextField(10);
                                                    textFieldFilmIdAddFilmToTxtFrameSearch.setText(String.valueOf(movieDetail.getId()));
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
                                                            for (int i = 0; i < listFilmInList.size(); i++) {
                                                                if (listFilmInList.get(i).getFilmId() == Integer.valueOf(textFieldFilmIdAddFilmToTxtFrameSearch.getText())) {
                                                                    JOptionPane.showConfirmDialog(null, "This movie is already existe!", "Error", JOptionPane.PLAIN_MESSAGE);
                                                                    frameAddFilmFromSearch.setVisible(false);
                                                                    return;
                                                                }
                                                            }
                                                            listModel.addElement(new ListModelElement(textFieldFilmNameAddFilmToTxtFrameSearch.getText(), comboBoxFilmModeAddFilmToTxtFrameSearch.getSelectedItem().toString()));
                                                            listFilmInList.add(new LesFilmsInList(textFieldFilmIdAddFilmToTxtFrameSearch.getText(), comboBoxFilmModeAddFilmToTxtFrameSearch.getSelectedItem().toString(), Integer.valueOf(textFieldFilmIdAddFilmToTxtFrameSearch.getText()), movieDetail.getRelease_date()));
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
                                                                fw.write("\n" + textFieldFilmNameAddFilmToTxtFrameSearch.getText() + "," + comboBoxFilmModeAddFilmToTxtFrameSearch.getSelectedItem().toString() + "," + textFieldFilmIdAddFilmToTxtFrameSearch.getText() + "," + movieDetail.getRelease_date());
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
            }
        });
//
//        textField1.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//            }
//        });

        // Delete Button
        buttonDelFilm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int indexDelFilm = listLesFilm.getSelectedIndex();
                System.out.println(indexDelFilm);
                String stringDelFilmNom = new String(listFilmInList.get(indexDelFilm).getNomdefilm());
                // Delete Last Space in String
                System.out.println(stringDelFilmNom);

                // Get Film names from TXT file, search the movie which we want to delete and then delete it
                //TXT to ArrayList
                File directory = new File("src/main/resources/film.csv");
                String absoultePath = directory.getAbsolutePath();
                List<String> listFilmInTxt = new ArrayList<String>();
                List<String> listModeInTxt = new ArrayList<String>();
                List<Integer> listFilmIdInTxt = new ArrayList<Integer>();
                List<String> listYearInTxt = new ArrayList<String>();

                String line = "";
                try {
                    FileInputStream fin = new FileInputStream(absoultePath);
                    InputStreamReader reader = new InputStreamReader(fin);
                    BufferedReader buffReader = new BufferedReader(reader);
                    StringBuffer stringBuffer = new StringBuffer();
                    while ((line = buffReader.readLine()) != null) {
                        String[] stringFilm = line.split(",");
                        listFilmInTxt.add(stringFilm[0]);
                        listModeInTxt.add(stringFilm[1]);
                        listFilmIdInTxt.add(Integer.valueOf(stringFilm[2]));
                        listYearInTxt.add(stringFilm[3]);
                    }
                    System.out.println(listFilmInTxt);
                    buffReader.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }


                for (int i = 0; i < listFilmInTxt.size(); i++) {
//                    System.out.println(listFilmInTxt.get(i));
                    if (listFilmInTxt.get(i).equals(stringDelFilmNom)) {
                        System.out.println(i);
                        System.out.println(listFilmInTxt.get(i));
                        if (indexDelFilm != -1) {
                            listModel.remove(indexDelFilm);
                            System.out.println("yes");
                            try {
                                FileWriter fw = new FileWriter(absoultePath);
                                fw.write("");
                                fw.flush();
                                fw.close();
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                            listFilmInTxt.remove(i);
                            listModeInTxt.remove(i);
                            listFilmIdInTxt.remove(i);
                            listYearInTxt.remove(i);


                            for (int j = 0; j < listFilmInTxt.size(); j++) {
                                if (j < listFilmInTxt.size() - 1) {
                                    try {
                                        FileWriter fw = new FileWriter(absoultePath, true);
                                        fw.write(listFilmInTxt.get(j) + "," + listModeInTxt.get(j) + "," + listFilmIdInTxt.get(j) + "," + listYearInTxt.get(j) + "\n");
                                        fw.close();
                                    } catch (IOException ioException) {
                                        ioException.printStackTrace();
                                    }
                                } else {
                                    try {
                                        FileWriter fw = new FileWriter(absoultePath, true);
                                        fw.write(listFilmInTxt.get(j) + "," + listModeInTxt.get(j) + "," + listFilmIdInTxt.get(j) + "," + listYearInTxt.get(j));
                                        fw.close();
                                    } catch (IOException ioException) {
                                        ioException.printStackTrace();
                                    }
                                }

                            }
                        }

                    }
                }


            }
        });
    }


    public class LesFilmsInList {
        String nomdefilm;
        String model;
        int filmId;
        String year;

        public LesFilmsInList(String nomdefilm, String model, int filmId, String year) {
            this.nomdefilm = nomdefilm;
            this.model = model;
            this.filmId = filmId;
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

        public int getFilmId() {
            return filmId;
        }

        public void setFilmId(int filmId) {
            this.filmId = filmId;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

    }

    public class ListModelElement {
        String nom;
        String model;

        public ListModelElement(String nom, String model) {
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

    public class ListFilmSearch {
        String nomdefilm;


        public ListFilmSearch(String nomdefilm) {
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


    public class SearchResult {
        String nomdefilm;
        int iddefilm;

        public SearchResult(String nomdefilm, int iddefilm) {
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

    public static Comparator<LesFilmsInList> FilmNameComparator = new Comparator<LesFilmsInList>() {
        @Override
        public int compare(LesFilmsInList o1, LesFilmsInList o2) {
            String FilmName1 = o1.getNomdefilm().toUpperCase();
            String FilmName2 = o2.getNomdefilm().toUpperCase();
            return FilmName1.compareTo(FilmName2);
        }
    };
    public static Comparator<LesFilmsInList> FilmModelComparator = new Comparator<LesFilmsInList>() {
        @Override
        public int compare(LesFilmsInList o1, LesFilmsInList o2) {
            String FilmModel1 = o1.getModel().toUpperCase();
            String FilmModel2 = o2.getModel().toUpperCase();
            return FilmModel1.compareTo(FilmModel2);
        }
    };
    public static Comparator<LesFilmsInList> FilmYearComparator = new Comparator<LesFilmsInList>() {
        @Override
        public int compare(LesFilmsInList o1, LesFilmsInList o2) {
            String FilmYear1 = o1.getYear().toUpperCase();
            String FilmYear2 = o2.getYear().toUpperCase();
            return FilmYear1.compareTo(FilmYear2);
        }
    };
}
