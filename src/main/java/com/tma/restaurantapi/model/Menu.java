package com.tma.restaurantapi.model;

import com.tma.restaurantapi.constant.ApplicationConstant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.util.Collection;

/**
 * Represent a menu
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private String image;

    @Column(name = "price")
    private double price;

    @Column(name = "type")
    private String type;

    @Column(name = "status")
    private MenuStatus status;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Collection<BillDetail> billDetails;

    @CreatedDate
    @Column(name = ApplicationConstant.createdDateColumnName)
    private Timestamp createdDate;

    @LastModifiedDate
    @Column(name = ApplicationConstant.lastModifiedDate)
    private Timestamp lastModifiedDate;

}
