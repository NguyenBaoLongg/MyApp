package com.example.project_lthdt.model;

public class GetDataProduct {
    int id;
    String name;
    int price ;
    String image;
    String classify;

    public GetDataProduct(int id, String name, int price, String image, String classify) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.classify = classify;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
