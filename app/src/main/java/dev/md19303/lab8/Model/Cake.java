package dev.md19303.lab8.Model;

public class Cake {
    private String _id;
    private String name;
    private String description;
    private int price;
    private int quantity;

    public Cake() {
    }

    public Cake(String _id, String description, String name, int price, int quantity) {
        this._id = _id;
        this.description = description;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
