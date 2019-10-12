package hse.dm_lab.controller;

import hse.dm_lab.util.DBManipulator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    private DBManipulator manipulator;

    private Button alert = new Button("Новая база данных успешно создана");

    @FXML
    void initialize() {
        assert deleteDBButton != null : "fx:id=\"deleteDBButton\" was not injected: check your FXML file 'Menu.fxml'.";
        assert newDBButton != null : "fx:id=\"newDBButton\" was not injected: check your FXML file 'Menu.fxml'.";
        assert openDBButton != null : "fx:id=\"openDBButton\" was not injected: check your FXML file 'Menu.fxml'.";
        manipulator = new DBManipulator();
    }

    @FXML
    private Button deleteDBButton;

    @FXML
    private Button newDBButton;

    @FXML
    private Button openDBButton;


    @FXML
    void deleteDB(ActionEvent event) {
        manipulator.deleteDB();
    }

    @FXML
    void initDB(ActionEvent event) {
        manipulator = new DBManipulator();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Успех");
        alert.setHeaderText("Успех");
        alert.setContentText("База данных успешно создана");
        alert.showAndWait();
    }

    @FXML
    void selectAll(ActionEvent event) {
    }
}
