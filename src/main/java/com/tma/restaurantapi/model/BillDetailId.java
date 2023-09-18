package com.tma.restaurantapi.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Embeddable
public class BillDetailId implements Serializable {

    private Long billId;
    private Long menuId;

}
