package InventorySystem.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Raymond Lanoux
 */
public class Inventory {
    private static ObservableList<Product> products = FXCollections.observableArrayList();
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static int partID = 0;
    private static int productID = 0;
    
    public static void addProduct(Product product) {
        products.add(product);
    }
    
    public static boolean removeProduct(int productID) {
        for (Product p : products){
            if (p.getProductID() == productID) {
                products.remove(p);
                return true;
            }
        }
        return false;
    }
    
    public static Product lookupProduct(int productID) {
        for (Product p : products) {
            if (p.getProductID() == productID) {
                return p;
            }
        }
        return null;
    }
    
    public static void updateProduct(int productIndex, Product product) {
        products.set(productIndex, product);
    }
    
    public static void addPart(Part part) {
        allParts.addAll(part);
    }
    
    public static boolean deletePart(int partID) {
        for (Part p : allParts) {
            if (p.getPartID() == partID) {
                allParts.remove(p);
                return true;
            }
        }
        return false;
    }
    
    public static Part lookupPart(int partID) {
        for(Part p : allParts) {
            if(p.getPartID() == partID)
                return p;
        }
        return null;
    }
    
    public static void updatePart(int partIndex, Part part) {
        allParts.set(partIndex, part);
    }
    
    public static ObservableList<Part> getParts() {
        return allParts;
    }
    
    public static ObservableList<Product> getProducts() {
        return products;
    }
    
    public static int getPartIDCount() {
        partID++;
        return partID;
    }
    
    public static int getProductIDCount() {
        productID++;
        return productID;
    }
}
