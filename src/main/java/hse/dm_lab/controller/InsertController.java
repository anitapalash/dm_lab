package hse.dm_lab.controller;

import hse.dm_lab.MainApplication;
import hse.dm_lab.model.Item;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class InsertController {

    private ToggleGroup toggleGroup = new ToggleGroup();

    @FXML
    private TextField fioTextField;

    @FXML
    private TextField roleTextField;

    @FXML
    private TextField claimCountTextField;

    @FXML
    private RadioButton maleRadioButton;

    @FXML
    private RadioButton femaleRadioButton;

    @FXML
    private Button submitButton;

    @FXML
    void submitInsert(ActionEvent event) {
        Item item = new Item();
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
            item.setRole("Пользователь");
        } else {
            switch (roleTextField.getText()) {
                case "Админ":
                case "Администратор":
                    item.setRole("Администратор");
                    break;
                case "Аналитик":
                    item.setRole("Аналитик");
                    break;
                case "Юзер":
                case "Пользователь":
                    item.setRole("Пользователь");
                    break;
                case "Дев":
                case "Девелопер":
                case "Разработчик":
                    item.setRole("Разработчик");
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
        if (sex != null) {
            item.setSex(sex);
        }

        MainApplication.manipulator.saveToDB(item);
        System.out.println("Added new line: " + item.getFio());
        submitButton.getScene().getWindow().hide();
    }

    @FXML
    void initialize() {
        maleRadioButton.setToggleGroup(toggleGroup);
        maleRadioButton.setSelected(true);
        femaleRadioButton.setToggleGroup(toggleGroup);
    }
}
