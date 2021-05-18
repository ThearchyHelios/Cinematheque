package UI;

import API.API;
import API.APIInterface;
import API.utils;
import Module.Movie;
import Module.Actor;
import Search.SearchActor;
import Search.SearchMovie;
import Search.SearchMovieByPersonID;
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

// Project Cinematheque {@link https://github.com/Marshellson/Cinematheque}

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
    private JComboBox<String> comboBoxSort;
    private JButton buttonDelFilm;
    private JComboBox comboBoxSelectSearchType;
    private APIInterface apiInterface;


    // Define listModel with model ListModelElement to stock films with Nom et Model.
    DefaultListModel<ListModelElement> listModel = new DefaultListModel<>();

    // Define listModel2 with model ListModelSearch to stock films with Nom.
    DefaultListModel<ListSearchModel> listModel2 = new DefaultListModel<>();

    DefaultListModel<ListSearchModel> listModel3 = new DefaultListModel<>();

    DefaultListModel<ListSearchModel> listModel4 = new DefaultListModel<>();


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
        textPaneMovieDetail.setEditable(false);

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

        // Add 2 search model to combobox.
        comboBoxSelectSearchType.addItem("Film");
        comboBoxSelectSearchType.addItem("Actor");


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

                System.out.println(listLesFilm.getSelectedIndex() + " has been selected!");

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


                        try {

                            // Import movie detail into text pane.
                            // Import formatted title into text pane.
                            styledDocumentTextPane.insertString(styledDocumentTextPane.getLength(), movieDetail.getTitle(), attributeSetTitle);

                            // Import released date into text pane.
                            styledDocumentTextPane.insertString(styledDocumentTextPane.getLength(), "\nReleased date: " + movieDetail.getRelease_date() + "\nGenre: ", null);

                            // Import Genre(s) into text pane.
                            for (int k = 0; k < movieDetail.getGenres().size(); k++) {
                                styledDocumentTextPane.insertString(styledDocumentTextPane.getLength(), movieDetail.getGenres().get(k).getName() + "  ", null);
                            }

                            // Import abstract into text pane.
                            styledDocumentTextPane.insertString(styledDocumentTextPane.getLength(), "\n\nAbstract:  " + movieDetail.getOverview(), null);

                            // Import movie run time into text pane.
                            styledDocumentTextPane.insertString(styledDocumentTextPane.getLength(), "\n\nRun time: " + movieDetail.getRuntime() + "min", null);

                            // Import movie score (*/10) into text pane
                            styledDocumentTextPane.insertString(styledDocumentTextPane.getLength(), "\nMark: " + movieDetail.getVote_average() + "/10", null);
                        } catch (BadLocationException badLocationException) {

                            //  This exception is to report bad locations within a document model
                            //  (that is, attempts to reference a location that doesn't exist).
                            badLocationException.printStackTrace();
                        } catch (NullPointerException nullPointerException) {
                            try {
                                // If throwing null then means the movie is added by ourselves.
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

        // Add action Listener to catch if button is clicked.
        buttonAddFilm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Instantiate frame "Add Film"
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

                // Add action listener to catch if button is clicked.
                buttonConfirmAddfilmToText.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        // Check if the length of film name is 0, if then return error.
                        if (textFieldFilmNameAddFilmToTxt.getText().length() == 0) {
                            JOptionPane.showMessageDialog(null, "You must enter film name!", "ERROR", JOptionPane.PLAIN_MESSAGE);
                            return;
                        }

                        // Write Film information into txt file, and add film into list model to show on screen.
                        try {

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


        // Add action listener to catch if the button is clicked.
        buttonSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboBoxSelectSearchType.getSelectedItem() == "Film") {
                    if (textField1.getText().length() == 0 || textField1.getText().replaceAll("\s", "") == null) {
                        JOptionPane.showMessageDialog(null, "You must enter film name!", "ERROR", JOptionPane.PLAIN_MESSAGE);
                        return;
                    }
                    // Init list model in search frame.
                    listModel2.removeAllElements();
                    String str = textField1.getText();
                    List<SearchResultFilm> searchResultFilmArrayList = new ArrayList<>();

                    // Asynchronously send the request and notify callback of its response or if an error occurred talking
                    // to the server, creating the request, or processing the response.
                    Call<SearchMovie> searchMovieCall = apiInterface.get_movie(utils.API_KEY, str);
                    searchMovieCall.enqueue(new Callback<SearchMovie>() {
                        @Override
                        // Invoked for a received HTTP response.
                        public void onResponse(Call<SearchMovie> call, Response<SearchMovie> response) {
                            SearchMovie searchMovie = response.body();

                            // Determinate if we get the result, if we get results then response.getTotal_results != 0
                            if (response.body() == null) {
                                JOptionPane.showMessageDialog(null, "You must enter film name!", "ERROR", JOptionPane.PLAIN_MESSAGE);
                                return;
                            }
                            if (response.body().getTotal_results() != 0) {

                                // Instantiate new frame for search results.
                                JFrame frameSearch = new JFrame("Search Results");
                                frameSearch.setVisible(true);
                                frameSearch.setDefaultCloseOperation(HIDE_ON_CLOSE);
                                frameSearch.setSize(700, 500);
                                JList<ListSearchModel> listSearchRemote = new JList<>();
                                listSearchRemote.setModel(listModel2);


                                JScrollPane jScrollPaneSearch = new JScrollPane(listSearchRemote);
                                jScrollPaneSearch.setSize(600, 600);


                                JTextPane jTextPaneSearch = new JTextPane();
                                jTextPaneSearch.setSize(400, 300);
                                StyledDocument styledDocumentTextpaneSearch = jTextPaneSearch.getStyledDocument();
                                jTextPaneSearch.setEditable(false);
                                JScrollPane jScrollPaneTextPaneSearch = new JScrollPane(jTextPaneSearch);

                                JButton jButtonAddToList = new JButton();
                                jButtonAddToList.setText("Add To Library");
                                jButtonAddToList.setFont(new Font("Monaco", Font.BOLD, 20));

                                JLabel jLabelPostImage = new JLabel();


                                JPanel panelSearchCenter = new JPanel();
                                JPanel panelSearchNorth = new JPanel();

                                panelSearchCenter.add(jTextPaneSearch);

                                frameSearch.add(jScrollPaneSearch, BorderLayout.NORTH);
                                frameSearch.add(jTextPaneSearch, BorderLayout.CENTER);
                                frameSearch.add(jLabelPostImage, BorderLayout.WEST);
                                frameSearch.add(jButtonAddToList, BorderLayout.SOUTH);


                                List<Movie.movie_detail> listMovieDetail = searchMovie.getResults();
                                // Add all movies which in results into list model to show.
                                for (int j = 0; j < searchMovie.getResults().size(); j++) {
                                    listModel2.addElement(new ListSearchModel(listMovieDetail.get(j).getTitle()));
                                    searchResultFilmArrayList.add(new SearchResultFilm(listMovieDetail.get(j).getTitle(), listMovieDetail.get(j).getId(), listMovieDetail.get(j).getRelease_date()));
                                    for (int a = 0; a < listFilmInList.toArray().length; a++) {
                                        if (listFilmInList.get(a).getNomdefilm().equals(listMovieDetail.get(j).getTitle())) {
                                            listModel2.setElementAt(new ListSearchModel(listMovieDetail.get(j).getTitle() + "  | Already In Library!"), j);
                                        }
                                    }
                                }


                                // Add selection listener to catch if the movie is selected.
                                listSearchRemote.addListSelectionListener(new ListSelectionListener() {
                                    @Override
                                    public void valueChanged(ListSelectionEvent e) {

                                        // Get the value only when the left key is released.
                                        if (e.getValueIsAdjusting()) {
                                            return;
                                        }
                                        jTextPaneSearch.setText(null);
                                        jLabelPostImage.setText(null);
                                        jLabelPostImage.setIcon(null);
                                        System.out.println(listSearchRemote.getSelectedIndex() + " has been selected!");
//                                    search_result search_result_index = search_resultArrayList.get(list_search.getSelectedIndex());

                                        // Instantiate the movie info which selected.
                                        SearchResultFilm searchResultFilmSelect = searchResultFilmArrayList.get(0);
                                        try {
                                            searchResultFilmSelect = searchResultFilmArrayList.get(listSearchRemote.getSelectedIndex());
                                        } catch (Exception exception) {
                                            exception.printStackTrace();
                                        }

                                        int searchResultFilmId = searchResultFilmSelect.iddefilm;
                                        String searchResultFilmNom = searchResultFilmSelect.nomdefilm;

                                        // Use the movie id which we got to get movie info.
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
                                            }


                                            @Override
                                            public void onFailure(Call<Movie.movie_detail> call, Throwable throwable) {
                                                JOptionPane.showMessageDialog(null, "You must enter film name!", "ERROR", JOptionPane.PLAIN_MESSAGE);
                                            }
                                        });
                                    }
                                });

                                // Add action listener to catch if the button is clicked.
                                jButtonAddToList.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {

                                        System.out.println("Film: " + searchResultFilmArrayList.get(listSearchRemote.getSelectedIndex()).nomdefilm);
                                        System.out.println("Film ID:" + searchResultFilmArrayList.get(listSearchRemote.getSelectedIndex()).iddefilm);

                                        JFrame frameAddFilmFromSearch = new JFrame("Add Film From Search");
                                        frameAddFilmFromSearch.setVisible(true);
                                        frameAddFilmFromSearch.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

                                        JPanel jPanel1 = new JPanel();
                                        JPanel jPanel3 = new JPanel();
                                        JPanel jPanel2 = new JPanel();


                                        frameAddFilmFromSearch.setSize(500, 200);

                                        JLabel label1AddFilmToTxtFrameSearch = new JLabel("Name: ");
                                        JTextField textFieldFilmNameAddFilmToTxtFrameSearch = new JTextField(10);
                                        textFieldFilmNameAddFilmToTxtFrameSearch.setText(searchResultFilmArrayList.get(listSearchRemote.getSelectedIndex()).nomdefilm);
                                        JLabel label2AddFilmToTxtFrameSearch = new JLabel("ID:   ");
                                        JTextField textFieldFilmIdAddFilmToTxtFrameSearch = new JTextField(10);
                                        textFieldFilmIdAddFilmToTxtFrameSearch.setText(String.valueOf(searchResultFilmArrayList.get(listSearchRemote.getSelectedIndex()).iddefilm));
                                        textFieldFilmNameAddFilmToTxtFrameSearch.setSize(300, -1);


                                        jPanel1.add(label1AddFilmToTxtFrameSearch);
                                        jPanel1.add(textFieldFilmNameAddFilmToTxtFrameSearch);
                                        jPanel2.add(label2AddFilmToTxtFrameSearch);
                                        jPanel2.add(textFieldFilmIdAddFilmToTxtFrameSearch);

                                        textFieldFilmNameAddFilmToTxtFrameSearch.setEditable(true);
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


                                                try {

                                                    listModel.addElement(new ListModelElement(textFieldFilmNameAddFilmToTxtFrameSearch.getText(), comboBoxFilmModeAddFilmToTxtFrameSearch.getSelectedItem().toString()));
                                                    listFilmInList.add(new LesFilmsInList(textFieldFilmNameAddFilmToTxtFrameSearch.getText(), comboBoxFilmModeAddFilmToTxtFrameSearch.getSelectedItem().toString(), Integer.valueOf(textFieldFilmIdAddFilmToTxtFrameSearch.getText()), searchResultFilmArrayList.get(listSearchRemote.getSelectedIndex()).release_date));
                                                    frameAddFilmFromSearch.setVisible(false);

                                                    FileWriter fw = new FileWriter(absoultePath, true);
                                                    fw.write("\n" + textFieldFilmNameAddFilmToTxtFrameSearch.getText() + "," + comboBoxFilmModeAddFilmToTxtFrameSearch.getSelectedItem().toString() + "," + textFieldFilmIdAddFilmToTxtFrameSearch.getText() + "," + searchResultFilmArrayList.get(listSearchRemote.getSelectedIndex()).release_date);
                                                    fw.close();


                                                } catch (IOException ioException) {
                                                    ioException.printStackTrace();
                                                }

                                            }
                                        });

                                    }
                                });

                            } else if (response.body().getTotal_results() == 0) {
                                JOptionPane.showMessageDialog(null, "No result!");
                            }

                        }


                        @Override
                        // Invoked when a network exception occurred talking to the server or when an unexpected exception
                        // occurred creating the request or processing the response.
                        public void onFailure(Call<SearchMovie> call, Throwable throwable) {
                            throwable.getMessage();

                        }
                    });
                } else if (comboBoxSelectSearchType.getSelectedItem() == "Actor") {
                    if (textField1.getText().length() == 0 || textField1.getText().replaceAll("\s", "") == null) {
                        JOptionPane.showMessageDialog(null, "You must enter actor name!", "ERROR", JOptionPane.PLAIN_MESSAGE);
                        return;
                    }
                    listModel2.removeAllElements();
                    listModel3.removeAllElements();
                    String str = textField1.getText();
                    List<SearchResultActor> searchResultActors = new ArrayList<>();

                    Call<SearchActor> searchActorCall = apiInterface.get_actor(utils.API_KEY, str);
                    searchActorCall.enqueue(new Callback<SearchActor>() {
                        @Override
                        public void onResponse(Call<SearchActor> call, Response<SearchActor> response) {
                            SearchActor searchActor = response.body();
                            if (response.body() == null) {
                                JOptionPane.showMessageDialog(null, "You must enter actor name!", "ERROR", JOptionPane.PLAIN_MESSAGE);
                                return;
                            }

                            if (response.body().getTotal_results() != 0) {
                                JFrame frameSearch = new JFrame("Search Results Actors");
                                frameSearch.setVisible(true);
                                frameSearch.setDefaultCloseOperation(HIDE_ON_CLOSE);
                                frameSearch.setSize(1000, 500);
                                JList<ListSearchModel> listSearchRemote = new JList<>();
                                listSearchRemote.setModel(listModel3);


                                JScrollPane jScrollPaneSearch = new JScrollPane(listSearchRemote);
                                jScrollPaneSearch.setSize(600, 600);


                                JTextPane jTextPaneSearch = new JTextPane();
                                jTextPaneSearch.setSize(400, 300);
                                StyledDocument styledDocumentTextpaneSearch = jTextPaneSearch.getStyledDocument();
                                jTextPaneSearch.setEditable(false);
                                JScrollPane jScrollPaneTextPaneSearch = new JScrollPane(jTextPaneSearch);

                                JButton jButtonSeeMasterpiece = new JButton();
                                jButtonSeeMasterpiece.setText("See All Films");
                                jButtonSeeMasterpiece.setFont(new Font("Monaco", Font.BOLD, 20));

                                JLabel jLabelPostImage = new JLabel();


                                JPanel panelSearchCenter = new JPanel();
                                JPanel panelSearchNorth = new JPanel();

                                panelSearchCenter.add(jTextPaneSearch);

                                frameSearch.add(jScrollPaneSearch, BorderLayout.NORTH);
                                frameSearch.add(jTextPaneSearch, BorderLayout.CENTER);
                                frameSearch.add(jLabelPostImage, BorderLayout.WEST);
                                frameSearch.add(jButtonSeeMasterpiece, BorderLayout.SOUTH);

                                List<Actor.actor_detail> listActorDetail = searchActor.getResults();
                                // Add all movies which in results into list model to show.
                                for (int j = 0; j < searchActor.getResults().size(); j++) {
                                    listModel3.addElement(new ListSearchModel(listActorDetail.get(j).getName()));
                                    searchResultActors.add(new SearchResultActor(listActorDetail.get(j).getName(), listActorDetail.get(j).getId(), listActorDetail.get(j).getKnown_for()));
                                }

                                listSearchRemote.addListSelectionListener(new ListSelectionListener() {
                                    @Override
                                    public void valueChanged(ListSelectionEvent e) {

                                        // Get the value only when the left key is released.
                                        if (e.getValueIsAdjusting()) {
                                            return;
                                        }
                                        jTextPaneSearch.setText(null);
                                        jLabelPostImage.setText(null);
                                        jLabelPostImage.setIcon(null);
                                        System.out.println(listSearchRemote.getSelectedIndex() + " has been selected!");
//                                    search_result search_result_index = search_resultArrayList.get(list_search.getSelectedIndex());

                                        // Instantiate the movie info which selected.
                                        SearchResultActor searchResultActorSelect = searchResultActors.get(0);
                                        try {
                                            searchResultActorSelect = searchResultActors.get(listSearchRemote.getSelectedIndex());
                                        } catch (Exception exception) {
                                            exception.printStackTrace();
                                        }

                                        int searchResultActorId = searchResultActorSelect.iddeactor;
                                        String searchResultActorName = searchResultActorSelect.nomdeactor;
                                        List<Movie.movie_detail> searchResultMasterPeace = searchResultActorSelect.actorMasterPeaceList;

                                        // Use the movie id which we got to get movie info.
                                        Call<Actor.actor_detail> callActorDetail = apiInterface.get_actor_by_id(searchResultActorId, utils.API_KEY);

                                        callActorDetail.enqueue(new Callback<Actor.actor_detail>() {
                                            @Override
                                            public void onResponse(Call<Actor.actor_detail> call, Response<Actor.actor_detail> response) {
                                                Actor.actor_detail actor_detail = response.body();


                                                SimpleAttributeSet attributeSetTitle = new SimpleAttributeSet();
                                                StyleConstants.setBold(attributeSetTitle, true);
                                                StyleConstants.setFontSize(attributeSetTitle, 30);

                                                BufferedImage bufferedImageIcon = null;
                                                try {
                                                    bufferedImageIcon = ImageIO.read(new URL("https://image.tmdb.org/t/p/w200" + actor_detail.getProfile_path()));
                                                    ImageIcon imageIcon = new ImageIcon(bufferedImageIcon);
                                                    jLabelPostImage.setIcon(imageIcon);

                                                } catch (IOException ioException) {
//                                                ioException.printStackTrace();
                                                    jLabelPostImage.setText("This actor has no post image");
                                                }


//                                            Edit Search Text Pane


                                                try {
                                                    styledDocumentTextpaneSearch.insertString(styledDocumentTextpaneSearch.getLength(), actor_detail.getName(), attributeSetTitle);
                                                    styledDocumentTextpaneSearch.insertString(styledDocumentTextpaneSearch.getLength(), "\nBirthday: " + actor_detail.getBirthday() + " to " + actor_detail.getDeathday(), null);
                                                    styledDocumentTextpaneSearch.insertString(styledDocumentTextpaneSearch.getLength(), "\n\nBiography:  " + actor_detail.getBiography(), null);
                                                    styledDocumentTextpaneSearch.insertString(styledDocumentTextpaneSearch.getLength(), "\n\nPlace of birth: " + actor_detail.getPlace_of_birth(), null);
                                                    styledDocumentTextpaneSearch.insertString(styledDocumentTextpaneSearch.getLength(), "\nPopularity: " + actor_detail.getPopularity(), null);
                                                } catch (BadLocationException badLocationException) {
                                                    badLocationException.printStackTrace();
                                                }

                                            }

                                            @Override
                                            public void onFailure(Call<Actor.actor_detail> call, Throwable throwable) {
                                                JOptionPane.showMessageDialog(null, "You must enter actor name!", "ERROR", JOptionPane.PLAIN_MESSAGE);
                                            }
                                        });


                                    }

                                });

                                jButtonSeeMasterpiece.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        System.out.println("Film Name: " + searchResultActors.get(listSearchRemote.getSelectedIndex()).getActorMasterPeaceList().get(0).getTitle());
                                        // Instantiate new frame for search results.

                                        listModel4.removeAllElements();
                                        JFrame frameSearchMasterPeace = new JFrame("Search Results");
                                        frameSearchMasterPeace.setVisible(true);
                                        frameSearchMasterPeace.setDefaultCloseOperation(HIDE_ON_CLOSE);
                                        frameSearchMasterPeace.setSize(700, 500);
                                        JList<ListSearchModel> listSearchRemoteMasterPeace = new JList<>();
                                        listSearchRemoteMasterPeace.setModel(listModel4);


                                        JScrollPane jScrollPaneSearchMasterPeace = new JScrollPane(listSearchRemoteMasterPeace);
                                        jScrollPaneSearchMasterPeace.setSize(600, 600);


                                        JTextPane jTextPaneSearchMasterPeace = new JTextPane();
                                        jTextPaneSearchMasterPeace.setSize(400, 300);
                                        StyledDocument styledDocumentTextpaneSearchMasterPeace = jTextPaneSearchMasterPeace.getStyledDocument();
                                        jTextPaneSearchMasterPeace.setEditable(false);
                                        JScrollPane jScrollPaneTextPaneSearchMasterPeace = new JScrollPane(jTextPaneSearchMasterPeace);

                                        JButton jButtonAddToList = new JButton();
                                        jButtonAddToList.setText("Add To Library");
                                        jButtonAddToList.setFont(new Font("Monaco", Font.BOLD, 20));

                                        JLabel jLabelPostImageMasterPeace = new JLabel();


                                        JPanel panelSearchCenterMasterPeace = new JPanel();
                                        JPanel panelSearchNorthMasterPeace = new JPanel();

                                        panelSearchCenterMasterPeace.add(jTextPaneSearchMasterPeace);



                                        frameSearchMasterPeace.add(jScrollPaneSearchMasterPeace, BorderLayout.NORTH);
                                        frameSearchMasterPeace.add(jTextPaneSearchMasterPeace, BorderLayout.CENTER);
                                        frameSearchMasterPeace.add(jLabelPostImageMasterPeace, BorderLayout.WEST);
                                        frameSearchMasterPeace.add(jButtonAddToList, BorderLayout.SOUTH);

                                        List<SearchResultFilm> searchResultFilmArrayList = new ArrayList<>();

                                        Call<SearchMovieByPersonID> callSearchMovieByPersonID = apiInterface.get_movies_by_person_id(searchResultActors.get(listSearchRemote.getSelectedIndex()).iddeactor, utils.API_KEY);
                                        callSearchMovieByPersonID.enqueue(new Callback<SearchMovieByPersonID>() {
                                            @Override
                                            public void onResponse(Call<SearchMovieByPersonID> call, Response<SearchMovieByPersonID> response) {
                                                System.out.println(listSearchRemote.getSelectedIndex());

//                                        List<Movie.movie_detail> listMovieDetail = searchResultActors.get(listSearchRemote.getSelectedIndex()).getActorMasterPeaceList();

                                                List<Movie.movie_detail> listMovieDetailByPersonID = response.body().getCast();
                                                // Add all movies which in results into list model to show.
                                                for (int j = 0; j < listMovieDetailByPersonID.size(); j++) {
                                                    listModel4.addElement(new ListSearchModel(listMovieDetailByPersonID.get(j).getTitle()));
                                                    searchResultFilmArrayList.add(new SearchResultFilm(listMovieDetailByPersonID.get(j).getTitle(), listMovieDetailByPersonID.get(j).getId(), listMovieDetailByPersonID.get(j).getRelease_date()));
                                                    for (int a = 0; a < listFilmInList.toArray().length; a++) {
                                                        if (listFilmInList.get(a).getNomdefilm().equals(listMovieDetailByPersonID.get(j).getTitle())) {
                                                            listModel4.setElementAt(new ListSearchModel(listMovieDetailByPersonID.get(j).getTitle() + "  | Already In Library!"), j);
                                                        }
                                                    }
                                                }

                                                frameSearchMasterPeace.setTitle("Search Results " + listMovieDetailByPersonID.size() + " Films");

                                                // Add selection listener to catch if the movie is selected.
                                                listSearchRemoteMasterPeace.addListSelectionListener(new ListSelectionListener() {
                                                    @Override
                                                    public void valueChanged(ListSelectionEvent e) {

                                                        // Get the value only when the left key is released.
                                                        if (e.getValueIsAdjusting()) {
                                                            return;
                                                        }
                                                        jTextPaneSearchMasterPeace.setText(null);
                                                        jLabelPostImageMasterPeace.setText(null);
                                                        jLabelPostImageMasterPeace.setIcon(null);
                                                        System.out.println(listSearchRemoteMasterPeace.getSelectedIndex() + " has been selected!");
//                                    search_result search_result_index = search_resultArrayList.get(list_search.getSelectedIndex());

                                                        // Instantiate the movie info which selected.
                                                        SearchResultFilm searchResultFilmSelect = searchResultFilmArrayList.get(0);
                                                        try {
                                                            searchResultFilmSelect = searchResultFilmArrayList.get(listSearchRemoteMasterPeace.getSelectedIndex());
                                                        } catch (Exception exception) {
                                                            exception.printStackTrace();
                                                        }

                                                        int searchResultFilmId = searchResultFilmSelect.iddefilm;
                                                        String searchResultFilmNom = searchResultFilmSelect.nomdefilm;

                                                        // Use the movie id which we got to get movie info.
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
                                                                    jLabelPostImageMasterPeace.setIcon(imageIcon);

                                                                } catch (IOException ioException) {
//                                                ioException.printStackTrace();
                                                                    jLabelPostImageMasterPeace.setText("This movie has no post image");
                                                                }


//                                            Edit Search Text Pane


                                                                try {
                                                                    styledDocumentTextpaneSearchMasterPeace.insertString(styledDocumentTextpaneSearchMasterPeace.getLength(), movieDetail.getTitle(), attributeSetTitle);
                                                                    styledDocumentTextpaneSearchMasterPeace.insertString(styledDocumentTextpaneSearchMasterPeace.getLength(), "\nReleased date: " + movieDetail.getRelease_date() + "\nGenres: ", null);
                                                                    for (int k = 0; k < movieDetail.getGenres().size(); k++) {
                                                                        styledDocumentTextpaneSearchMasterPeace.insertString(styledDocumentTextpaneSearchMasterPeace.getLength(), movieDetail.getGenres().get(k).getName() + "  ", null);
                                                                    }
                                                                    styledDocumentTextpaneSearchMasterPeace.insertString(styledDocumentTextpaneSearchMasterPeace.getLength(), "\n\nAbstract:  " + movieDetail.getOverview(), null);
                                                                    styledDocumentTextpaneSearchMasterPeace.insertString(styledDocumentTextpaneSearchMasterPeace.getLength(), "\n\nRun time: " + movieDetail.getRuntime() + "min", null);
                                                                    styledDocumentTextpaneSearchMasterPeace.insertString(styledDocumentTextpaneSearchMasterPeace.getLength(), "\nMark: " + movieDetail.getVote_average() + "/10", null);
                                                                } catch (BadLocationException badLocationException) {
                                                                    badLocationException.printStackTrace();
                                                                }
                                                            }


                                                            @Override
                                                            public void onFailure(Call<Movie.movie_detail> call, Throwable throwable) {
                                                                JOptionPane.showMessageDialog(null, "You must enter film name!", "ERROR", JOptionPane.PLAIN_MESSAGE);
                                                            }
                                                        });
                                                    }
                                                });

                                                // Add action listener to catch if the button is clicked.
                                                jButtonAddToList.addActionListener(new ActionListener() {
                                                    @Override
                                                    public void actionPerformed(ActionEvent e) {

                                                        System.out.println("Film: " + searchResultFilmArrayList.get(listSearchRemoteMasterPeace.getSelectedIndex()).nomdefilm);
                                                        System.out.println("Film ID:" + searchResultFilmArrayList.get(listSearchRemoteMasterPeace.getSelectedIndex()).iddefilm);

                                                        JFrame frameAddFilmFromSearch = new JFrame("Add Film From Search");
                                                        frameAddFilmFromSearch.setVisible(true);
                                                        frameAddFilmFromSearch.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

                                                        JPanel jPanel1 = new JPanel();
                                                        JPanel jPanel3 = new JPanel();
                                                        JPanel jPanel2 = new JPanel();


                                                        frameAddFilmFromSearch.setSize(500, 200);

                                                        JLabel label1AddFilmToTxtFrameSearch = new JLabel("Name: ");
                                                        JTextField textFieldFilmNameAddFilmToTxtFrameSearch = new JTextField(10);
                                                        textFieldFilmNameAddFilmToTxtFrameSearch.setText(searchResultFilmArrayList.get(listSearchRemoteMasterPeace.getSelectedIndex()).nomdefilm);
                                                        JLabel label2AddFilmToTxtFrameSearch = new JLabel("ID:   ");
                                                        JTextField textFieldFilmIdAddFilmToTxtFrameSearch = new JTextField(10);
                                                        textFieldFilmIdAddFilmToTxtFrameSearch.setText(String.valueOf(searchResultFilmArrayList.get(listSearchRemoteMasterPeace.getSelectedIndex()).iddefilm));
                                                        textFieldFilmNameAddFilmToTxtFrameSearch.setSize(300, -1);


                                                        jPanel1.add(label1AddFilmToTxtFrameSearch);
                                                        jPanel1.add(textFieldFilmNameAddFilmToTxtFrameSearch);
                                                        jPanel2.add(label2AddFilmToTxtFrameSearch);
                                                        jPanel2.add(textFieldFilmIdAddFilmToTxtFrameSearch);

                                                        textFieldFilmNameAddFilmToTxtFrameSearch.setEditable(true);
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


                                                                try {

                                                                    listModel.addElement(new ListModelElement(textFieldFilmNameAddFilmToTxtFrameSearch.getText(), comboBoxFilmModeAddFilmToTxtFrameSearch.getSelectedItem().toString()));
                                                                    listFilmInList.add(new LesFilmsInList(textFieldFilmNameAddFilmToTxtFrameSearch.getText(), comboBoxFilmModeAddFilmToTxtFrameSearch.getSelectedItem().toString(), Integer.valueOf(textFieldFilmIdAddFilmToTxtFrameSearch.getText()), searchResultFilmArrayList.get(listSearchRemoteMasterPeace.getSelectedIndex()).release_date));
                                                                    frameAddFilmFromSearch.setVisible(false);

                                                                    FileWriter fw = new FileWriter(absoultePath, true);
                                                                    fw.write("\n" + textFieldFilmNameAddFilmToTxtFrameSearch.getText() + "," + comboBoxFilmModeAddFilmToTxtFrameSearch.getSelectedItem().toString() + "," + textFieldFilmIdAddFilmToTxtFrameSearch.getText() + "," + searchResultFilmArrayList.get(listSearchRemoteMasterPeace.getSelectedIndex()).release_date);
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
                                            public void onFailure(Call<SearchMovieByPersonID> call, Throwable t) {

                                            }
                                        });


                                    }
                                });

                            }
                        }

                        @Override
                        public void onFailure(Call<SearchActor> call, Throwable t) {

                        }
                    });
                }

            }
        });
