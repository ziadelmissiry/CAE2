package com.example.cae2;

public class pdfclass {

    public String name,url;
// empty constructor
    public pdfclass(){}

    // constructor

    public pdfclass(String name, String url) {
        this.name = name;
        this.url = url;
    }

    //getters & setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
