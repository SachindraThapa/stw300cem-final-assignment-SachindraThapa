package com.sachindra.futsalbook.model;

public class CartModel {
    private String _id;
    private Product products;
    private String delivered;
    private int quantity;

    public CartModel(String _id, Product products, String delivered, int quantity) {
        this._id = _id;
        this.products = products;
        this.delivered = delivered;
        this.quantity = quantity;
    }

    public CartModel(Product products, String delivered, int quantity) {
        this.products = products;
        this.delivered = delivered;
        this.quantity = quantity;
    }

    public String get_id() {
        return _id;
    }

    public Product getProducts() {
        return products;
    }

    public String getDelivered() {
        return delivered;
    }

    public int getQuantity() {
        return quantity;
    }
}
