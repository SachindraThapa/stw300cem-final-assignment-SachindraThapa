package com.sachindra.futsalbook.model;

public class Category {
    String contents, name;

    public Category(String contents, String name) {
        this.contents = contents;
        this.name = name;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getName() {
        return name;
    }
}
