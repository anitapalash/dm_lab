package hse.dm_lab.controller;

import hse.dm_lab.MainApplication;
import hse.dm_lab.model.Item;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainViewController {

    private ToggleGroup toggleGroup = new ToggleGroup();

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
    private TextField claimCountTextField;

    @FXML
    private RadioButton femaleRadioButton;

    @FXML
    private TextField fioTextField;

    @FXML
    private RadioButton maleRadioButton;

    @FXML
    private TextField roleTextField;

    @FXML
    private Button submitButton;

    @FXML
    private TableColumn<Item, String> sexColumn;

    @FXML
    private TableColumn<Item, String> roleColumn;

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
    void insert(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Pane pane = FXMLLoader.load(MainViewController.class.getResource("/fxml/InsertView.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setTitle("Palashinovich File Database");
        maleRadioButton.setToggleGroup(toggleGroup);
        femaleRadioButton.setToggleGroup(toggleGroup);
        stage.showAndWait();
    }

    @FXML
    void submitInsert(ActionEvent event) {
        Item item = new Item(MainApplication.manipulator.getNewId());
        //fio field
        if (fioTextField.getText() == null || fioTextField.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка");
            alert.setContentText("Не заполнено поле ФИО");
            alert.showAndWait();
        } else {
            if (fioTextField.getText().length() < 10) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Ошибка");
                alert.setContentText("Слишком короткое ФИО");
                alert.showAndWait();
            }
        }
        item.setFio(fioTextField.getText());

        //role field
        if (roleTextField.getText() == null || roleTextField.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка");
            alert.setContentText("Не заполнена роль. Будет присвоена роль Пользователь");
            alert.showAndWait();
            item.setRole('U');
        } else {
            switch (roleTextField.getText()) {
                case "Админ":
                case "Администратор":
                    item.setRole('A');
                    break;
                case "Аналитик":
                    item.setRole('N');
                    break;
                case "Юзер":
                case "Пользователь":
                    item.setRole('U');
                    break;
                case "Дев":
                case "Девелопер":
                    item.setRole('D');
                    break;
                default:
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ошибка");
                    alert.setHeaderText("Ошибка");
                    alert.setContentText("Нет такой роли");
                    alert.showAndWait();
            }
        }

        //claimCount field
        if (claimCountTextField.getText() == null || claimCountTextField.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Внимание");
            alert.setHeaderText("Внимание");
            alert.setContentText("Вы не указали количество заявок, будет присвоен 0");
            alert.showAndWait();
            item.setClaimCount(0);
        } else {
            try {
                int claims = Integer.parseInt(claimCountTextField.getText());
                item.setClaimCount(claims);
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Ошибка");
                alert.setContentText("Вы ввели не числовое значение количества заявок");
                alert.showAndWait();
            }
        }

        //sex field
        String sex = ((ToggleButton)toggleGroup.getSelectedToggle()).getText();
        if (sex.equals("Мужской")) {
            item.setSex(true);
        } else {
            item.setSex(false);
        }

        MainApplication.manipulator.saveToDB(item);
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
        ObservableList<Item> items = (ObservableList<Item>) MainApplication.manipulator.showAll();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        fioColumn.setCellValueFactory(new PropertyValueFactory<>("fio"));
        sexColumn.setCellValueFactory(new PropertyValueFactory<>("sex"));
        claimColumn.setCellValueFactory(new PropertyValueFactory<>("claimCount"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        mainTable.setItems(items);
    }

}
