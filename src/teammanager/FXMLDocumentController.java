package teammanager;

import customjavafxcontrols.NumberOnlyTextField;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author dpetla and brunopetla
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private ImageView Logo_img, Player_img;
    @FXML
    private Button Cancel_btn, Save_btn;
    @FXML
    private Label PNum_label, PName_label, PPosition_label, PHeight_label, PAge_label, PWeight_label;
    @FXML
    private ListView players_list;
    @FXML
    private NumberOnlyTextField gamesPlayed_tf, shotsToGoal_tf, goals_tf;
    @FXML
    private NumberOnlyTextField assistances_tf, tackles_tf, wrongPasses_tf;
    @FXML
    private NumberOnlyTextField faults_tf, yellowCards_tf, redCards_tf;
    @FXML
    private TextField Search_txtField;
    @FXML
    private MenuItem close_btn;
    @FXML
    private TextField editName_txt;
    @FXML
    private TextField editNum_txt;
    @FXML
    private TextField editHeight_txt;
    @FXML
    private TextField editWeight_txt;
    @FXML
    private TextField editAge_txt;
    @FXML
    private ComboBox<Position> position_cb;
    @FXML
    private ComboBox<Position> editPosition_cb;

    final Stage newWindow = new Stage();

    //Name of the file to save system info 
    String sysFile = "Players.dat";

    //reference to hold a player instance
    Player player;

    // track if any changes were made. 
    //If true when clicking the close button there is option to save the changes made
    boolean isEdited = false;

    //collection to hold all players
    ArrayList<Player> players = new ArrayList<>();
    ArrayList<Player> filteredPlayers = new ArrayList<>();

    // get file to set up team logo
    File path = new File("imgs/CA_Paranaense.png");
    Image logo = new Image(path.toURI().toString());

    /**
     * Routines that run when program start.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // set team logo on main page
        Logo_img.setImage(logo);

        try {
            // Load file Players.dat with all players data
            loadFile(sysFile);
        } catch (Exception ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // update listview with Players.dat
        updatePlayersList();

        //select first player of the list view to display on the screen
        selectPlayer(players.get(0));

        /* disable text fields related to player information.
        They will be editable only when clicking the edit player button. */
        playerEditable(false);

        // load available positions to combobox used to filter listview
        position_cb.getItems().setAll(Position.values());
        position_cb.getItems().get(0);

        try {

            newWindow.initModality(Modality.APPLICATION_MODAL);
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("FXMLaddNewPlayerController.fxml")
            );

            Scene scene = new Scene(loader.load());
            newWindow.setScene(scene);

            FXMLaddNewPlayerController controller = loader.<FXMLaddNewPlayerController>getController();
            controller.mainWindow = this;

        } catch (Exception e) {

            System.err.println(e.getMessage());

        }

        // ### Controllers. Used lambda expression instead of annonyomous inner class
        /* #Listview controller. 
            When user clicks on one item in the listview that item/player is 
            displayed on screen. 
         */
        players_list.setOnMouseClicked(e -> {
            int index = players_list.getSelectionModel().getSelectedIndex();
            Player selected = (Player) players_list.getItems().get(index);
            selectPlayer(selected);
        });

        /* #Search bar key released controller.
            If there is text in the search bar it compares the user input against
            the name of all the players in the program.
         */
        Search_txtField.setOnKeyReleased(e -> {

            //temporary list used during search
            ObservableList<Player> list = FXCollections.observableArrayList();
            String target = null;
            target = Search_txtField.getText().toUpperCase();

            if (target == null || target.isEmpty()) {

                // load players full collection
                updatePlayersList();
                selectPlayer((Player) players_list.getItems().get(0));

            } else {

                // every time search bar is used clear search combobox selection.
                // they are not to work togehter
                position_cb.getSelectionModel().clearSelection();

                // empty listview to add just the results of the search
                players_list.getItems().clear();

                // iterate through all players, not just the ones currently in 
                // the listview (ex. previsous search)
                for (int i = 0; i < players.size(); i++) {

                    Player p = players.get(i);

                    // if user input is found in the player's full name, add to
                    // filtered list
                    if (p.toString().contains(target)) {
                        list.add(p);
                    }
                }

                // sort list and add to the listview
                Collections.sort(list);
                players_list.getItems().addAll(list);

                // if none players 
                if (!list.isEmpty()) {
                    selectPlayer((Player) players_list.getItems().get(0));
                }
            }
        });

        // position combobox action controller
        position_cb.setOnAction(e -> {

            // temporary list used only for search purposes
            ObservableList<Player> list = FXCollections.observableArrayList();

            // every time search combobox is used clear search bar text.
            // they are not to work togehter
            Search_txtField.clear();

            // empty listview to add just the results of the search
            players_list.getItems().clear();

            // iterate through all players, not just the ones currently in the listview (ex. previsous search)
            for (int i = 0; i < players.size(); i++) {
                Player p = players.get(i);

                // when position match add it to the temporary list
                if (p.getPosition().equals(position_cb.getValue())) {
                    list.add(p);
                }
            }

            // sort temporary list, add it to the listview and select first in the list
            Collections.sort(list);
            players_list.getItems().addAll(list);
            if (!players_list.getItems().isEmpty()) {
                selectPlayer((Player) players_list.getItems().get(0));
            }

        });

        /* #Close button action controller 
            Let the user close program. Checks if any changes were made to the program
            (using variable is Editable) and if changes were made prompt the user to save.
         */
        close_btn.setOnAction(e -> {
            if (isEdited) {

                // check if user wants to save before closing
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Save project");
                alert.setHeaderText("Do you want to save the changes made to this project?");
                alert.setContentText("Your changes will be lost if you don’t save them.");
                Optional<ButtonType> result = alert.showAndWait();

                //click ok to save
                if (result.get() == ButtonType.OK) {
                    saveProject();
                }
            }

            //exit program
            System.exit(0);
        });
    }

    /**
     * Read the default file used in the program and converts every line to a
     * Player object and add them to the players collection.
     *
     * @param importFile
     * @throws Exception
     */
    public void loadFile(String importFile) throws Exception {

        // reader file Players.dat from root
        try (BufferedReader input = new BufferedReader(new FileReader(
                new File(importFile)))) {

            while (true) {

                //get each line from the file to convert to a Players object
                String nextLine = input.readLine();
                if (nextLine != null) {     //run until there is no more characters

                    // create players instances for each file line
                    player = Player.valueOf(nextLine);

                    // add player to the collection
                    players.add(player);

                } else {

                    // stop at the end of the file
                    break;
                }
            }
        }
    }

    /**
     * Displays all information on the main screen of the program of the player
     * passed to the method. It also update the selection in the listview.
     *
     * @param p
     */
    public void selectPlayer(Player p) {

        // select player p in the listview
        players_list.getSelectionModel().select(p);

        // set player header info
        PNum_label.setText(p.getNum() + "");
        PName_label.setText(p.getName());
        Player_img.setImage(p.getImage());
        PPosition_label.setText(p.getPosition() + "");
        PHeight_label.setText(p.getHeight() + " cm");
        PWeight_label.setText(p.getWeight() + " kg");
        PAge_label.setText(p.getAge() + " yrs");

        // player stats
        gamesPlayed_tf.setText(p.getStats().getGamesPlayed() + "");
        shotsToGoal_tf.setText(p.getStats().getShotToGoal() + "");
        goals_tf.setText(p.getStats().getGoals() + "");
        assistances_tf.setText(p.getStats().getAssist() + "");
        tackles_tf.setText(p.getStats().getTackles() + "");
        wrongPasses_tf.setText(p.getStats().getWrongPass() + "");
        faults_tf.setText(p.getStats().getFaults() + "");
        yellowCards_tf.setText(p.getStats().getYellowCard() + "");
        redCards_tf.setText(p.getStats().getRedCard() + "");

    }

    /**
     * Updates players_list with current collection of players.
     */
    public void updatePlayersList() {

        // if there are items in the listview, clear it
        if (!players_list.getItems().isEmpty()) {

            players_list.getItems().clear();

        }

        // sort players alphabetically
        Collections.sort(players);

        // iterate throgh players collection and add all players to the listview
        Iterator<Player> it = players.iterator();
        while (it.hasNext()) {

            players_list.getItems().add(it.next());

        }
    }

    /**
     * Overwrites an existing file create a new file with name Players.dat.
     *
     * @param fileName
     * @throws IOException
     */
    public void saveToFile(String fileName) throws IOException {

        try (PrintWriter outputStream = new PrintWriter(new FileWriter(fileName))) {

            for (Player p : players) {

                // outputing players header info
                outputStream.print(p.getNum() + ",");
                outputStream.print(p.getFirstName() + ",");
                outputStream.print(p.getLastName() + ",");
                int pos = 0;

                switch (p.getPosition()) {
                    case DEFENSE:
                        pos = 1;
                        break;
                    case GOALKEEPER:
                        pos = 2;
                        break;
                    case MIDFIELDER:
                        pos = 3;
                        break;
                    case STRIKER:
                        pos = 4;
                        break;
                }

                outputStream.print(pos + ",");
                outputStream.print(p.getWeight() + ",");
                outputStream.print(p.getHeight() + ",");
                outputStream.print(p.getAge() + ",");
                outputStream.print(p.getPath() + ",");

                // outputing players stats
                outputStream.print(p.getStats().getGamesPlayed() + ",");
                outputStream.print(p.getStats().getGoals() + ",");
                outputStream.print(p.getStats().getAssist() + ",");
                outputStream.print(p.getStats().getShotToGoal() + ",");
                outputStream.print(p.getStats().getTackles() + ",");
                outputStream.print(p.getStats().getWrongPass() + ",");
                outputStream.print(p.getStats().getFaults() + ",");
                outputStream.print(p.getStats().getYellowCard() + ",");
                outputStream.println(p.getStats().getRedCard());

            }

        }

        // file does not have changes to save
        isEdited = false;
    }

    @FXML
    public void saveProject() {

        //save current data to system data file
        try {
            saveToFile(sysFile);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void DeleteButtonEvent(ActionEvent event) {

        // find player currently being displayed
        int index = players_list.getSelectionModel().getSelectedIndex();
        Player selected = players.get(index);

        // pop-up to confirm delete action
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete Player");
        alert.setHeaderText("Removing " + selected.getName());
        alert.setContentText("Do you want to delete this player?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {

            // remove from collection
            players.remove(selected);

            // set reference to null
            System.out.println("Player deleted: " + selected.getName());
            selected = null;
            // file has changes to save
            isEdited = true;

            // update listview to remove deleted player from it
            updatePlayersList();

            // select first player of the listview
            selectPlayer(players.get(0));

        } else {
            System.out.println("player not deleted.");
        }

    }

    // Save as option
    @FXML
    private void ExportButtonEvent(ActionEvent event) {

        // variable for the name of the file to be saved
        String exportName = null;

        //get the name of the file
        TextInputDialog userData = new TextInputDialog();
        userData.setTitle("Export file");
        userData.setHeaderText("Exporting to the root folder.");
        userData.setContentText("Enter the name of file:");
        Optional<String> result = userData.showAndWait();

        if (result.isPresent()) {

            exportName = userData.getResult();
            try {

                //save file with custom name
                saveToFile(exportName);

            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }

            System.out.println("Export done (" + exportName + ").");

        } else {
            System.out.println("Export aborted.");
        }

    }

    @FXML
    private void editPlayerButtonEvent(ActionEvent event) {

        // load available positions to combobox used to edit player
        editPosition_cb.getItems().setAll(Position.values());

        // make stats fields playerEditable
        // make save and cancel buttons clickable
        playerEditable(true);

    }

    @FXML
    private void saveEditPlayer_btn(ActionEvent event) {

        // get player's header information
        String name = editName_txt.getText();
        Position pos = editPosition_cb.getValue();
        int n = Integer.parseInt(editNum_txt.getText());
        int w = Integer.parseInt(editWeight_txt.getText());
        int h = Integer.parseInt(editHeight_txt.getText());
        int a = Integer.parseInt(editAge_txt.getText());

        // set player's header information
        int index = players_list.getSelectionModel().getSelectedIndex();
        Player p = players.get(index);

        // set name only if there is 2 words (first and last name) in the textfield
        if (name.indexOf(" ") > 0 && name.length() > 3) {
            p.setName(name);
        }

        p.setPosition(pos);
        p.setNum(n);
        p.setHeight(h);
        p.setWeight(w);
        p.setAge(a);

        // parse user input to proper format
        int sGamesPlayed = Integer.parseInt(gamesPlayed_tf.getText());
        int sShotToGoal = Integer.parseInt(shotsToGoal_tf.getText());
        int sGoal = Integer.parseInt(goals_tf.getText());
        int sWrongPass = Integer.parseInt(wrongPasses_tf.getText());
        int sAssist = Integer.parseInt(assistances_tf.getText());
        int sTackles = Integer.parseInt(tackles_tf.getText());
        int sFault = Integer.parseInt(faults_tf.getText());
        int sYellowCard = Integer.parseInt(yellowCards_tf.getText());
        int sRedCard = Integer.parseInt(redCards_tf.getText());

        // set stat variables
        p.getStats().setGamesPlayed(sGamesPlayed);
        p.getStats().setShotToGoal(sShotToGoal);
        p.getStats().setGoals(sGoal);
        p.getStats().setWrongPass(sWrongPass);
        p.getStats().setAssist(sAssist);
        p.getStats().setTackles(sTackles);
        p.getStats().setFaults(sFault);
        p.getStats().setYellowCard(sYellowCard);
        p.getStats().setRedCard(sRedCard);

        // update collection and listview
        updatePlayersList();
        playerEditable(false);
        selectPlayer(p);

        // file has changes to save
        isEdited = true;

    }

    @FXML
    private void CancelEditStats_btn(ActionEvent event) {

        //make stats fields not playerEditable
        // make save and cancel buttons not clickable
        playerEditable(false);

    }

    public void playerEditable(Boolean bool) {

        // make header textfields not visible and open input text box
        editName_txt.setVisible(bool);
        PName_label.setVisible(!bool);
        editNum_txt.setVisible(bool);
        PNum_label.setVisible(!bool);
        editPosition_cb.setVisible(bool);
        PPosition_label.setVisible(!bool);
        editHeight_txt.setVisible(bool);
        PHeight_label.setVisible(!bool);
        editWeight_txt.setVisible(bool);
        PWeight_label.setVisible(!bool);
        editAge_txt.setVisible(bool);
        PAge_label.setVisible(!bool);

        //make stats fields not playerEditable
        gamesPlayed_tf.setEditable(bool);
        shotsToGoal_tf.setEditable(bool);
        goals_tf.setEditable(bool);
        assistances_tf.setEditable(bool);
        tackles_tf.setEditable(bool);
        wrongPasses_tf.setEditable(bool);
        faults_tf.setEditable(bool);
        yellowCards_tf.setEditable(bool);
        redCards_tf.setEditable(bool);

        //sets the color of the textfields to red when they are playerEditable
        String redBorder = "";
        if (bool) {
            redBorder = "-fx-border-color: red";
            Player p = (Player) players_list.getSelectionModel().getSelectedItem();

            //Populate with current value
            editName_txt.setText(p.getName());
            editNum_txt.setText(p.getNum() + "");
            editPosition_cb.setValue(p.getPosition());
            editHeight_txt.setText(p.getHeight() + "");
            editWeight_txt.setText(p.getWeight() + "");
            editAge_txt.setText(p.getAge() + "");

        }

        gamesPlayed_tf.setStyle(redBorder);
        gamesPlayed_tf.setStyle(redBorder);
        shotsToGoal_tf.setStyle(redBorder);
        goals_tf.setStyle(redBorder);
        assistances_tf.setStyle(redBorder);
        tackles_tf.setStyle(redBorder);
        wrongPasses_tf.setStyle(redBorder);
        faults_tf.setStyle(redBorder);
        yellowCards_tf.setStyle(redBorder);
        redCards_tf.setStyle(redBorder);

        // make save and cancel buttons not clickable
        Save_btn.setDisable(!bool);
        Cancel_btn.setDisable(!bool);
    }

    @FXML
    void addNewPlayerWindow() {
        newWindow.show();
    }

}
