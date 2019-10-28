package hse.dm_lab.controller;

import hse.dm_lab.MainApplication;
import hse.dm_lab.model.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainViewController {

    @FXML
    private TableColumn<Item, String> claimColumn;

    @FXML
    private TableView<Item> mainTable;

    @FXML
    private Button createBackupButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private TableColumn<Item, String> fioColumn;

    @FXML
    private Button fromBackupButton;

    @FXML
    private TableColumn<Item, String> idColumn;

    @FXML
    private Button importDataButton;

    @FXML
    private Button insertButton;

    @FXML
    private Button searchButon;

    @FXML
    private TableColumn<Item, String> sexColumn;

    @FXML
    private TableColumn<Item, String> roleColumn;

    @FXML
    void backupDB(ActionEvent event) {
    }

    @FXML
    void delete(ActionEvent event) {
        Item item = mainTable.getSelectionModel().getSelectedItem();
        if (item == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка");
            alert.setContentText("Не выбран элемент для удаления");
            alert.showAndWait();
        } else {
            MainApplication.manipulator.deleteItem(item.getId());
            refreshTable();
        }
    }

    @FXML
    public void editFioColumn(TableColumn.CellEditEvent editedCell) {
        Item selectedItem = mainTable.getSelectionModel().getSelectedItem();
        selectedItem.setFio(editedCell.getNewValue().toString());
        refreshTable();
    }

    @FXML
    public void editSexColumn(TableColumn.CellEditEvent editedCell) {
        String newSex = editedCell.getNewValue().toString();
        if (newSex.equals("Мужской") || newSex.equals("Женский")) {
            Item selectedItem = mainTable.getSelectionModel().getSelectedItem();
            selectedItem.setSex(newSex);
            refreshTable();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка");
            alert.setContentText("Недопустимое значение");
            alert.showAndWait();
        }
    }

    @FXML
    public void editClaimCountColumn(TableColumn.CellEditEvent editedCell) {
        Integer newClaimCount = Integer.parseInt(editedCell.getNewValue().toString());
        Item selectedItem = mainTable.getSelectionModel().getSelectedItem();
        selectedItem.setClaimCount(newClaimCount);
        refreshTable();
    }

    @FXML
    public void editRoleColumn(TableColumn.CellEditEvent editedCell) {
        String newRole = editedCell.getNewValue().toString();
        Item selectedItem = mainTable.getSelectionModel().getSelectedItem();
        switch (newRole) {
            case "Админ":
            case "Администратор":
                selectedItem.setRole("Администратор");
                break;
            case "Юзер":
            case "Пользователь":
                selectedItem.setRole("Пользователь");
                break;
            case "Девелопер":
            case "Разработчик":
                selectedItem.setRole("Разработчик");
                break;
            case "Аналитик":
                selectedItem.setRole("Аналитик");
                break;
            default:
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Ошибка");
                alert.setContentText("Недопустимое значение");
                alert.showAndWait();
        }
        refreshTable();
    }

    @FXML
    void importData(ActionEvent event) {
    }

    @FXML
    void insert(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Pane pane = FXMLLoader.load(MainViewController.class.getClassLoader().getResource("fxml/InsertView.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setTitle("Palashinovich File Database");
        stage.showAndWait();
        refreshTable();
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
        ObservableList<Item> items = FXCollections.observableArrayList(MainApplication.manipulator.showAll());
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        fioColumn.setCellValueFactory(new PropertyValueFactory<>("fio"));
        sexColumn.setCellValueFactory(new PropertyValueFactory<>("sex"));
        claimColumn.setCellValueFactory(new PropertyValueFactory<>("claimCount"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        mainTable.setItems(items);

        mainTable.setEditable(true);
        fioColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        sexColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        roleColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        claimColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    void refreshTable() {
        ObservableList<Item> items = FXCollections.observableArrayList(MainApplication.manipulator.showAll());
        mainTable.setItems(items);
    }
}
