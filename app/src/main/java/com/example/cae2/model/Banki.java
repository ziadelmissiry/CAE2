package com.example.cae2.model;

public class Banki {
    private String userId;
    private String email;
    private String pdf_name_create;
    private String pdf_url;



    public Banki(){

        //emty constructor
    }

    public Banki(String userId, String email, String pdf_name_create, String pdf_url) {
        this.userId = userId;
        this.email = email;
        this.pdf_name_create = pdf_name_create;
        this.pdf_url = pdf_url;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPdf_name_create() {
        return pdf_name_create;
    }

    public void setPdf_name_create(String pdf_name_create) {
        this.pdf_name_create = pdf_name_create;
    }

    public String getPdf_url() {
        return pdf_url;
    }

    public void setPdf_url(String pdf_url) {
        this.pdf_url = pdf_url;
    }
}
