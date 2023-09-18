package com.tma.restaurantapi.service;

import com.tma.restaurantapi.exception.ApiException;

import java.util.List;

/**
 * The RestaurantService interface defines operations for a generic service related to a restaurant entity.
 * @param <T>
 */
public interface RestaurantService<T> {

    /**
     * Retrieve all entities
     *
     * @return
     */
    List<T> findAll(Integer pageNo, Integer pageSize, String sortBy);

    /**
     * Retrieve an entity by given id
     *
     * @param id
     * @return
     */
    T findById(Long id) throws ApiException;

}
