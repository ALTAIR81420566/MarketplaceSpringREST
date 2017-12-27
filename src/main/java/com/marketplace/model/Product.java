package com.marketplace.model;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "PRODUCTS")
public class Product {

    @Id
    @Column(name = "PRODUCT_ID")
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "PRODUCTS_SEQ")
    @SequenceGenerator(name = "PRODUCTS_SEQ", sequenceName = "PRODUCTS_SEQ", allocationSize=1)
    private Long uID;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "START_PRICE")
    private double startPrice;

    @Column(name = "START_BIDDING_DATE")
    private Long startBiddingDate = new Date().getTime();

    @Column(name = "END_DATE")
    private Long endBiddingDate = new Date().getTime();

    @Column(name = "TIME")
    private Long time;

    @Column(name = "BID_STEP")
    private double step;

    @Column(name = "IS_BUY_NOW")
    private int buyNow;

    @Column(name = "SELLER_ID")
    private long sellerID;

    @Column(name = "IS_SOLD")
    private int sold;


    public Product(String title,String description,
                   double startPrice,double step) {
        this.description = description;
        this.title = title;
        this.startPrice = startPrice;
        this.step = step;
    }

    public Product(){

    };


    public Long getEndBiddingDate() {
        return endBiddingDate;
    }

    public void setEndBiddingDate(Long endBiddingDate) {
        this.endBiddingDate = endBiddingDate;
    }

    public long getTimeMillis(){
        return  time * 60 * 60 * 1000;
    }
    public void setuID(Long uID) {
        this.uID = uID;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public long getSellerID() {
        return sellerID;
    }

    public void setSellerID(long sellerID) {
        this.sellerID = sellerID;
    }

    public Long getStartBiddingDate() {
        return startBiddingDate;
    }

    public void setStartBiddingDate(Long startBiddingDate) {
        this.startBiddingDate = startBiddingDate;
    }

    public Long getuID() {
        return uID;
    }

    public void setuID(long uID) {
        this.uID = uID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(double startPrice) {
        this.startPrice = startPrice;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        this.step = step;
    }

    public int getBuyNow() {
        return buyNow;
    }

    public void setIsBuyNow(int isBuyNow) {
        if(isBuyNow == 0 || isBuyNow == 1){
            this.buyNow = isBuyNow;
        }
    }

//    public void setTimeMillis(long aLong) {
//        time = aLong / 1000 /60 / 60;
//        this.stopDate = aLong + startBiddingDate;
//    }

}
