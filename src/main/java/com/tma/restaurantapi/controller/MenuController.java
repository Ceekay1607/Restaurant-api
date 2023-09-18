package com.tma.restaurantapi.controller;

import com.tma.restaurantapi.constant.ApiHandlingMessage;
import com.tma.restaurantapi.model.dto.response.MenuResponse;
import com.tma.restaurantapi.model.dto.request.MenuRequest;
import com.tma.restaurantapi.exception.ApiException;
import com.tma.restaurantapi.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

/**
 * The MenuController class represents the menu controller.
 * It handles HTTP requests and provides endpoints for interacting with the menu.
 */
@RestController
@RequestMapping(value = "/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    /**
     * Create new dish in menu
     *
     * @param menuRequestList
     * @return
     */
    @PutMapping
    public ResponseEntity create(@RequestBody List<MenuRequest> menuRequestList) throws ApiException {
        menuService.addMenuItem(menuRequestList);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", ApiHandlingMessage.CREATE_SUCCESS, "httpStatus", HttpStatus.CREATED, "timestamp", ZonedDateTime.now()));
    }

    /**
     * Return all menu record, take request param to get pagination
     * Default value for page is 0, for size is 3 and sorted by id
     *
     * @return
     */
    @GetMapping
    public ResponseEntity findAll(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "3") Integer size,
                                                          @RequestParam(defaultValue = "id") String sortBy) {
        List<MenuResponse> menuResponseList = menuService.findAll(page, size, sortBy);
        if (!menuResponseList.isEmpty()) {
            return new ResponseEntity<>(menuResponseList, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", ApiHandlingMessage.EMPTY_MENU));
    }

    /**
     * Get menu by provided id in path variable
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity findById(@PathVariable Long id) throws ApiException {
        return new ResponseEntity<>(menuService.findById(id), HttpStatus.OK);
    }

    /**
     * Delete menu by ID
     * Change status to DISABLE, not completely delete in database
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = {"/{id}"})
    public ResponseEntity delete(@PathVariable Long id) throws ApiException {
        menuService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", ApiHandlingMessage.DELETE_SUCCESS, "httpStatus", HttpStatus.OK, "timestamp", ZonedDateTime.now()));
    }

    /**
     * Update menu item by given ID and MenuDtoResponse
     *
     * @param id
     * @param menuRequest
     * @return
     */
    @PatchMapping(value = "/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody MenuRequest menuRequest) throws ApiException {
        menuService.update(id, menuRequest);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", ApiHandlingMessage.UPDATE_SUCCESS, "httpStatus", HttpStatus.OK, "timestamp", ZonedDateTime.now()));
    }

    /**
     * Find Menu item with keyword
     *
     * @param keyword
     * @return
     */
    @GetMapping(value = "/search")
    public ResponseEntity findMenuByKeyword(@RequestParam String keyword) {
        List<MenuResponse> menuResponseList = menuService.findMenuByKeyword(keyword);
        if (!menuResponseList.isEmpty()) {
            return new ResponseEntity<>(menuService.findMenuByKeyword(keyword), HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", ApiHandlingMessage.NONE_MATCHED_DISHES));
    }

}
