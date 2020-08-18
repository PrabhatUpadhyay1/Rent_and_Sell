package com.prabhat.mainactivity.Model;

public class Home_model {

    private String name;
    private String description;
    private String price;
    private String imagelink;
    private String status;
    private String Email;
    private String id;
    private String Phone1;
    private String customer;

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }


    public String getUrl() {
        return imagelink;
    }

    public void setUrl(String imagelink) {
        this.imagelink = imagelink;
    }

    public String getImagelink() {
        return imagelink;
    }

    public void setImagelink(String imagelink) {
        this.imagelink = imagelink;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone1() {
        return Phone1;
    }

    public void setPhone1(String phone1) {
        Phone1 = phone1;
    }

    private Home_model(String name, String description, String price, String imagelink,
                       String status, String Email, String id, String Phone1, String customer) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.Email=Email;
        this.id=id;
        this.customer=customer;
        this.Phone1=Phone1;
    }
    public Home_model(){};

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescrition(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
