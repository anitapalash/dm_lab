package hse.dm_lab.controller;

import hse.dm_lab.MainApplication;
import hse.dm_lab.model.Item;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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
    void searchItems(ActionEvent event) {
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
            switch (role) {
                case "Девлопер":
                case "Разработчик":
                    filterItem.setRole("Разработчик");
                    break;
                case "Админ":
                case "Администратор":
                    filterItem.setRole("Администратор");
                    break;
                case "Юзер":
                case "Пользователь":
                    filterItem.setRole("Пользователь");
                    break;
                case "Аналитик":
                    filterItem.setRole("Аналитик");
                    break;
                default:
                    System.out.println("Недопустимое значение роли");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ошибка");
                    alert.setHeaderText("Ошибка");
                    alert.setContentText("Недопустимое значение роли");
                    alert.showAndWait();
                    break;
            }
        }

        MainApplication.manipulator.selectItems(filterItem);
        System.out.println("Search completed");
        searchButton.getScene().getWindow().hide();
    }
}
