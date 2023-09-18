package com.tma.restaurantapi.model;

import com.tma.restaurantapi.constant.ApplicationConstant;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;

/**
 * Represent an item in a Bill
 */
@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class BillDetail {

    @EmbeddedId
    private BillDetailId id = new BillDetailId();
    double price;
    private int quantity;

    @ManyToOne
    @MapsId("menuId")
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToOne
    @MapsId("billId")
    @JoinColumn(name = "bill_id")
    private Bill bill;

    @CreatedDate
    @Column(name = ApplicationConstant.createdDateColumnName)
    private Timestamp createdDate;

    @LastModifiedDate
    @Column(name = ApplicationConstant.lastModifiedDate)
    private Timestamp lastModifiedDate;

}
