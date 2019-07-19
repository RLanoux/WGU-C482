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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import InventorySystem.Model.Inventory;
import static InventorySystem.Model.Inventory.deletePart;
import static InventorySystem.Model.Inventory.getParts;
import static InventorySystem.Model.Inventory.getProducts;
import static InventorySystem.Model.Inventory.removeProduct;
import InventorySystem.Model.Part;
import InventorySystem.Model.Product;
/**
 * FXML Controller class
 *
 * @author Raymond Lanoux
 */
public class MainScreenController implements Initializable {

    @FXML
    private AnchorPane scrMain;

    @FXML
    private Button btnExit;

    @FXML
    private Button searchParts;

    @FXML
    private Button addParts;

    @FXML
    private Button modifyParts;

    @FXML
    private Button deleteParts;

    @FXML
    private Button searchProducts;

    @FXML
    private Button addProducts;

    @FXML
    private Button modifyProducts;

    @FXML
    private Button deleteProducts;
    
    @FXML
    private TextField txtSearchParts;
    
    @FXML
    private TextField txtSearchProducts;
    
    @FXML
    private TableView<Part> tvParts;

    @FXML
    private TableColumn<Part, Integer> partIDCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Integer> partInStockCol;

    @FXML
    private TableColumn<Part, Double> partPriceCol;

    @FXML
    private TableView<Product> tvProducts;

    @FXML
    private TableColumn<Product, Integer> productIDCol;

    @FXML
    private TableColumn<Product, String> productNameCol;

    @FXML
    private TableColumn<Product, Integer> productInventoryCol;

    @FXML
    private TableColumn<Product, Double> productPriceCol;
    
