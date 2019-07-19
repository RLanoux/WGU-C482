package InventorySystem.View_Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import InventorySystem.Model.Inhouse;
import InventorySystem.Model.Inventory;
import static InventorySystem.Model.Inventory.addPart;
import InventorySystem.Model.Outsourced;
import InventorySystem.Model.Part;

/**
 * FXML Controller class
 *
 * @author Raymond Lanoux
 */
public class AddPartScreenController implements Initializable {
    
    @FXML
    private AnchorPane scrAddPart;

    @FXML
    private RadioButton rbAddPartInHouse;

    @FXML
    private ToggleGroup tgSource;

    @FXML
    private RadioButton rbAddPartOutsourced;

    @FXML
    private Label lblAddPartDyn;

    @FXML
    private TextField txtAddPartID;

    @FXML
    private TextField txtAddPartName;

    @FXML
    private TextField txtAddPartInv;

    @FXML
    private TextField txtAddPartPrice;

    @FXML
    private TextField txtAddPartMax;

    @FXML
    private TextField txtAddPartDyn;

    @FXML
    private TextField txtAddPartMin;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnCancel;
    
    @FXML
    private boolean bOutSourced;
    
    @FXML
    private String errPartValid = new String();
    
    @FXML
    private String errPartField = new String();
    
    @FXML
    private int partID;

    @FXML
    void handleAddPartCancel(ActionEvent event) throws IOException {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exit to Main Screen");
            alert.setHeaderText("Are you sure you want to cancel?");
            alert.setContentText("Press OK to exit to the Main screen. \nPress Cancel to stay on this screen.");
            alert.showAndWait();
            
            if (alert.getResult() == ButtonType.OK) {
                Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene scene = new Scene(root);        
                Stage winMainScreen = (Stage)((Node)event.getSource()).getScene().getWindow();        
                winMainScreen.setTitle("Inventory Management System");
                winMainScreen.setScene(scene);
                winMainScreen.show();
            }
            else {
                alert.close();
            }
        }
        catch (IOException e) {}
    }

    @FXML
    void handleAddPartSave(ActionEvent event) throws IOException {
        String name = txtAddPartName.getText();
        String inStock = txtAddPartInv.getText();
        String price = txtAddPartPrice.getText();
        String min = txtAddPartMin.getText();
        String max = txtAddPartMax.getText();
        String partDyn = txtAddPartDyn.getText();
        
        errPartField = Part.getEmptyFields(name, inStock, price, max, min, partDyn, errPartField);
        if (errPartField.length() > 0){
            Alert alert = new Alert(Alert.AlertType.WARNING);
           alert.setTitle("Part Addition Warning");
           alert.setHeaderText("The part was NOT added!");
           alert.setContentText(errPartField);
           alert.showAndWait();
           errPartField = "";
        }
        else {
            try {
                errPartValid = Part.getPartValidation( 
                       Integer.parseInt(inStock), 
                       Double.parseDouble(price), 
                       Integer.parseInt(max), 
                       Integer.parseInt(min), 
                       errPartValid);
                if (errPartValid.length() > 0) {
                   Alert alert = new Alert(Alert.AlertType.WARNING);
                   alert.setTitle("Part Addition Warning");
                   alert.setHeaderText("The part was NOT added!");
                   alert.setContentText(errPartValid);
                   alert.showAndWait();
                   errPartValid = "";
                }
                else {
                    if (bOutSourced == true) {
                    Outsourced oSP = new Outsourced();
                    oSP.setPartID(partID);
                    oSP.setName(name);
                    oSP.setInStock(Integer.parseInt(inStock));
                    oSP.setPrice(Double.parseDouble(price));
                    oSP.setMax(Integer.parseInt(max));
                    oSP.setMin(Integer.parseInt(min));
                    oSP.setCompanyName(partDyn);
                    addPart(oSP);
                    }
                    else {
                    Inhouse iHP = new Inhouse ();
                    iHP.setPartID(partID);
                    iHP.setName(name);
                    iHP.setInStock(Integer.parseInt(inStock));
                    iHP.setPrice(Double.parseDouble(price));
                    iHP.setMax(Integer.parseInt(max));
                    iHP.setMin(Integer.parseInt(min));
                    iHP.setMachineID(Integer.parseInt(partDyn));
                    addPart(iHP);
                    }
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    Stage winMainScreen = (Stage)((Node)event.getSource()).getScene().getWindow();
                    winMainScreen.setTitle("Inventory Management System");
                    winMainScreen.setScene(scene);
                    winMainScreen.show();
                }
           }
           catch (IOException e) {}
        }
    }
    
    @FXML
    void setTGSource(ActionEvent event) {
        if (tgSource.getSelectedToggle() == rbAddPartInHouse) {
            lblAddPartDyn.setText("Machine ID");
            txtAddPartDyn.setPromptText("Machine ID");
        }
        else {
            lblAddPartDyn.setText("Company Name");
            txtAddPartDyn.setPromptText("Company Name");
            bOutSourced = true;
        }
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partID = Inventory.getPartIDCount();
        txtAddPartID.setText("Auto Gen: " + partID);
    }
}
