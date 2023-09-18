package com.tma.restaurantapi.model;

import com.tma.restaurantapi.constant.ApplicationConstant;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.sql.Timestamp;
import java.util.Collection;

/**
 * Represent a Bill
 */
@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double total;
    private BillStatus billStatus;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Collection<BillDetail> billDetails;

    @CreatedDate
    @Column(name = ApplicationConstant.createdDateColumnName)
    private Timestamp createdDate;

    @LastModifiedDate
    @Column(name = ApplicationConstant.lastModifiedDate)
    private Timestamp lastModifiedDate;

}