    @FXML
    public void addPartsAction (ActionEvent eAddParts) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPartScreen.fxml"));
            Parent addPartScreen = loader.load();
            Scene addPartScene = new Scene(addPartScreen);
            Stage winAddPart = (Stage)((Node)eAddParts.getSource()).getScene().getWindow();
            winAddPart.setTitle("Add Part");
            winAddPart.setScene(addPartScene);
            winAddPart.show();
        }
        catch (IOException e) {}
    }
    
    @FXML
    public void modifyPartsAction (ActionEvent eModifyParts) throws IOException {
        selectedPart = tvParts.getSelectionModel().getSelectedItem();
        selectedPartIndex = getParts().indexOf(selectedPart);
        if (selectedPart == null) {
            Alert nullalert = new Alert(AlertType.ERROR);
            nullalert.setTitle("Part Modification Error");
            nullalert.setHeaderText("The part is NOT able to be modified!");
            nullalert.setContentText("There was no part selected!");
            nullalert.showAndWait();
        }
        else {
            try {
                Parent modifyPartScreen = FXMLLoader.load(getClass().getResource("ModifyPartScreen.fxml"));
                Scene modifyPartScene = new Scene(modifyPartScreen);
                Stage winModifyPart = (Stage)((Node)eModifyParts.getSource()).getScene().getWindow();
                winModifyPart.setTitle("Modify Part");
                winModifyPart.setScene(modifyPartScene);
                winModifyPart.show();
            }
            catch (IOException e) {}
        }
    }
    
    @FXML
    public void deletePartsAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Part");
        alert.setHeaderText("Are you sure you want to delete this part?");
        alert.setContentText("Press OK to delete the part. \nPress Cancel to cancel the deletion.");
        alert.showAndWait();
        
        if (alert.getResult() == ButtonType.OK) {
            try {
                Part part = tvParts.getSelectionModel().getSelectedItem();
                deletePart(part.getPartID());
            }
            catch (NullPointerException e) {
                Alert nullalert = new Alert(AlertType.ERROR);
                nullalert.setTitle("Part Deletion Error");
                nullalert.setHeaderText("The part was NOT deleted!");
                nullalert.setContentText("There was no part selected!");
                nullalert.showAndWait();
            }
        }
        else {
            alert.close();
        }
    }
    
    @FXML
    public void searchPartsAction(ActionEvent event) {
        String searchPartIDString = txtSearchParts.getText();
        if (searchPartIDString.equals("")){
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Part Search Warning");
                alert.setHeaderText("There were no parts found!");
                alert.setContentText("You did not enter a part ID or name to search for!");
                alert.showAndWait();
        }
        else {
        boolean found=false;
            try {
                Part searchPart = Inventory.lookupPart(Integer.parseInt(searchPartIDString));
                if (searchPart != null) {
                    ObservableList<Part> filteredPartsList = FXCollections.observableArrayList();
                    filteredPartsList.add(searchPart);
                    tvParts.setItems(filteredPartsList);
                }
                else {
                    Alert alert = new Alert(AlertType.WARNING);
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
                        tvParts.setItems(filteredPartsList);
                    }
                }
                if (found==false) {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Part Search Warning");
                    alert.setHeaderText("There were no parts found!");
                    alert.setContentText("The search term entered does not match any part name!");
                    alert.showAndWait();
                }
            }
        }
    }
    
    @FXML
    public void addProductsAction (ActionEvent eAddProducts) throws IOException {
        try {
            Parent addProductScreen = FXMLLoader.load(getClass().getResource("AddProductScreen.fxml"));
            Scene addProductScene = new Scene(addProductScreen);
            Stage winAddProduct = (Stage)((Node)eAddProducts.getSource()).getScene().getWindow();
            winAddProduct.setTitle("Add Product");
            winAddProduct.setScene(addProductScene);
            winAddProduct.show();
        }
        catch (IOException e) {}
    }
    
    @FXML
    public void modifyProductsAction (ActionEvent eModifyProducts) throws IOException {
        selectedProduct = tvProducts.getSelectionModel().getSelectedItem();
        selectedProductIndex = getProducts().indexOf(selectedProduct);
        if (selectedProduct == null) {
            Alert nullalert = new Alert(AlertType.ERROR);
            nullalert.setTitle("Product Modification Error");
            nullalert.setHeaderText("The product is NOT able to be modified!");
            nullalert.setContentText("There was no product selected!");
            nullalert.showAndWait();
        }
        else {
            try {
                Parent modifyProductScreen = FXMLLoader.load(getClass().getResource("ModifyProductScreen.fxml"));
                Scene modifyProductScene = new Scene(modifyProductScreen);
                Stage winModifyProduct = (Stage)((Node)eModifyProducts.getSource()).getScene().getWindow();
                winModifyProduct.setTitle("Modify Product");
                winModifyProduct.setScene(modifyProductScene);
                winModifyProduct.show();
            }
            catch (IOException e) {}
        }
    }
    
    @FXML
    public void deleteProductsAction(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete Product");
        alert.setHeaderText("Are you sure you want to delete this product?");
        alert.setContentText("Press OK to delete the product. \nPress Cancel to cancel the deletion.");
        alert.showAndWait();
        
        if (alert.getResult() == ButtonType.OK) {
            try {
                Product product = tvProducts.getSelectionModel().getSelectedItem();
                removeProduct(product.getProductID());
            }
            catch (NullPointerException e) {
                Alert nullalert = new Alert(AlertType.ERROR);
                nullalert.setTitle("Product Deletion Error");
                nullalert.setHeaderText("The product was NOT deleted!");
                nullalert.setContentText("There was no product selected!");
                nullalert.showAndWait();
            }
        }
        else {
            alert.close();
        }
    }

    @FXML
    public void searchProductsAction(ActionEvent event) {
       String searchProductIDString = txtSearchProducts.getText();
        if (searchProductIDString.equals("")){
            Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Product Search Warning");
                alert.setHeaderText("There were no products found!");
                alert.setContentText("You did not enter a product to search for!");
                alert.showAndWait();
        }
        else {
        boolean found=false;
            try {
                Product searchProduct = Inventory.lookupProduct(Integer.parseInt(searchProductIDString));
                if (searchProduct != null) {
                    ObservableList<Product> filteredProductsList = FXCollections.observableArrayList();
                    filteredProductsList.add(searchProduct);
                    tvProducts.setItems(filteredProductsList);
                }
                else {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Product Search Warning");
                    alert.setHeaderText("There were no products found!");
                    alert.setContentText("The search term entered does not match any product ID!");
                    alert.showAndWait();
                }
            }
            catch (NumberFormatException e) {
                for (Product p : getProducts()) {
                    if (p.getName().equals(searchProductIDString)) {
                        found=true;
                        ObservableList<Product> filteredProductsList = FXCollections.observableArrayList();
                        filteredProductsList.add(p);
                        tvProducts.setItems(filteredProductsList);
                    }
                }
                if (found==false) {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Product Search Warning");
                    alert.setHeaderText("There were no products found!");
                    alert.setContentText("The search term entered does not match any product names!");
                    alert.showAndWait();
                }
            }
        }
    }
    @FXML
    public void updatePartsTV() {
        tvParts.setItems(getParts());
    }
    @FXML
    public void updateProductsTV() {
        tvProducts.setItems(getProducts());
    }
    
    @FXML
    public void exitButtonAction (ActionEvent eExitButton) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Inventory Management System");
        alert.setHeaderText("Are you sure you want to exit?");
        alert.setContentText("Press OK to exit the program. \nPress Cancel to stay on this screen.");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            Stage winMainScreen = (Stage)((Node)eExitButton.getSource()).getScene().getWindow();
            winMainScreen.close();
        }
        else {
            alert.close();
        }
    }
    
    private static Part selectedPart;
    
    private static int selectedPartIndex;
    
    public static Part getSelectedPart() {
        return selectedPart;
    }
    
    public static int getSelectedPartIndex() {
        return selectedPartIndex;
    }
    
    private static Product selectedProduct;
    
    private static int selectedProductIndex;
    
    public static Product getSelectedProduct() {
        return selectedProduct;
    }
    
    public static int getSelectedProductIndex() {
        return selectedProductIndex;
    }
    
    public static ObservableList<Part> selectedAssocPart = FXCollections.observableArrayList();
    
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
        updatePartsTV();
        productIDCol.setCellValueFactory(new PropertyValueFactory("productID"));
        productNameCol.setCellValueFactory(new PropertyValueFactory("name"));
        productInventoryCol.setCellValueFactory(new PropertyValueFactory("inStock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory("price"));
        updateProductsTV();
    }
}
