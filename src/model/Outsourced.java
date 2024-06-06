package model;

public class Outsourced extends Part {
    private String companyName;

    /**
     * This is the class to describe parts that are outsourced.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * This is the getter.
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * This is the setter.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}