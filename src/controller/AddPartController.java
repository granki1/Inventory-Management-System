package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class provides the ability to manipulate the part inventory
 */
public class AddPartController implements Initializable {

    @FXML private Label partIdNameLabel;
    @FXML private RadioButton inHouseRadioButton;
    @FXML private RadioButton outsourcedRadioButton;
    @FXML private TextField partNameText;
    @FXML private TextField partInventoryText;
    @FXML private TextField partPriceText;
    @FXML private TextField partMaxText;
    @FXML private TextField partIdNameText;
    @FXML private TextField partMinText;

    /**
     * @param event Cancel button
     * @throws IOException From FXMLLoader
     */
    @FXML
    void cancelButtonAction(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Please Confirm");
        alert.setContentText("Cancel changes and return to the main screen?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            returnToMainScreen(event);
        }
    }

    /**
     * @param event In-house radio button
     */
    @FXML
    void inHouseRadioButtonAction(ActionEvent event) {

        partIdNameLabel.setText("Machine ID");
    }

    /**
     * @param event Outsourced radio button
     */
    @FXML
    void outsourcedRadioButtonAction(ActionEvent event) {

        partIdNameLabel.setText("Company Name");
    }

    /**
     * New part is added to inventory
     * Verifies all information is valid
     * @param event Save button action
     * @throws IOException From FXMLLoader
     */
    @FXML
    void saveButtonAction(ActionEvent event) throws IOException {

        try {
            int id = 0;
            String name = partNameText.getText();
            double price = Double.parseDouble(partPriceText.getText());
            int stock = Integer.parseInt(partInventoryText.getText());
            int min = Integer.parseInt(partMinText.getText());
            int max = Integer.parseInt(partMaxText.getText());
            int machineId;
            String companyName;
            boolean partAddSuccessful = false;

            if (name.isEmpty()) {
                displayAlert(5);
            } else {
                if (minValid(min, max) && inventoryValid(min, max, stock)) {

                    if (inHouseRadioButton.isSelected()) {
                        try {
                            machineId = Integer.parseInt(partIdNameText.getText());
                            InHouse newInHousePart = new InHouse(id, name, price, stock, min, max, machineId);
                            newInHousePart.setId(Inventory.getNewPartId());
                            Inventory.addPart(newInHousePart);
                            partAddSuccessful = true;
                        } catch (Exception e) {
                            displayAlert(2);
                        }
                    }

                    if (outsourcedRadioButton.isSelected()) {
                        companyName = partIdNameText.getText();
                        Outsourced newOutsourcedPart = new Outsourced(id, name, price, stock, min, max,
                                companyName);
                        newOutsourcedPart.setId(Inventory.getNewPartId());
                        Inventory.addPart(newOutsourcedPart);
                        partAddSuccessful = true;
                    }

                    if (partAddSuccessful) {
                        returnToMainScreen(event);
                    }
                }
            }
        } catch(Exception e) {
            displayAlert(1);
        }
    }

    /**
     * Returns to main screen
     * @param event Passed from parent method
     * @throws IOException From FXMLLoader
     */
    private void returnToMainScreen(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/MainScreen.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Verifies min
     * @param min The min
     * @param max The max
     */
    private boolean minValid(int min, int max) {

        boolean isValid = true;

        if (min <= 0 || min >= max) {
            isValid = false;
            displayAlert(3);
        }

        return isValid;
    }

    /**
     * Verifies min
     * @param min The min
     * @param max The max
     * @param stock The inventory
     */
    private boolean inventoryValid(int min, int max, int stock) {

        boolean isValid = true;

        if (stock < min || stock > max) {
            isValid = false;
            displayAlert(4);
        }

        return isValid;
    }

    /**
     * Alert messages
     * @param alertType Alert message select
     */
    private void displayAlert(int alertType) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        switch (alertType) {
            case 1:
                alert.setTitle("Error Adding Part");
                alert.setContentText("Form contains blank fields or invalid values.");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Invalid");
                alert.setContentText("Machine ID may only contain numbers.");
                alert.showAndWait();
                break;
            case 3:
                alert.setTitle("Invalid");
                alert.setContentText("Min must be higher than 0 and less than the max");
                alert.showAndWait();
                break;
            case 4:
                alert.setTitle("Invalid");
                alert.setContentText("Inventory needs to be a number equal to or between the Min and Max.");
                alert.showAndWait();
                break;
            case 5:
                alert.setTitle("Error");
                alert.setContentText("Name cannot be empty.");
                alert.showAndWait();
                break;
        }
    }

    /**
     * Starts form with in house selected
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        inHouseRadioButton.setSelected(true);
    }
}