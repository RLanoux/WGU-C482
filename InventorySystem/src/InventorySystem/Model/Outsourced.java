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
public class Outsourced extends Part {
    private final SimpleIntegerProperty partID;
    private final SimpleIntegerProperty inStock;
    private final SimpleIntegerProperty max;
    private final SimpleIntegerProperty min;
    private final SimpleDoubleProperty price;
    private final SimpleStringProperty name;
    private final SimpleStringProperty companyName;
    
    public Outsourced() {
        this.partID = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.inStock = new SimpleIntegerProperty();
        this.price = new SimpleDoubleProperty();
        this.max = new SimpleIntegerProperty();
        this.min = new SimpleIntegerProperty();
        this.companyName = new SimpleStringProperty();
    }
    
    public String getCompanyName() {
        return companyName.get();
    }
    
    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }
    
    public StringProperty companyNameProperty() {
        return companyName;
    }
    
    @Override
    public int getPartID() {
       return partID.get();
    }

    @Override
    public void setPartID(int partID) {
        this.partID.set(partID);
    }

    @Override
    public IntegerProperty partIDProperty() {
        return partID;
    }

    @Override
    public int getInStock() {
        return inStock.get();
    }

    @Override
    public void setInStock(int inStock) {
        this.inStock.set(inStock);
    }

    @Override
    public IntegerProperty inStockProperty() {
        return inStock;
    }

    @Override
    public int getMin() {
        return min.get();
    }

    @Override
    public void setMin(int min) {
        this.min.set(min);
    }

    @Override
    public IntegerProperty minProperty() {
        return min;
    }

    @Override
    public int getMax() {
        return max.get();
    }

    @Override
    public void setMax(int max) {
        this.max.set(max);
    }

    @Override
    public IntegerProperty maxProperty() {
        return max;
    }

    @Override
    public String getName() {
        return name.get();
    }

    @Override
    public void setName(String name) {
        this.name.set(name);
    }

    @Override
    public StringProperty nameProperty() {
        return name;
    }

    @Override
    public double getPrice() {
        return price.get();
    }

    @Override
    public void setPrice(double price) {
        this.price.set(price);
    }

    @Override
    public DoubleProperty priceProperty() {
        return price;
    }
}
