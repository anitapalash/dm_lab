package hse.dm_lab.controller;

import hse.dm_lab.MainApplication;
import hse.dm_lab.model.Item;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.List;

public class SearchController {
    @FXML
    private TextField idTextField;

    @FXML
    private TextField fioTextField;

    @FXML
    private TextField claimCountTextField;

    @FXML
    private TextField sexTextField;

    @FXML
    private TextField roleTextField;

    @FXML
    private Button searchButton;

    @FXML
    List<Item> searchItems(ActionEvent event) {
        Item filterItem = new Item();
        if (idTextField.getText() != null && !idTextField.getText().trim().isEmpty()) {
            Integer id = Integer.parseInt(idTextField.getText());
            filterItem.setId(id);
        }
        if (fioTextField.getText() != null && !fioTextField.getText().trim().isEmpty()) {
            filterItem.setFio(fioTextField.getText());
        }
        String claimCount = claimCountTextField.getText();
        String sex = sexTextField.getText();
        String role = roleTextField.getText();
        if (claimCount != null && !claimCount.trim().isEmpty()) {
            Integer claims = Integer.parseInt(claimCount);
            filterItem.setClaimCount(claims);
        }
        if (sex != null && !sex.trim().isEmpty()) {
            if (sex.equals("Мужской") || sex.equals("Женский")) {
                filterItem.setSex(sex);
            } else {
                System.out.println("Недопустимое значение пола");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Ошибка");
                alert.setContentText("Недопустимое значение пола");
                alert.showAndWait();
            }
        }
        if (role != null && !role.trim().isEmpty()) {
            if (role.equals("Девлопер") || role.equals("Разработчик")) {
                filterItem.setRole("Разработчик");
            } else if (role.equals("Админ") || role.equals("Администратор")) {
                filterItem.setRole("Администратор");
            } else if (role.equals("Юзер") || role.equals("Пользователь")) {
                filterItem.setRole("Пользователь");
            } else if (role.equals("Аналитик")) {
                filterItem.setRole("Аналитик");
            } else {
                System.out.println("Недопустимое значение роли");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Ошибка");
                alert.setContentText("Недопустимое значение роли");
                alert.showAndWait();
            }
        }

        return MainApplication.manipulator.selectItems(filterItem);
    }
}
