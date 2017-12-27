package com.marketplace.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

public class AdvancedSearchParams {
    @Min(value = 1, message = "must be more than 1")
    private Long uId;
    private String title;

    private String description;

    @Min(value = 1, message = "must be more than 1")
    private Integer minPrice;

    @Min(value = 1, message = "must be more than 1")
    private Integer maxPrice;

    private Boolean buyNow;

    @Pattern(regexp = "([0-9]{2}/[0-9]{2}/[0-9]{4} [0-9]{2}:[0-9]{2})||([ ])", message = "invalid format")
    private String startDate;

    @Pattern(regexp = "([0-9]{2}/[0-9]{2}/[0-9]{4} [0-9]{2}:[0-9]{2})||([ ])", message = "invalid format")
    private String expireDate;

    @Min(value = 1, message = "must be more than 1")
    private Integer bidCount;

    public Long getuId() {
        return uId;
    }

    public void setuId(Long uId) {
        this.uId = uId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Boolean getBuyNow() {
        return buyNow;
    }

    public void setBuyNow(Boolean buyNow) {
        this.buyNow = buyNow;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public Integer getBidCount() {
        return bidCount;
    }

    public void setBidCount(Integer bidCount) {
        this.bidCount = bidCount;
    }
}
