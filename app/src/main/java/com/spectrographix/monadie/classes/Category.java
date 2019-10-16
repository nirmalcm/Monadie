package com.spectrographix.monadie.classes;

import java.util.List;

/**
 * Created by user name on 2/28/2018.
 */

public class Category {

    private String categoryId;
    private String categoryImage;
    private String categoryName;
    private String categoryParent;
    private String categoryImageUrl;

    private List<Product> categoryProducts;

    public Category()
    {
    }

    public Category(String categoryId, String categoryName, List<Product> categoryProducts)
    {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryProducts = categoryProducts;
    }

    public Category(String categoryId, String categoryName,String categoryImage,String categoryParent, List<Product> categoryProducts)
    {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryImage = categoryImage;
        this.categoryParent = categoryParent;
        this.categoryProducts = categoryProducts;
    }

    public Category(String categoryId, String categoryName,String categoryImage,String categoryParent)
    {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryImage = categoryImage;
        this.categoryParent = categoryParent;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public List<Product> getCategoryProducts() {
        return categoryProducts;
    }

    public void setCategoryProducts(List<Product> categoryProducts) {
        this.categoryProducts = categoryProducts;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public String getCategoryParent() {
        return categoryParent;
    }

    public void setCategoryParent(String categoryParent) {
        this.categoryParent = categoryParent;
    }

    public String getCategoryImageUrl() {
        categoryImageUrl = Url.PATH_MONGO_CATEGORY_IMAGE + categoryImage;
        return categoryImageUrl;
    }
}