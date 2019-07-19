package InventorySystem.View_Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import InventorySystem.Model.Inventory;
import static InventorySystem.Model.Inventory.getParts;
import InventorySystem.Model.Part;
import InventorySystem.Model.Product;
import static InventorySystem.Model.Product.addAssociatedPart;
import static InventorySystem.Model.Product.getAssociatedPartsList;
import static InventorySystem.Model.Product.removeAssociatedPart;

/**
 * FXML Controller class
 *
 * @author Raymond Lanoux
 */
public class AddProductScreenController implements Initializable {

    @FXML
    private AnchorPane scrAddProduct;

     @FXML
    private TextField txtAddProductID;

    @FXML
    private TextField txtAddProductName;

    @FXML
    private TextField txtAddProductInv;

    @FXML
    private TextField txtAddProductPrice;

    @FXML
    private TextField txtAddProductMax;

    @FXML
    private TextField txtAddProductMin;

    @FXML
    private Button btnSearch;

    @FXML
    private TextField txtSearchParts;

    @FXML
    private TableView<Part> tvPartsInStock;

    @FXML
    private TableColumn<Part, Integer> partIDCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Integer> partInStockCol;

    @FXML
    private TableColumn<Part, Double> partPriceCol;

    @FXML
    private Button btnAdd;

    @FXML
    private TableView<Part> tvAssociatedParts;

    @FXML
    private TableColumn<Part, Integer> associatedPartIDCol;

    @FXML
    private TableColumn<Part, String> associatedPartNameCol;

    @FXML
    private TableColumn<Part, Integer> associatedPartInStockCol;

    @FXML
    private TableColumn<Part, Double> associatedPartPriceCol;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnCancel;
    
    @FXML
    private int productID;
    
    @FXML
    private String errProductValid = new String();
    
    @FXML
    private String errProductField = new String();
    
    @FXML
    public void handleSearchAction(ActionEvent event) {
        String searchPartIDString = txtSearchParts.getText();
        if (searchPartIDString.equals("")){
            Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Part Search Warning");
                alert.setHeaderText("There were no parts found!");
                alert.setContentText("You did not enter a part to search for!");
                alert.showAndWait();
        }
        else {
        boolean found=false;
            try {
                Part searchPart = Inventory.lookupPart(Integer.parseInt(searchPartIDString));
                if (searchPart != null) {
                    ObservableList<Part> filteredPartsList = FXCollections.observableArrayList();
                    filteredPartsList.add(searchPart);
                    tvPartsInStock.setItems(filteredPartsList);
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Part Search Warning");
                    alert.setHeaderText("There were no parts found!");
                    alert.setContentText("The search term entered does not match any part ID!");
                    alert.showAndWait();
                }
            }
            catch (NumberFormatException e) {
                for (Part p : getParts()) {
                    if (p.getName().equals(searchPartIDString)) {
                        found=true;
                        ObservableList<Part> filteredPartsList = FXCollections.observableArrayList();
                        filteredPartsList.add(p);
                        tvPartsInStock.setItems(filteredPartsList);
                    }
                }
                if (found==false) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Part Search Warning");
                    alert.setHeaderText("There were no parts found!");
                    alert.setContentText("The search term entered does not match any part name!");
                    alert.showAndWait();
                }
            }
        }
    }
    
    @FXML
    public void handleAddAction(ActionEvent event) {
        Part part = tvPartsInStock.getSelectionModel().getSelectedItem();
        if (part == null) {
            Alert nullalert = new Alert(Alert.AlertType.ERROR);
            nullalert.setTitle("Associated Part Addition Error");
            nullalert.setHeaderText("The part was not added!");
            nullalert.setContentText("A part was not selected!");
            nullalert.showAndWait();
        }
        else {
            addAssociatedPart(part);
            tvAssociatedParts.setItems(getAssociatedPartsList());
        }
    }
    
    @FXML
    public void handleDeleteAction(ActionEvent event) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Associated Part");
            alert.setHeaderText("Are you sure you want to delete the associated part?");
            alert.setContentText("Press OK to delete the associated part. \nPress Cancel to cancel the deletion.");
            alert.showAndWait();
        
        if (alert.getResult() == ButtonType.OK) {
            try {
                Part part = tvAssociatedParts.getSelectionModel().getSelectedItem();
                removeAssociatedPart(part.getPartID());
            }
            catch (NullPointerException e) {
                Alert nullalert = new Alert(Alert.AlertType.ERROR);
                nullalert.setTitle("Associated Part Deletion Error");
                nullalert.setHeaderText("The part was not deleted!");
                nullalert.setContentText("A part was not selected!");
                nullalert.showAndWait();
            }
        }
        else {
            alert.close();
        }
    }
    
    @FXML
    public void handleSaveAction(ActionEvent event) throws IOException {
        String name = txtAddProductName.getText();
        String inStock = txtAddProductInv.getText();
        String price = txtAddProductPrice.getText();
        String max = txtAddProductMax.getText();
        String min = txtAddProductMin.getText();
        ObservableList<Part> associatedParts = tvAssociatedParts.getItems();
        
        errProductField = Product.getEmptyFields(name, inStock, price, max, min, errProductField);
        if (errProductField.length() > 0){
            Alert alert = new Alert(Alert.AlertType.WARNING);
           alert.setTitle("Product Addition Warning");
           alert.setHeaderText("The product was NOT added!");
           alert.setContentText(errProductField);
           alert.showAndWait();
           errProductField = "";
        }
        else {
            if (associatedParts.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Product Save Error");
                alert.setHeaderText("The product was not saved!");
                alert.setContentText("An associated part was not selected!");
                alert.showAndWait();
            }
            else {
                try{
                    errProductValid = Part.getPartValidation( 
                       Integer.parseInt(inStock), 
                       Double.parseDouble(price), 
                       Integer.parseInt(max), 
                       Integer.parseInt(min), 
                       errProductValid);
                    if (errProductValid.length() > 0) {
                       Alert alert = new Alert(Alert.AlertType.WARNING);
                       alert.setTitle("Product Addition Warning");
                       alert.setHeaderText("The product was NOT added!");
                       alert.setContentText(errProductValid);
                       alert.showAndWait();
                       errProductValid = "";
                    }
                    else {
                        Product addProduct = new Product();
                        addProduct.setProductID(productID);
                        addProduct.setName(name);
                        addProduct.setInStock(Integer.parseInt(inStock));
                        addProduct.setPrice(Double.parseDouble(price));
                        addProduct.setMax(Integer.parseInt(max));
                        addProduct.setMin(Integer.parseInt(min));
                        addProduct.setAssociatedPartsList(associatedParts);
                        Inventory.addProduct(addProduct);

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
    }
    
    @FXML
    public void handleCancelAction (ActionEvent eCancel) throws IOException {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exit to Main Screen");
            alert.setHeaderText("Are you sure you want to cancel?");
            alert.setContentText("Press OK to exit to the Main screen. \nPress Cancel to stay on this screen.");
            alert.showAndWait();
            
            if (alert.getResult() == ButtonType.OK) {
                Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene scene = new Scene(root);
                Stage winMainScreen = (Stage)((Node)eCancel.getSource()).getScene().getWindow();
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
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("partID"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInStockCol.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        associatedPartIDCol.setCellValueFactory(new PropertyValueFactory<>("partID"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInStockCol.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        productID = Inventory.getProductIDCount();
        txtAddProductID.setText("Auto Gen: " + productID);
    }    
    
}
