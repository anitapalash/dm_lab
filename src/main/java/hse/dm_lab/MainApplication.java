package hse.dm_lab;

import hse.dm_lab.model.Item;
import hse.dm_lab.util.DBManipulator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class MainApplication extends Application {

    public static DBManipulator manipulator = new DBManipulator();
    private static List<Item> tempList = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        String fxmlFile = "/fxml/Menu.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResourceAsStream(fxmlFile));
        stage.setTitle("Palashinovich File Database");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static List<Item> getTempList() {
        return tempList;
    }

    public static void setTempList(List<Item> tempList) {
        MainApplication.tempList = tempList;
    }
}
