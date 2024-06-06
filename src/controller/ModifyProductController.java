package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class provides the ability to manipulate the product inventory
 */
public class ModifyProductController implements Initializable {

    Product selectedProduct;

    private ObservableList<Part> assocParts = FXCollections.observableArrayList();

    @FXML private TableView<Part> assocPartTableView;
    @FXML private TableColumn<Part, Integer> assocPartIdColumn;
    @FXML private TableColumn<Part, String> assocPartNameColumn;
    @FXML private TableColumn<Part, Integer> assocPartInventoryColumn;
    @FXML private TableColumn<Part, Double> assocPartPriceColumn;
    @FXML private TableView<Part> partTableView;
    @FXML private TableColumn<Part, Integer> partIdColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Integer> partInventoryColumn;
    @FXML private TableColumn<Part, Double> partPriceColumn;
    @FXML private TextField partSearchText;
    @FXML private TextField productIdText;
    @FXML private TextField productNameText;
    @FXML private TextField productInventoryText;
    @FXML private TextField productPriceText;
    @FXML private TextField productMaxText;
    @FXML private TextField productMinText;

    /**
     * Adds part from all parts table to associate parts table
     * Must have part selected
     * @param event Add button
     */
    @FXML void addButtonAction(ActionEvent event) {

        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            displayAlert(5);
        } else {
            assocParts.add(selectedPart);
            assocPartTableView.setItems(assocParts);
        }
    }

    /**
     * User confirms and returns to main screen
     * @param event Cancel button
     * @throws IOException From FXMLLoader
     */
    @FXML void cancelButtonAction(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Cancel changes and return to the main screen?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            returnToMainScreen(event);
        }
    }

    /**
     * User confirms to remove part
     * A part must be selected
     * @param event Remove button
     */
    @FXML void removeButtonAction(ActionEvent event) {

        Part selectedPart = assocPartTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            displayAlert(5);
        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Remove the selected part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                assocParts.remove(selectedPart);
                assocPartTableView.setItems(assocParts);
            }
        }
    }

    /**
     * New product is added and user is sent to main screen
     * All values are verified
     * @param event Save button
     * @throws IOException From FXMLLoader
     */
    @FXML void saveButtonAction(ActionEvent event) throws IOException {

        try {
            int id = selectedProduct.getId();
            String name = productNameText.getText();
            double price = Double.parseDouble(productPriceText.getText());
            int stock = Integer.parseInt(productInventoryText.getText());
            int min = Integer.parseInt(productMinText.getText());
            int max = Integer.parseInt(productMaxText.getText());

            if (name.isEmpty()) {
                displayAlert(7);
            } else {
                if (minValid(min, max) && inventoryValid(min, max, stock)) {

                    Product newProduct = new Product(id, name, price, stock, min, max);

                    for (Part part : assocParts) {
                        newProduct.addAssociatedPart(part);
                    }

                    Inventory.addProduct(newProduct);
                    Inventory.deleteProduct(selectedProduct);
                    returnToMainScreen(event);
                }
            }
        } catch (Exception e){
            displayAlert(1);
        }
    }

    /**
     * Parts can be searched by name or ID
     * @param event Part search button
     */
    @FXML void searchBtnAction(ActionEvent event) {

        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> partsFound = FXCollections.observableArrayList();
        String searchString = partSearchText.getText();

        for (Part part : allParts) {
            if (String.valueOf(part.getId()).contains(searchString) ||
                    part.getName().contains(searchString)) {
                partsFound.add(part);
            }
        }

        partTableView.setItems(partsFound);

        if (partsFound.isEmpty()) {
            displayAlert(1);
        }
    }

    /**
     * Resets part table with empty search
     * @param event Parts search
     */
    @FXML void searchKeyPressed(KeyEvent event) {

        if (partSearchText.getText().isEmpty()) {
            partTableView.setItems(Inventory.getAllParts());
        }
    }

    /**
     * Loads the main screen
     * @param event Main Screen Return
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
     * Verifies min is >0 and <Max
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
     * Verifies that inventory level is ≤ or between min and max
     * @param min The min
     * @param max The max
     * @param stock The inventory level
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
     * @param alertType Alert message
     */
    private void displayAlert(int alertType) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);

        switch (alertType) {
            case 1:
                alert.setTitle("Error");
                alert.setHeaderText("Error Modifying Product");
                alert.setContentText("Form contains blank fields or invalid values");
                alert.showAndWait();
                break;
            case 2:
                alertInfo.setTitle("Error");
                alertInfo.setHeaderText("Part not found");
                alertInfo.showAndWait();
                break;
            case 3:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid value for Min");
                alert.setContentText("Min must be greater than 0 and less than Max");
                alert.showAndWait();
                break;
            case 4:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid value for Inventory");
                alert.setContentText("Inventory must be equal to or between Min and Max");
                alert.showAndWait();
                break;
            case 5:
                alert.setTitle("Error");
                alert.setHeaderText("Part not selected");
                alert.showAndWait();
                break;
            case 7:
                alert.setTitle("Error");
                alert.setHeaderText("Name Empty");
                alert.setContentText("Name cannot be empty");
                alert.showAndWait();
                break;
        }
    }

    /**
     * Populates tables
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        selectedProduct = MainScreenController.getProductToModify();
        assocParts = selectedProduct.getAllAssociatedParts();

        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        partTableView.setItems(Inventory.getAllParts());

        assocPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocPartInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        assocPartTableView.setItems(assocParts);

        productIdText.setText(String.valueOf(selectedProduct.getId()));
        productNameText.setText(selectedProduct.getName());
        productInventoryText.setText(String.valueOf(selectedProduct.getStock()));
        productPriceText.setText(String.valueOf(selectedProduct.getPrice()));
        productMaxText.setText(String.valueOf(selectedProduct.getMax()));
        productMinText.setText(String.valueOf(selectedProduct.getMin()));
    }
}