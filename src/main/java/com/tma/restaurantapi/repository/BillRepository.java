package com.tma.restaurantapi.repository;

import com.tma.restaurantapi.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The BillRepository interface is responsible for providing database access and operations related to the Bill entity.
 * It extends the JpaRepository interface, which provides generic CRUD (Create, Read, Update, Delete) operations for the Menu entity.
 */
@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
}
