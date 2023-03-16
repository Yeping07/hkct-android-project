package com.hkct.project.Model;

import java.util.Date;

public class Product extends ProductId {

    private String product_image, product_name, product_price, product_comment;
    private Date time;

    public String getImage() {
        return product_image;
    }

    public String getProductName() {
        return product_name;
    }

    public String getProductPrice() {
        return product_price;
    }

    public String getProductComment() {
        return product_comment;
    }

    public Date getTime() {
        return time;
    }
}
