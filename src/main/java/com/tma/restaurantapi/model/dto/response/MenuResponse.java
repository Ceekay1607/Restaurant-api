package com.tma.restaurantapi.model.dto.response;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Represent menu in response object
 */
@Data
public class MenuResponse {

    private long id;
    private String name;
    private String description;
    private String image;
    private double price;
    private String type;
    private Timestamp createdDate;
    private Timestamp lastModifiedDate;

}
