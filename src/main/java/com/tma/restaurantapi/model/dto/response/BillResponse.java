package com.tma.restaurantapi.model.dto.response;

import com.tma.restaurantapi.model.BillStatus;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Collection;

/**
 * Represent Bill response object
 */
@Data
public class BillResponse {

    private Long id;
    private double total;
    private BillStatus billStatus;
    private Collection<BillDetailResponse> billDetails;
    private Timestamp createdDate;
    private Timestamp lastModifiedDate;

}
