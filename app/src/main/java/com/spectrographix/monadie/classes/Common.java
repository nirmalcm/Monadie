package com.spectrographix.monadie.classes;

/**
 * Created by user name on 2/28/2018.
 */

public class Common {

    private int dummyId;
    private String dummyName;
    private String dummyDescription;
    private int dummyImage;

    private Double biddingPrice;
    private String biddingUser;

    // Google Console APIs developer key
    public static final String DEVELOPER_KEY = "AIzaSyBsosjnzX1Y8_tW_kC6ASCTCHGsXG9fJg0";

    // YouTube video id
    public static final String YOUTUBE_VIDEO_CODE = "_oEA18Y8gM0";

    public Common()
    {
    }

    public Common(int dummyId, String dummyName, String dummyDescription, int dummyImage)
    {
        this.dummyId = dummyId;
        this.dummyName = dummyName;
        this.dummyDescription = dummyDescription;
        this.dummyImage = dummyImage;
    }

    public String getDummyName() {
        return dummyName;
    }

    public void setDummyName(String dummyName) {
        this.dummyName = dummyName;
    }

    public int getDummyImage() {
        return dummyImage;
    }

    public void setDummyImage(int dummyImage) {
        this.dummyImage = dummyImage;
    }

    public int getDummyId() {
        return dummyId;
    }

    public void setDummyId(int dummyId) {
        this.dummyId = dummyId;
    }

    public String getDummyDescription() {
        return dummyDescription;
    }

    public void setDummyDescription(String dummyDescription) {
        this.dummyDescription = dummyDescription;
    }

    public Double getBiddingPrice() {
        return biddingPrice;
    }

    public void setBiddingPrice(Double biddingPrice) {
        this.biddingPrice = biddingPrice;
    }

    public String getBiddingUser() {
        return biddingUser;
    }

    public void setBiddingUser(String biddingUser) {
        this.biddingUser = biddingUser;
    }
}
