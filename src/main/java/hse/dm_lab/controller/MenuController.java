package hse.dm_lab.controller;

import hse.dm_lab.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    private Button alert = new Button("Новая база данных успешно создана");

    @FXML
    void initialize() {
        assert deleteDBButton != null : "fx:id=\"deleteDBButton\" was not injected: check your FXML file 'Menu.fxml'.";
        assert newDBButton != null : "fx:id=\"newDBButton\" was not injected: check your FXML file 'Menu.fxml'.";
        assert openDBButton != null : "fx:id=\"openDBButton\" was not injected: check your FXML file 'Menu.fxml'.";
    }

    @FXML
    private Button deleteDBButton;

    @FXML
    private Button newDBButton;

    @FXML
    private Button openDBButton;


    @FXML
    void deleteDB(ActionEvent event) {
        MainApplication.manipulator.deleteDB();
    }

    @FXML
    void initDB(ActionEvent event) {
        MainApplication.manipulator.createDB();
    }

    @FXML
    void selectAll(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Pane pane = FXMLLoader.load(MenuController.class.getResource("/fxml/MainView.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setTitle("Palashinovich File Database");
        openDBButton.getScene().getWindow().hide();
        stage.show();
    }
}
