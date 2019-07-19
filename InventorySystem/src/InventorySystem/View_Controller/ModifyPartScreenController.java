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
import static InventorySystem.Model.Inventory.updatePart;
import InventorySystem.Model.Outsourced;
import InventorySystem.Model.Part;
import static InventorySystem.View_Controller.MainScreenController.getSelectedPart;
import static InventorySystem.View_Controller.MainScreenController.getSelectedPartIndex;

/**
 * FXML Controller class
 *
 * @author Raymond Lanoux
 */
public class ModifyPartScreenController implements Initializable {

    @FXML
    private AnchorPane scrModifyPart;

    @FXML
    private RadioButton rbInHouse;

    @FXML
    private ToggleGroup tgSource;

    @FXML
    private RadioButton rbOutsourced;

    @FXML
    private Label lblModifyPartDyn;

    @FXML
    private TextField txtModifyPartID;

    @FXML
    private TextField txtModifyPartName;

    @FXML
    private TextField txtModifyPartInv;

    @FXML
    private TextField txtModifyPartPrice;

    @FXML
    private TextField txtModifyPartMax;

    @FXML
    private TextField txtModifyPartDyn;

    @FXML
    private TextField txtModifyPartMin;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnCancel;
    
    @FXML
    private int partID;
    
    @FXML
    private boolean bOutSourced;
    
    @FXML
    private String errPartValid = new String();
    
    @FXML
    private String errPartField = new String();
    
    @FXML
    public void setTgSource(ToggleGroup tgSource) {
        this.tgSource = tgSource;
        rbInHouse.setToggleGroup(tgSource);
        rbOutsourced.setToggleGroup(tgSource);
    }
    
    @FXML
    public void getSourceAction (ActionEvent event) throws Exception {
        if (tgSource.getSelectedToggle() == rbInHouse) {
            lblModifyPartDyn.setText("Machine ID");
            txtModifyPartDyn.setPromptText("Machine ID");
        }
        else {
            lblModifyPartDyn.setText("Company Name");
            txtModifyPartDyn.setPromptText("Company Name");
            bOutSourced = true;
        }
    }
    
    @FXML
    public void handleSaveAction(ActionEvent event) {
        String name = txtModifyPartName.getText();
        String inStock = txtModifyPartInv.getText();
        String price = txtModifyPartPrice.getText();
        String min = txtModifyPartMin.getText();
        String max = txtModifyPartMax.getText();
        String partDyn = txtModifyPartDyn.getText();
        
        errPartField = Part.getEmptyFields(name, inStock, price, max, min, partDyn, errPartField);
        if (errPartField.length() > 0){
            Alert alert = new Alert(Alert.AlertType.WARNING);
           alert.setTitle("Part Modification Warning");
           alert.setHeaderText("The part was NOT modified!");
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
                   alert.setTitle("Part Modification Warning");
                   alert.setHeaderText("The part was NOT modified!");
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
                        updatePart(getSelectedPartIndex(), oSP);
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
                        updatePart(getSelectedPartIndex(), iHP);
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
    public void handleCancelAction (ActionEvent event) throws IOException {
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
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Part selectedPart = getSelectedPart();
        partID = getSelectedPart().getPartID();
        txtModifyPartID.setText("Auto Gen: " + partID);
        txtModifyPartName.setText(selectedPart.getName());
        txtModifyPartInv.setText(Integer.toString(selectedPart.getInStock()));
        txtModifyPartPrice.setText(Double.toString(selectedPart.getPrice()));
        txtModifyPartMax.setText(Integer.toString(selectedPart.getMax()));
        txtModifyPartMin.setText(Integer.toString(selectedPart.getMin()));
        if (selectedPart instanceof Inhouse) {
            tgSource.selectToggle(rbInHouse);
            txtModifyPartDyn.setText(Integer.toString(((Inhouse) selectedPart).getMachineID()));
            bOutSourced = false;
        }
        else {
            tgSource.selectToggle(rbOutsourced);
            txtModifyPartDyn.setText(((Outsourced) selectedPart).getCompanyName());
            lblModifyPartDyn.setText("Company Name");
            txtModifyPartDyn.setPromptText("Company Name");
            bOutSourced = true;
        }
    }    
    
}
