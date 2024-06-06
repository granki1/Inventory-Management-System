package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This is the product class, based upon the UML diagram.
 */
public class Product {

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * RUNTIME ERROR
     * This error is a small one, and it took me longer to realize the mistake that I'd care to admit.
     * I received an ArrayStoreException due to trying to save a cost with decimals when in the string it was declared as an int.
     * It was an easy fix, changing it to double to allow two decimal spaces to be saved, in the case a part/product cost does not round to a whole number.
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Id getter
     * @return ID of product
     */
    public int getId() {
        return id;
    }

    /**
     * Id setter
     * @param id ID of product
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Name getter
     * @return Name of product
     */
    public String getName() {
        return name;
    }

    /**
     * Name setter
     * @param name Name product
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Price getter
     * @return Price of product
     */
    public double getPrice() {
        return price;
    }

    /**
     * Price setter
     * @param price Price of product
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Stock getter
     * @return Stock of product
     */
    public int getStock() {
        return stock;
    }

    /**
     * Stock setter
     * @param stock Stock of product
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Min getter
     * @return Min of product
     */
    public int getMin() {
        return min;
    }

    /**
     * Min setter
     * @param min Min of product
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Max getter
     * @return Max of product
     */
    public int getMax() {
        return max;
    }

    /**
     * Max setter
     * @param max Max of product
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Adds a part to the list
     * @param part Part that is added
     */
    public void  addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * Removes part from part list of product
     * @param selectedAssociatedPart The part being removed
     * @return boolean of status of part removal
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        if (associatedParts.contains(selectedAssociatedPart)) {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        else
            return false;
    }

    /**
     * Retrieves the list of parts for the product.
     * @return List of parts
     */
    public ObservableList<Part> getAllAssociatedParts() {return associatedParts;}
}