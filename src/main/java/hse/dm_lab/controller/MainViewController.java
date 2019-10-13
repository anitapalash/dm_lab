package hse.dm_lab.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<?, ?> claimColumn;

    @FXML
    private Button createBackupButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private TableColumn<?, ?> fioColumn;

    @FXML
    private Button fromBackupButton;

    @FXML
    private TableColumn<?, ?> idColumn;

    @FXML
    private Button importDataButton;

    @FXML
    private Button insertButton;

    @FXML
    private Button searchButon;

    @FXML
    private TableColumn<?, ?> sexColumn;


    @FXML
    void backupDB(ActionEvent event) {
    }

    @FXML
    void delete(ActionEvent event) {
    }

    @FXML
    void editDB(ActionEvent event) {
    }

    @FXML
    void importData(ActionEvent event) {
    }

    @FXML
    void insert(ActionEvent event) {
    }

    @FXML
    void recoverFromBackup(ActionEvent event) {
    }

    @FXML
    void search(ActionEvent event) {
    }

    @FXML
    void initialize() {
        assert claimColumn != null : "fx:id=\"claimColumn\" was not injected: check your FXML file 'MainView.fxml'.";
        assert createBackupButton != null : "fx:id=\"createBackupButton\" was not injected: check your FXML file 'MainView.fxml'.";
        assert deleteButton != null : "fx:id=\"deleteButton\" was not injected: check your FXML file 'MainView.fxml'.";
        assert editButton != null : "fx:id=\"editButton\" was not injected: check your FXML file 'MainView.fxml'.";
        assert fioColumn != null : "fx:id=\"fioColumn\" was not injected: check your FXML file 'MainView.fxml'.";
        assert fromBackupButton != null : "fx:id=\"fromBackupButton\" was not injected: check your FXML file 'MainView.fxml'.";
        assert idColumn != null : "fx:id=\"idColumn\" was not injected: check your FXML file 'MainView.fxml'.";
        assert importDataButton != null : "fx:id=\"importDataButton\" was not injected: check your FXML file 'MainView.fxml'.";
        assert insertButton != null : "fx:id=\"insertButton\" was not injected: check your FXML file 'MainView.fxml'.";
        assert searchButon != null : "fx:id=\"searchButon\" was not injected: check your FXML file 'MainView.fxml'.";
        assert sexColumn != null : "fx:id=\"sexColumn\" was not injected: check your FXML file 'MainView.fxml'.";
    }

}
