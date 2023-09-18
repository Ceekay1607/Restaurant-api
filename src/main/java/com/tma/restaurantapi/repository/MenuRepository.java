package com.tma.restaurantapi.repository;

import com.tma.restaurantapi.model.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The MenuRepository interface is responsible for providing database access and operations related to the Menu entity.
 * It extends the JpaRepository interface, which provides generic CRUD (Create, Read, Update, Delete) operations for the Menu entity.
 */
@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    @Query("SELECT m FROM Menu m WHERE (m.name LIKE %:keyword% OR m.description LIKE %:keyword% OR m.type LIKE %:keyword%) AND m.status = 0")
    List<Menu> findByKeyword(@Param("keyword") String keyword);

    @Query("SELECT m FROM Menu m WHERE m.status=0")
    Page<Menu> findAllMenu(Pageable pageable);

    Optional<Menu> findByName(String name);
}