//
//        textField1.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//            }
//        });

        // Add action listener to catch if the button is clicked.
        buttonDelFilm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Get selected item's index
                int indexDelFilm = listLesFilm.getSelectedIndex();
                System.out.println("Delete Film Index:" + indexDelFilm);

                // Get delete film's name.
                String stringDelFilmNom = new String(listFilmInList.get(indexDelFilm).getNomdefilm());
                // Delete Last Space in String
                System.out.println("Delete Film Name: " + stringDelFilmNom);

                // Get Film names from TXT file, search the movie which we want to delete and then delete it
                // Turn TXT to ArrayList
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


                // Check if the movie film is in array list, if yes then delete.
                for (int i = 0; i < listFilmInTxt.size(); i++) {
//                    System.out.println(listFilmInTxt.get(i));
                    if (listFilmInTxt.get(i).equals(stringDelFilmNom)) {
                        System.out.println(i);
                        System.out.println(listFilmInTxt.get(i));
                        if (indexDelFilm != -1) {
                            listFilmInList.remove(indexDelFilm);
                            listModel.remove(indexDelFilm);
                            System.out.println("yes");
                            try {

                                // Empty txt file
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

                            // Rewrite the processed form into the TXT file.
                            for (int j = 0; j < listFilmInTxt.size(); j++) {

                                // Except for the last line, add "\n" to wrap line.
                                if (j < listFilmInTxt.size() - 1) {
                                    try {
                                        FileWriter fw = new FileWriter(absoultePath, true);
                                        fw.write(listFilmInTxt.get(j) + "," + listModeInTxt.get(j) + "," + listFilmIdInTxt.get(j) + "," + listYearInTxt.get(j) + "\n");
                                        fw.close();
                                    } catch (IOException ioException) {
                                        ioException.printStackTrace();
                                    }
                                    // For the last line.
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
//        buttonSearchInLibrary.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String stringSearchInLibrary = textFieldSearchInLibrary.getText();
//                List<ListModelElement> listSearchInLibrary = new ArrayList<>();
//                for(int i=0;i<listFilmInList.toArray().length;i++){
//                    if(stringSearchInLibrary.toLowerCase().contains(listFilmInList.get(i).getNomdefilm().toLowerCase())){
//                        listSearchInLibrary.add(new ListModelElement(listFilmInList.get(i).getNomdefilm(), listFilmInList.get(i).getModel()));
//                    }
//                }
//                listModel.removeAllElements();
//                for(int i=0;i<listSearchInLibrary.toArray().length;i++){
//                    listModel.addElement(new ListModelElement(listSearchInLibrary.get(i).getNom(), listSearchInLibrary.get(i).getModel()));
//                }
//            }
//        });
//        textFieldSearchInLibrary.addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyTyped(KeyEvent e) {
//                super.keyTyped(e);
//                String stringSearchInLibrary = textFieldSearchInLibrary.getText();
//                List<ListModelElement> listSearchInLibrary = new ArrayList<>();
//                listModel.removeAllElements();
//                for(int i=0;i<listFilmInList.toArray().length;i++){
//                    if(stringSearchInLibrary.toLowerCase().contains(listFilmInList.get(i).getNomdefilm().toLowerCase())){
//                        listSearchInLibrary.add(new ListModelElement(listFilmInList.get(i).getNomdefilm(), listFilmInList.get(i).getModel()));
//                    }
//                }
//                if(textFieldSearchInLibrary.getText().equals("")){
//                    for(int i=0; i<listFilmInList.toArray().length; i++){
//                        listModel.addElement(new ListModelElement(listFilmInList.get(i).getNomdefilm(), listFilmInList.get(i).getModel()));
//                    }
//                } else {
//                    for(int i=0;i<listSearchInLibrary.toArray().length;i++){
//                        listModel.addElement(new ListModelElement(listSearchInLibrary.get(i).getNom(), listSearchInLibrary.get(i).getModel()));
//                    }
//                }
//            }
//        });
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

    public class ListSearchModel {
        String nomdefilm;


        public ListSearchModel(String nomdefilm) {
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


    public class SearchResultFilm {
        String nomdefilm;
        int iddefilm;
        String release_date;

        public SearchResultFilm(String nomdefilm, int iddefilm, String release_date) {
            this.nomdefilm = nomdefilm;
            this.iddefilm = iddefilm;
            this.release_date = release_date;
        }

        public String getRelease_date() {
            return release_date;
        }

        public void setRelease_date(String release_date) {
            this.release_date = release_date;
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

    public class ActorMasterPeace {
        String nomdefilm;
        int iddefilm;

        public ActorMasterPeace(String nomdefilm, int iddefilm) {
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
    }

    public class SearchResultActor {
        String nomdeactor;
        int iddeactor;
        List<Movie.movie_detail> actorMasterPeaceList;

        public SearchResultActor(String nomdeactor, int iddeactor, List<Movie.movie_detail> actorMasterPeaceList) {
            this.nomdeactor = nomdeactor;
            this.iddeactor = iddeactor;
            this.actorMasterPeaceList = actorMasterPeaceList;
        }

        public String getNomdeactor() {
            return nomdeactor;
        }

        public void setNomdeactor(String nomdeactor) {
            this.nomdeactor = nomdeactor;
        }

        public int getIddeactor() {
            return iddeactor;
        }

        public void setIddeactor(int iddeactor) {
            this.iddeactor = iddeactor;
        }

        public List<Movie.movie_detail> getActorMasterPeaceList() {
            return actorMasterPeaceList;
        }

        public void setActorMasterPeaceList(List<Movie.movie_detail> actorMasterPeaceList) {
            this.actorMasterPeaceList = actorMasterPeaceList;
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
