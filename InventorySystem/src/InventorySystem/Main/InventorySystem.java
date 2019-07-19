package InventorySystem.Main;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import InventorySystem.View_Controller.MainScreenController;

/**
 *
 * @author Raymond Lanoux
 */
public class InventorySystem extends Application {
    
    @Override
    public void start(Stage stage) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainScreenController.class.getResource("MainScreen.fxml"));
            

            Parent root = fxmlLoader.load();
            MainScreenController mainScrCntrl = fxmlLoader.getController();
            Scene scene = new Scene(root);

            stage.setTitle("Inventory Management System");
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
