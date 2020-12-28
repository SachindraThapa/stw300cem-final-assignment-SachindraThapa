package com.sachindra.futsalbook.model;

public class PriceModel {
    String daytype, daypart, price;

    public PriceModel(String daytype, String daypart, String price) {
        this.daytype = daytype;
        this.daypart = daypart;
        this.price = price;
    }

    public String getDaytype() {
        return daytype;
    }

    public void setDaytype(String daytype) {
        this.daytype = daytype;
    }

    public String getDaypart() {
        return daypart;
    }

    public void setDaypart(String daypart) {
        this.daypart = daypart;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
