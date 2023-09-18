package com.tma.restaurantapi.model.dto.response;

import lombok.Data;

/**
 * Represent Bill Detail response object
 */
@Data
public class BillDetailResponse {

    private String name;
    private int quantity;
    private double price;

}
