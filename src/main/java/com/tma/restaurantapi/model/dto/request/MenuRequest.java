package com.tma.restaurantapi.model.dto.request;

import lombok.Builder;

/**
 * Represent Menu in request body
 */
@Builder
public class MenuRequest {

    private String name = "Empty";
    private String description = "Empty";
    private String image = "Empty";
    private double price;
    private String type = "Empty";

    public MenuRequest(String name, String description, String image, double price, String type) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
        this.type = type;
    }

    public MenuRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
