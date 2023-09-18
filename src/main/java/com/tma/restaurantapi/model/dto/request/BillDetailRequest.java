package com.tma.restaurantapi.model.dto.request;

import lombok.Builder;
import lombok.Data;

/**
 * Represent Bill Detail from request body
 */
@Data
@Builder
public class BillDetailRequest {

    private int quantity;
    private Long menuId;

}
