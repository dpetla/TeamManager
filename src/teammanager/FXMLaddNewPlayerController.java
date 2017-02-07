package teammanager;

import customjavafxcontrols.NumberOnlyTextField;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author brunopetla and dpetla
 */
public class FXMLaddNewPlayerController implements Initializable {

    @FXML
    private Button saveNewPlayer_btn;
    @FXML
    private NumberOnlyTextField faults_addTf;
    @FXML
    private NumberOnlyTextField yellowCards_addTf;
    @FXML
    private NumberOnlyTextField redCards_addTf;
    @FXML
    private Button chooseImage_btn;
    @FXML
    private NumberOnlyTextField jersey_addTf;
    @FXML
    private NumberOnlyTextField age_addTf;
    @FXML
    private TextField name_addTf;
    @FXML
    private NumberOnlyTextField height_addTf;
    @FXML
    private NumberOnlyTextField weight_addTf;
    @FXML
    private NumberOnlyTextField assistances_addTf;
    @FXML
    private NumberOnlyTextField tackles_addTf;
    @FXML
    private NumberOnlyTextField wrongPasses_addTf;
    @FXML
    private NumberOnlyTextField gamesPlayed_addTf;
    @FXML
    private NumberOnlyTextField shotsToGoal_addTf;
    @FXML
    private NumberOnlyTextField goals_addTf;
    @FXML
    private Button cancelAdd_btn;
    @FXML
    private ImageView playerPreview_img;
    @FXML
    private ComboBox<Position> position_addCBox;

    public FXMLDocumentController mainWindow;
    Image image;
    AnchorPane scenePane;
    File loadFile;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // populate combo box with player positions' options
        position_addCBox.getItems().setAll(Position.values());

        // loads a preview image of the playes
        chooseImage_btn.setOnAction(e -> {

            FileChooser choosy = new FileChooser();
            choosy.setInitialDirectory(new File("./imgs"));
            loadFile = choosy.showOpenDialog(mainWindow.newWindow);

            if (loadFile == null) return;
            
            try {
                image = SwingFXUtils.toFXImage(ImageIO.read(loadFile), null);
                playerPreview_img.setImage(image);

            } catch (IOException ex) {
                System.err.println("IO Error attempting to load image: " + loadFile);
            }
        });

        // save all new player data
        saveNewPlayer_btn.setOnAction(e -> {

            // string to hold all error messages
            String errorContent = "";

            try {

                // get name
                String name = name_addTf.getText();

                // Check if theis at least two name in the textfield
                if (name.isEmpty() || name.indexOf(" ") <= 0) 
                    errorContent += "- Enter first and last name\n";

                // image is null add error message to the error string
                if (image == null)
                    errorContent += "- Player's image not selected\n";

                // 
                if (position_addCBox.getValue() == null) {
                    errorContent += "- Player's position not selected\n";
                    throw new NullPointerException();
                }

                // get first and last name
                String firstName = name.substring(0, name.indexOf(" "));
                String lastName = name.substring(name.indexOf(" ") + 1);

                // get player's information
                int n = Integer.parseInt(jersey_addTf.getText());
                int w = Integer.parseInt(weight_addTf.getText());
                int h = Integer.parseInt(height_addTf.getText());
                int a = Integer.parseInt(age_addTf.getText());

                // get image path
                String path = loadFile.toString().substring(loadFile.toString().lastIndexOf("/") + 1);

                // get player's position
                Position pos = position_addCBox.getValue();

                // get all stats and create stats object
                int gamesPlayed = Integer.parseInt(gamesPlayed_addTf.getText());
                int goals = Integer.parseInt(goals_addTf.getText());
                int assist = Integer.parseInt(assistances_addTf.getText());
                int shotToGoal = Integer.parseInt(shotsToGoal_addTf.getText());
                int tackles = Integer.parseInt(tackles_addTf.getText());
                int wrongPass = Integer.parseInt(wrongPasses_addTf.getText());
                int faults = Integer.parseInt(faults_addTf.getText());
                int yellowCard = Integer.parseInt(yellowCards_addTf.getText());
                int redCard = Integer.parseInt(redCards_addTf.getText());

                // create statistics instance
                Statistics st = new Statistics(gamesPlayed, goals, assist, 
                        shotToGoal, tackles, wrongPass, faults, yellowCard,
                        redCard);

                // create new player
                Player p = new Player(new Name(firstName, lastName), n, w, h, a, image, path, pos, st);

                // add player to players collection
                mainWindow.players.add(p);

                // sort collection with new player
                Collections.sort(mainWindow.players);

                // update listview and select new player in the listview
                mainWindow.updatePlayersList();
                mainWindow.selectPlayer(mainWindow.players.get(mainWindow.players.indexOf(p)));

                // file has changes to save
                mainWindow.isEdited = true;

                //clear all fields
                clearFields();
                path = "";

            } catch (Exception ex) {

                // alert user of all error message and any extra runtime exception
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error, please re-check this items below:");
                alert.setContentText(errorContent + "\n" + ex.getMessage());
                alert.showAndWait();
                return;
            }

            //close window
            mainWindow.newWindow.close();
        }); // end of save new player btn

        //code for canceling the add player option
        cancelAdd_btn.setOnAction(e -> {

            //clear all fields
            clearFields();

            //close window without save any data
            mainWindow.newWindow.close();
        });
    }

    /**
     * Clear all field of the add new player page.
     */
    private void clearFields() {

        //clear header fields
        name_addTf.clear();
        jersey_addTf.setText("0");
        weight_addTf.setText("0");
        height_addTf.setText("0");
        age_addTf.setText("0");
        playerPreview_img.setImage(null);
        position_addCBox.setValue(null);

        // clear stats fields
        gamesPlayed_addTf.setText("0");
        goals_addTf.setText("0");
        assistances_addTf.setText("0");
        shotsToGoal_addTf.setText("0");
        tackles_addTf.setText("0");
        wrongPasses_addTf.setText("0");
        faults_addTf.setText("0");
        yellowCards_addTf.setText("0");
        redCards_addTf.setText("0");
    }
}
