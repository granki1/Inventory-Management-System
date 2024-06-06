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
 * This class provides the ability to manipulate the main screen
 */
public class MainScreenController implements Initializable {

    private static Part partToModify;
    private static Product productToModify;
    @FXML private TextField partSearchText;
    @FXML private TableView<Part> partTableView;
    @FXML private TableColumn<Part, Integer> partIdColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Integer> partInventoryColumn;
    @FXML private TableColumn<Part, Double> partPriceColumn;
    @FXML private TextField productSearchText;
    @FXML private TableView<Product> productTableView;
    @FXML private TableColumn<Product, Integer> productIdColumn;
    @FXML private TableColumn<Product, String> productNameColumn;
    @FXML private TableColumn<Product, Integer> productInventoryColumn;
    @FXML private TableColumn<Product, Double> productPriceColumn;

    /**
     * Gets the part that is selected
     * @return A part or null if nothing is selected
     */
    public static Part getPartToModify() {
        return partToModify;
    }

    /**
     * Gets the product that is selected
     * @return A product or null if nothing is selected
     */
    public static Product getProductToModify() {
        return productToModify;
    }

    /**
     * @param event Exit button action.
     */
    @FXML void exitButtonAction(ActionEvent event) {

        System.exit(0);
    }

    /**
     * Loads to the add part screen
     * @param event Add button
     * @throws IOException From FXMLLoader
     */
    @FXML void partAddAction(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/AddPartScreen.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Deletes the selected part
     * Confirmation will appear for the user
     * @param event Part delete
     */
    @FXML void partDeleteAction(ActionEvent event) {

        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            displayAlert(3);
        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Do you want to delete the selected part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(selectedPart);
            }
        }
    }

    /**
     * Loads to the modify part screen
     * Will not work unless a part is selected
     * @param event Part modify
     * @throws IOException From FXMLLoader
     */
    @FXML void partModifyAction(ActionEvent event) throws IOException {

        partToModify = partTableView.getSelectionModel().getSelectedItem();

        if (partToModify == null) {
            displayAlert(3);
        } else {
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/ModifyPartScreen.fxml")));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Parts can be searched by name or ID
     * @param event Part search
     */
    @FXML void partSearchBtnAction(ActionEvent event) {

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
     * Resets part table after search
     * @param event Parts search
     */
    @FXML void partSearchTextKeyPressed(KeyEvent event) {

        if (partSearchText.getText().isEmpty()) {
            partTableView.setItems(Inventory.getAllParts());
        }

    }

    /**
     * Loads to add product screen
     * @param event Add product
     * @throws IOException From FXMLLoader
     */
    @FXML void productAddAction(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/AddProductScreen.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Removes the selected product
     * A product must be selected and not have an associated part, user will have to confirm
     * @param event Product delete
     */
    @FXML void productDeleteAction(ActionEvent event) {

        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            displayAlert(4);
        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Do you want to delete the selected product?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {

                ObservableList<Part> assocParts = selectedProduct.getAllAssociatedParts();

                if (!assocParts.isEmpty()) {
                    displayAlert(5);
                } else {
                    Inventory.deleteProduct(selectedProduct);
                }
            }
        }
    }

    /**
     * Loads to modify product screen
     * A product must be selected
     * @param event Product modify
     * @throws IOException From FXMLLoader
     */
    @FXML void productModifyAction(ActionEvent event) throws IOException {

        productToModify = productTableView.getSelectionModel().getSelectedItem();

        if (productToModify == null) {
            displayAlert(4);
        } else {
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/ModifyProductScreen.fxml")));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * FUTURE ENHANCEMENT
     * The future enhancement is a simple concept that I did not implement but can make life easier.
     * This search function currently is case-sensitive, meaning the user has to type exactly how the part/product is shown.
     * This can be a hassle as the list of parts/products becomes large and not everyone is as thorough with spelling/grammar.
     * Implementing a feature that allows searches to not be case-sensitive would be helpful for users.
     */

    /**
     * Products can be searched for by ID or name.
     * @param event Part search button
     */
    @FXML void productSearchBtnAction(ActionEvent event) {

        ObservableList<Product> allProducts = Inventory.getAllProducts();
        ObservableList<Product> productsFound = FXCollections.observableArrayList();
        String searchString = productSearchText.getText();

        for (Product product : allProducts) {
            if (String.valueOf(product.getId()).contains(searchString) ||
                    product.getName().contains(searchString)) {
                productsFound.add(product);
            }
        }

        productTableView.setItems(productsFound);

        if (productsFound.isEmpty()) {
            displayAlert(2);
        }
    }

    /**
     * Resets the product table after search is empty
     * @param event Products search
     */
    @FXML void productSearchTextKeyPressed(KeyEvent event) {

        if (productSearchText.getText().isEmpty()) {
            productTableView.setItems(Inventory.getAllProducts());
        }
    }

    /**
     * Alert messages
     * @param alertType Alert message
     */
    private void displayAlert(int alertType) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Alert alertError = new Alert(Alert.AlertType.ERROR);

        switch (alertType) {
            case 1:
                alert.setTitle("Error");
                alert.setHeaderText("Part Not found");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Error");
                alert.setHeaderText("Product Not Found");
                alert.showAndWait();
                break;
            case 3:
                alertError.setTitle("Error");
                alertError.setHeaderText("Please Select Part");
                alertError.showAndWait();
                break;
            case 4:
                alertError.setTitle("Error");
                alertError.setHeaderText("Please Select Product");
                alertError.showAndWait();
                break;
            case 5:
                alertError.setTitle("Error");
                alertError.setHeaderText("Has Associated Parts");
                alertError.setContentText("Product must have no associated parts in order to delete");
                alertError.showAndWait();
                break;
        }
    }

    /**
     * Populates both parts and products tables
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        partTableView.setItems(Inventory.getAllParts());
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTableView.setItems(Inventory.getAllProducts());
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}