package com.tma.restaurantapi.repository;

import com.tma.restaurantapi.model.BillDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * The BillDetailRepository interface is responsible for providing database access and operations related to the BillDetail entity.
 * It extends the JpaRepository interface, which provides generic CRUD (Create, Read, Update, Delete) operations for the Menu entity.
 */
@Repository
public interface BillDetailRepository extends JpaRepository<BillDetail, Long> {

    @Modifying
    @Query("DELETE FROM BillDetail b WHERE b.bill.id=?1 AND b.menu.id=?2")
    @Transactional
    void deleteBillDetail(long billId, long menuId);

}
