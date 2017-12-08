package com.marketplace.model;

import javax.persistence.*;

@Entity
@Table(name = "BIDS")
public class User {

    @Id
    @Column(name = "BID_ID")
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "USERS_SEQ")
    @SequenceGenerator(name = "USERS_SEQ", sequenceName = "USERS_SEQ", allocationSize=1)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "BILLING_ADDRESS")
    private String billingAddress;

    @Column(name = "LOGIN")
    private String login;

    @Column(name = "PASSWORD")
    private String password;

    public User( String login,String password,String billingAddress, String name ) {
        this.name = name;
        this.billingAddress = billingAddress;
        this.login = login;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}



