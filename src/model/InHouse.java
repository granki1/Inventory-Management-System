package model;
public class InHouse extends Part {

    /**
     * This is the class to describe parts that are InHouse.
     */
    private int machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * This is the getter.
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * This is the setter.
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}