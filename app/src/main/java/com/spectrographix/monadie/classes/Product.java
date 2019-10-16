package com.spectrographix.monadie.classes;

import java.io.Serializable;

/**
 * Created by user name on 2/8/2018.
 */

public class Product implements Serializable {

    private String productId;
    private String productName;
    private String productImage;
    private String productDescription;
    private String productPrice;
    private String productImageUrl;
    private String productOwner;
    private String productCategoryId;

    public Product(String productId, String productName, String productDescription, String productPrice, String productOwner, String productImage)
    {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.productOwner = productOwner;
        this.productImage = productImage;
    }

    public Product(String productId, String productCategoryId, String productName, String productDescription, String productPrice, String productOwner, String productImage)
    {
        this.productId = productId;
        this.productCategoryId = productCategoryId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.productOwner = productOwner;
        this.productImage = productImage;
    }

    public Product()
    {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductImageUrl() {
        productImageUrl = Url.PATH_MONGO_PRODUCT_IMAGE + productImage;
        return productImageUrl;
    }

    public String getProductOwner() {
        return productOwner;
    }

    public void setProductOwner(String productOwner) {
        this.productOwner = productOwner;
    }

    public String getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(String productCategoryId) {
        this.productCategoryId = productCategoryId;
    }
}
