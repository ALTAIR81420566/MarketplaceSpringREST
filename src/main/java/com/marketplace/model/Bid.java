package com.marketplace.model;

import javax.persistence.*;


@Entity
@Table(name = "BIDS")
public class Bid {

    @Id
    @Column(name = "BID_ID")
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "BIDS_SEQ")
    @SequenceGenerator(name = "BIDS_SEQ", sequenceName = "BIDS_SEQ", allocationSize=1)
    private Long id;

    @Column(name = "BID")
    private double count;

    @Column(name = "USER_ID")
    private long userId;

    @Column(name = "PRODUCT_ID")
    private long productId;

    public Bid(double count, long userId, long productId) {
        this.count = count;
        this.userId = userId;
        this.productId = productId;
    }

    public Bid() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }
}
