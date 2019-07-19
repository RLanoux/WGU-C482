package InventorySystem.Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Raymond Lanoux
 */
public abstract class Part {
    private final SimpleIntegerProperty partID;
    private final SimpleIntegerProperty inStock;
    private final SimpleIntegerProperty max;
    private final SimpleIntegerProperty min;
    private final SimpleDoubleProperty price;
    private final SimpleStringProperty name;
    
    public Part() {
        this.partID = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.inStock = new SimpleIntegerProperty();
        this.price = new SimpleDoubleProperty();
        this.max = new SimpleIntegerProperty();
        this.min = new SimpleIntegerProperty();
    }

    public abstract int getPartID();

    public abstract void setPartID(int partID);
    
    public abstract IntegerProperty partIDProperty();

    public abstract int getInStock();

    public abstract void setInStock(int inStock);
    
    public abstract IntegerProperty inStockProperty();

    public abstract int getMin();

    public abstract void setMin(int min);
    
    public abstract IntegerProperty minProperty();

    public abstract int getMax();

    public abstract void setMax(int max);
    
    public abstract IntegerProperty maxProperty();
    
    public abstract String getName();

    public abstract void setName(String name);
    
    public abstract StringProperty nameProperty();

    public abstract double getPrice();

    public abstract void setPrice(double price);
    
    public abstract DoubleProperty priceProperty();
    
    public static String getPartValidation(int inStock, double price, int max, int min, String errPartValid) {
        if (inStock < 1) {
            errPartValid = errPartValid + "\nThe Inventory cannot be less than 1. ";
        }
        if (price <= 0) {
            errPartValid = errPartValid + "\nThe Price must be greater than $0. ";
        }
        if (max < min) {
            errPartValid = errPartValid + "\nThe Maximum stock must be greater than the Minimum stock. ";
        }
        if (inStock > max) {
            errPartValid = errPartValid + "\nThe Inventory must be less than or equal to the Maximum stock. ";
        }
        if (inStock < min) {
            errPartValid = errPartValid + "\nThe Inventory must be greater than or equal to the Minimum stock. ";
        }
        return errPartValid;
    }
    
    public static String getEmptyFields(String name, String inStock, String price, String max, String min, String partDyn, String errPartField) {
        if (name.equals("")) {
            errPartField = errPartField + "The Part Name field cannot be empty. ";
        }
        if (inStock.equals("")) {
            errPartField = errPartField + "\nThe Part Inventory field cannot be empty. ";
        }
        if (price.equals("")) {
            errPartField = errPartField + "\nThe Part Price field cannot be empty. ";
        }
        if (max.equals("")) {
            errPartField = errPartField + "\nThe Part Max field cannot be empty. ";
        }
        if (min.equals("")) {
            errPartField = errPartField + "\nThe Part Min field cannot be empty. ";
        }
        if (partDyn.equals("")) {
            errPartField = errPartField + "\nThe Part MachineID or Company Name field cannot be empty. ";
        }
        return errPartField;
    }
}
