package com.tma.restaurantapi.controller;

import com.tma.restaurantapi.constant.ApiHandlingMessage;
import com.tma.restaurantapi.model.dto.request.BillDetailRequest;
import com.tma.restaurantapi.model.dto.response.BillResponse;
import com.tma.restaurantapi.exception.ApiException;
import com.tma.restaurantapi.service.BillService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

/**
 * The BillController class represents the bill controller.
 * <p>
 * It handles HTTP requests and provides endpoints for interacting with the Bill.
 */
@RestController
@RequestMapping(value = "/bill")
public class BillController {

    private final BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
    }

    /**
     * Return all bill record
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<Iterable<BillResponse>> findAll(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size,
                                                          @RequestParam(defaultValue = "id") String sortBy) {
        return new ResponseEntity<>(billService.findAll(page, size, sortBy), HttpStatus.OK);
    }

    /**
     * Get bill by provided id
     *
     * @param id
     * @return
     */
    @GetMapping(value = "{id}")
    public ResponseEntity<BillResponse> findById(@PathVariable Long id) throws ApiException {
        return new ResponseEntity<>(billService.findById(id), HttpStatus.OK);
    }

    /**
     * Create new Bill
     *
     * @return
     */
    @PostMapping
    public ResponseEntity createNewBill() {
        billService.createNewBill();
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", ApiHandlingMessage.CREATE_SUCCESS, "httpStatus", HttpStatus.OK, "timestamp", ZonedDateTime.now()));
    }

    /**
     * Add item to bill
     *
     * @param id
     * @param billDetailRequestList
     * @return
     */
    @PatchMapping(value = "/{id}/add")
    public ResponseEntity addBillDetail(@PathVariable Long id, @RequestBody List<BillDetailRequest> billDetailRequestList) throws ApiException {
        billService.addBillDetail(id, billDetailRequestList);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", ApiHandlingMessage.ADD_SUCCESS, "httpStatus", HttpStatus.OK, "timestamp", ZonedDateTime.now()));
    }

    /**
     * Update Bill status to PAID
     *
     * @param id
     * @return
     */
    @PatchMapping(value = "/{id}/pay")
    public ResponseEntity payBill(@PathVariable Long id) throws ApiException {
        billService.payBill(id);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", ApiHandlingMessage.PAID_SUCCESS, "httpStatus", HttpStatus.OK, "timestamp", ZonedDateTime.now()));
    }

    @PatchMapping(value = "/{id}/update")
    private ResponseEntity updateBillDetailQuantity(@PathVariable Long id, @RequestBody BillDetailRequest billDetailRequest) throws ApiException {
        billService.updateBillDetailQuantity(id, billDetailRequest);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", ApiHandlingMessage.UPDATE_SUCCESS, "httpStatus", HttpStatus.OK, "timestamp", ZonedDateTime.now()));
    }

}
