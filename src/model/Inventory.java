package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This is the class for inventory
 * Stores parts and products in observable ArrayLists
 */
public class Inventory {

    private static int partId = 0;
    private static int productId = 0;

    /**
     * This list is for parts in inventory
     */
    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**
     * This list is for products in inventory
     */
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Retrieves list of all parts
     * @return list of parts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Retrieves list of all products
     * @return List of products
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * Adds part to inventory
     * @param newPart Part to add
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Adds product to inventory
     * @param newProduct Product to add.
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Creates a part ID
     * @return Unique part ID
     */
    public static int getNewPartId() {
        return ++partId;
    }

    /**
     * Creates a product ID
     * @return Unique product ID
     */
    public static int getNewProductId() {
        return ++productId;
    }

    /**
     * Search list of parts by ID
     * @param partId The part ID
     * @return Null if not found, part object if it is found
     */
    public static Part lookupPart(int partId) {
        Part partFound = null;

        for (Part part : allParts) {
            if (part.getId() == partId) {
                partFound = part;
            }
        }

        return partFound;
    }

    /**
     * Search list of parts by name
     * @param partName The part name
     * @return List of parts found
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> partsFound = FXCollections.observableArrayList();

        for (Part part : allParts) {
            if (part.getName().equals(partName)) {
                partsFound.add(part);
            }
        }

        return partsFound;
    }

    /**
     * Search list of products by ID
     * @param productId The product ID
     * @return Null if not found, product if found
     */
    public static Product lookupProduct(int productId) {
        Product productFound = null;

        for (Product product : allProducts) {
            if (product.getId() == productId) {
                productFound = product;
            }
        }

        return productFound;
    }

    /**
     * Search list of products by name
     * @param productName The product name
     * @return List of products found
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> productsFound = FXCollections.observableArrayList();

        for (Product product : allProducts) {
            if (product.getName().equals(productName)) {
                productsFound.add(product);
            }
        }

        return productsFound;
    }

    /**
     * Updates part in parts list
     * @param index Index of part being replaced
     * @param selectedPart Part being used in place of
     */
    public static void updatePart (int index, Part selectedPart) {

        allParts.set(index, selectedPart);
    }

    /**
     * Updates product in products list
     * @param index Index of product being replaced
     * @param selectedProduct Product being used in place of
     */
    public static void updateProduct (int index, Product selectedProduct) {

        allProducts.set(index, selectedProduct);
    }

    /**
     * Deletes part from parts list
     *
     * @param selectedPart Part being deleted
     */
    public static void deletePart(Part selectedPart) {
        allParts.remove(selectedPart);
    }

    /**
     * Deletes product from product list
     *
     * @param selectedProduct Product being deleted
     */
    public static void deleteProduct(Product selectedProduct) {
        allProducts.remove(selectedProduct);
    }
}