package com.spectrographix.monadie.classes;

import java.io.Serializable;

/**
 * Created by user name on 2/8/2018.
 */

public class Auction implements Serializable {

    private String auctionId;
    private String basePrice;
    private String bidPrice;
    private String startTime;
    private String endTime;
    private String lastBidUser;

    private String auctionProductName;
    private String auctionProductDescription;
    private String auctionProductPrice;
    private String auctionProductOwner;
    private String auctionProductImage;
    private String auctionProductCategory;

    private String auctionProductImageString;
    private String auctionProductImageFileName;

    public Auction(String auctionId, String auctionProductName,String auctionProductDescription,String auctionProductPrice, String auctionProductOwner, String auctionProductImage,String basePrice, String startTime, String endTime)
    {
        this.auctionId = auctionId;
        this.auctionProductName = auctionProductName;
        this.auctionProductDescription = auctionProductDescription;
        this.auctionProductPrice = auctionProductPrice;
        this.auctionProductImage = auctionProductImage;
        this.basePrice = basePrice;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Auction(String auctionProductName, String auctionProductPrice, String auctionProductDescription, String auctionProductCategory,String auctionProductImageString, String auctionProductImageFileName, String auctionProductOwner, String basePrice, String startTime, String endTime)
    {
        this.auctionProductName = auctionProductName;
        this.auctionProductPrice = auctionProductPrice;
        this.auctionProductDescription = auctionProductDescription;
        this.auctionProductCategory = auctionProductCategory;
        this.auctionProductImageString = auctionProductImageString;
        this.auctionProductImageFileName = auctionProductImageFileName;
        this.auctionProductOwner = auctionProductOwner;
        this.basePrice = basePrice;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Auction(String auctionId, String productId, String basePrice, String bidPrice ,String startTime, String endTime, String lastBidUser)
    {
        this.auctionId = auctionId;
        this.basePrice = basePrice;
        this.bidPrice = bidPrice;
        this.startTime = startTime;
        this.endTime = endTime;
        this.lastBidUser = lastBidUser;
    }

    public Auction()
    {
    }

    public String getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(String auctionId) {
        this.auctionId = auctionId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


    public String getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(String basePrice) {
        this.basePrice = basePrice;
    }

    public String getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(String bidPrice) {
        this.bidPrice = bidPrice;
    }

    public String getLastBidUser() {
        return lastBidUser;
    }

    public void setLastBidUser(String lastBidUser) {
        this.lastBidUser = lastBidUser;
    }

    public String getAuctionProductName() {
        return auctionProductName;
    }

    public void setAuctionProductName(String auctionProductName) {
        this.auctionProductName = auctionProductName;
    }

    public String getAuctionProductImage() {
        return Url.PATH_MONGO_PRODUCT_IMAGE+auctionProductImage;
    }

    public void setAuctionProductImage(String auctionProductImage) {
        this.auctionProductImage = auctionProductImage;
    }

    public String getAuctionProductPrice() {
        return auctionProductPrice;
    }

    public void setAuctionProductPrice(String auctionProductPrice) {
        this.auctionProductPrice = auctionProductPrice;
    }

    public String getAuctionProductDescription() {
        return auctionProductDescription;
    }

    public void setAuctionProductDescription(String auctionProductDescription) {
        this.auctionProductDescription = auctionProductDescription;
    }

    public String getAuctionProductImageString() {
        return auctionProductImageString;
    }

    public void setAuctionProductImageString(String auctionProductImageString) {
        this.auctionProductImageString = auctionProductImageString;
    }

    public String getAuctionProductImageFileName() {
        return auctionProductImageFileName;
    }

    public void setAuctionProductImageFileName(String auctionProductImageFileName) {
        this.auctionProductImageFileName = auctionProductImageFileName;
    }

    public String getAuctionProductCategory() {
        return auctionProductCategory;
    }

    public void setAuctionProductCategory(String auctionProductCategory) {
        this.auctionProductCategory = auctionProductCategory;
    }

    public String getAuctionProductOwner() {
        return auctionProductOwner;
    }

    public void setAuctionProductOwner(String auctionProductOwner) {
        this.auctionProductOwner = auctionProductOwner;
    }
}