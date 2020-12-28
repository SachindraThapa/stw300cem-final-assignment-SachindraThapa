package com.sachindra.futsalbook.model;

public class Product {
    private Category category_name;
    private String _id, stock, name, slug, brand, categoryx, description, image;

    private int discount, rating, price;

    public Product(Category category_name, String _id, String stock, String name, String slug, String brand, String categoryx, String description, String image, int discount, int rating, int price) {
        this.category_name = category_name;
        this._id = _id;
        this.stock = stock;
        this.name = name;
        this.slug = slug;
        this.brand = brand;
        this.categoryx = categoryx;
        this.description = description;
        this.image = image;
        this.discount = discount;
        this.rating = rating;
        this.price = price;
    }

    public Category getCategory_name() {
        return category_name;
    }

    public String get_id() {
        return _id;
    }

    public String getStock() {
        return stock;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public String getBrand() {
        return brand;
    }

    public String getCategoryx() {
        return categoryx;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public int getDiscount() {
        return discount;
    }

    public int getRating() {
        return rating;
    }

    public int getPrice() {
        return price;
    }
}
