package InventorySystem.Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Raymond Lanoux
 */
public class Product {
    public static ObservableList<Part> associatedParts = FXCollections.observableArrayList();;
    private final IntegerProperty productID;
    private final IntegerProperty inStockProduct;
    private final IntegerProperty minProduct;
    private final IntegerProperty maxProduct;
    private final DoubleProperty priceProduct;
    private final StringProperty nameProduct;


    public Product() {
        productID = new SimpleIntegerProperty();
        inStockProduct = new SimpleIntegerProperty();
        minProduct = new SimpleIntegerProperty();
        maxProduct = new SimpleIntegerProperty();
        priceProduct = new SimpleDoubleProperty();
        nameProduct = new SimpleStringProperty();
    }
    
    public static void addAssociatedPart(Part part){
        associatedParts.add(part);
    }
    
    public static boolean removeAssociatedPart(int partID) {
        for (Part p : associatedParts) {
            if (p.getPartID() == partID) {
                associatedParts.remove(p);
                return true;
            }
        }
        return false;
    }
    
    public static Part lookupAssociatedPart(int partID) {
        return associatedParts.get(partID);
    }

    public int getProductID() {
        return productID.get();
    }

    public void setProductID(int productID) {
        this.productID.set(productID);
    }

    public int getInStock() {
        return inStockProduct.get();
    }

    public void setInStock(int inStock) {
        this.inStockProduct.set(inStock);
    }

    public int getMin() {
        return minProduct.get();
    }

    public void setMin(int min) {
        this.minProduct.set(min);
    }

    public int getMax() {
        return maxProduct.get();
    }

    public void setMax(int max) {
        this.maxProduct.set(max);
    }

    public double getPrice() {
        return priceProduct.get();
    }

    public void setPrice(double price) {
        this.priceProduct.set(price);
    }

    public String getName() {
        return nameProduct.get();
    }

    public void setName(String name) {
        this.nameProduct.set(name);
    }
    
    public static ObservableList getAssociatedPartsList() {
        return associatedParts;
    }
    
    public static void setAssociatedPartsList(ObservableList<Part> associatedParts) {
        Product.associatedParts = associatedParts;
    }
    
    public IntegerProperty productIDProperty() {
        return productID;
    }
    
    public IntegerProperty inStockProperty() {
        return inStockProduct;
    }
    
    public IntegerProperty minProperty() {
        return minProduct;
    }
    
    public IntegerProperty maxProperty() {
        return maxProduct;
    }
    
    public DoubleProperty priceProperty() {
        return priceProduct;
    }
    
    public StringProperty nameProperty() {
        return nameProduct;
    }
    
    public static String getProductValidation(int inStock, double price, int max, int min, String errProductValid) {
        if (inStock < 1) {
            errProductValid = errProductValid + "\nThe Inventory cannot be less than 1. ";
        }
        if (price <= 0) {
            errProductValid = errProductValid + "\nThe Price must be greater than $0. ";
        }
        if (max < min) {
            errProductValid = errProductValid + "\nThe Maximum stock must be greater than the Minimum stock. ";
        }
        if (inStock > max) {
            errProductValid = errProductValid + "\nThe Inventory must be less than or equal to the Maximum stock. ";
        }
        if (inStock < min) {
            errProductValid = errProductValid + "\nThe Inventory must be greater than or equal to the Minimum stock. ";
        }
        return errProductValid;
    }
    
    public static String getEmptyFields(String name, String inStock, String price, String max, String min, String errProductField) {
        if (name.equals("")) {
            errProductField = errProductField + "The Product Name field cannot be empty. ";
        }
        if (inStock.equals("")) {
            errProductField = errProductField + "\nThe Product Inventory field cannot be empty. ";
        }
        if (price.equals("")) {
            errProductField = errProductField + "\nThe Product Price field cannot be empty. ";
        }
        if (max.equals("")) {
            errProductField = errProductField + "\nThe Product Max field cannot be empty. ";
        }
        if (min.equals("")) {
            errProductField = errProductField + "\nThe Product Min field cannot be empty. ";
        }
        return errProductField;
    }
    
}
