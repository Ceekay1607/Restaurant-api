package com.tma.restaurantapi.service;

import com.tma.restaurantapi.constant.ApiHandlingMessage;
import com.tma.restaurantapi.model.dto.response.BillResponse;
import com.tma.restaurantapi.model.dto.request.BillDetailRequest;
import com.tma.restaurantapi.model.dto.response.MenuResponse;
import com.tma.restaurantapi.exception.ApiException;
import com.tma.restaurantapi.model.Bill;
import com.tma.restaurantapi.model.BillDetail;
import com.tma.restaurantapi.model.BillStatus;
import com.tma.restaurantapi.model.Menu;
import com.tma.restaurantapi.repository.BillDetailRepository;
import com.tma.restaurantapi.repository.BillRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The BillService class is responsible for providing operations related to bills and bill details.
 * It interacts with the BillRepository to fetch Bill data.
 * Implements the {@link RestaurantService} interface for bill-related operations.
 */
@Service
public class BillService implements RestaurantService<BillResponse> {

    private final ModelMapper modelMapper;
    private final BillRepository billRepository;
    private final BillDetailRepository billDetailRepository;
    private final MenuService menuService;

    public BillService(ModelMapper modelMapper, BillRepository billRepository, BillDetailRepository billDetailRepository, MenuService menuService) {
        this.modelMapper = modelMapper;
        this.billRepository = billRepository;
        this.billDetailRepository = billDetailRepository;
        this.menuService = menuService;
    }

    @Override
    public List<BillResponse> findAll(Integer page, Integer size, String sortBy) {
        Pageable paging = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Bill> bills = billRepository.findAll(paging);
        return bills.stream().map(bill -> modelMapper.map(bill, BillResponse.class)).collect(Collectors.toList());
    }

    @Override
    public BillResponse findById(Long id) throws ApiException {
        Bill bill = billRepository.findById(id).orElseThrow(() -> new ApiException(ApiHandlingMessage.BILL_NOT_FOUND, HttpStatus.NOT_FOUND));

        return modelMapper.map(bill, BillResponse.class);
    }

    /**
     * Create new bill
     *
     * @return Response entity
     */
    public void createNewBill() {
        Bill bill = new Bill();
        bill.setBillStatus(BillStatus.UNPAID);
        bill.setTotal(0);

        billRepository.save(bill);
    }

    /**
     * Add item to bill
     *
     * @param billDetailRequestList
     * @return
     */
    public void addBillDetail(Long billId, List<BillDetailRequest> billDetailRequestList) throws ApiException {
        Bill bill = billRepository.findById(billId).orElseThrow(() -> new ApiException(ApiHandlingMessage.BILL_NOT_FOUND, HttpStatus.NOT_FOUND));

        //If bill is already paid -> cannot add item
        if (bill.getBillStatus().equals(BillStatus.PAID)) {
            throw new ApiException(ApiHandlingMessage.BILL_PAID, HttpStatus.METHOD_NOT_ALLOWED);
        }

        for(BillDetailRequest billDetailRequest : billDetailRequestList) {
            if (billDetailRequest.getQuantity() < 0) {
                throw new ApiException(ApiHandlingMessage.NEGATIVE_NUMBER, HttpStatus.BAD_REQUEST);
            }

            //get menu by id
            Long menuId = billDetailRequest.getMenuId();
            MenuResponse menuResponse = menuService.findById(menuId);
            Menu menu = modelMapper.map(menuResponse, Menu.class);

            //get BillDetail from Bill by menuId
            BillDetail billDetail;
            try {
                billDetail = findBillDetail(bill, billDetailRequest);
                billDetail.setQuantity(billDetail.getQuantity() + billDetailRequest.getQuantity());
            } catch (ApiException e) {
                //if billDetail is not added yet, create new billDetail
                billDetail = new BillDetail();
                billDetail.setBill(bill);
                billDetail.setMenu(menu);
                billDetail.setPrice(menu.getPrice());
                billDetail.setQuantity(billDetailRequest.getQuantity());
            }

            bill.setTotal(bill.getTotal() + menu.getPrice() * billDetailRequest.getQuantity());
            bill.getBillDetails().add(billDetail);
        }

        //save bill
        billRepository.save(bill);
    }

    /**
     * Find bill detail with matched menuId
     *
     * @param bill
     * @param billDetailRequest
     * @return
     */
    private BillDetail findBillDetail(Bill bill, BillDetailRequest billDetailRequest) throws ApiException {
        Long menuId = billDetailRequest.getMenuId();

        for (BillDetail billDetail : bill.getBillDetails()) {
            if (billDetail.getMenu().getId() == menuId) {
                return billDetail;
            }
        }
        throw new ApiException(ApiHandlingMessage.BILL_DETAIL_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    /**
     * Set bill status to PAID
     *
     * @param billId
     * @return
     */
    public void payBill(Long billId) throws ApiException {
        Bill bill = billRepository.findById(billId).orElseThrow(() -> new ApiException(ApiHandlingMessage.BILL_NOT_FOUND, HttpStatus.NOT_FOUND));

        bill.setBillStatus(BillStatus.PAID);
        billRepository.save(bill);
    }

    /**
     * Update BillDetail quantity
     *
     * @param billId
     * @param billDetailRequest
     * @return
     */
    public void updateBillDetailQuantity(Long billId, BillDetailRequest billDetailRequest) throws ApiException {
        if (billDetailRequest.getQuantity() < 0) {
            throw new ApiException(ApiHandlingMessage.NEGATIVE_NUMBER, HttpStatus.BAD_REQUEST);
        }
        Bill bill = billRepository.findById(billId).orElseThrow(() -> new ApiException(ApiHandlingMessage.BILL_NOT_FOUND, HttpStatus.NOT_FOUND));

        //If bill is already paid -> cannot add item
        if (bill.getBillStatus().equals(BillStatus.PAID)) {
            throw new ApiException(ApiHandlingMessage.BILL_PAID, HttpStatus.METHOD_NOT_ALLOWED);
        }

        //update Bill information
        setNewQuantity(bill, billDetailRequest);

        billRepository.save(bill);
    }

    /**
     * Set new quantity for Bill detail with BillDetailRequest
     *
     * @param bill
     * @param billDetailRequest
     */
    private void setNewQuantity(Bill bill, BillDetailRequest billDetailRequest) throws ApiException {
        BillDetail billDetail = findBillDetail(bill, billDetailRequest);

        int oldQuantity = billDetail.getQuantity();
        int newQuantity = billDetailRequest.getQuantity();
        double price = billDetail.getPrice();
        billDetail.setQuantity(newQuantity);


        if (billDetailRequest.getQuantity() == 0) {
            billDetailRepository.deleteBillDetail(bill.getId(), billDetailRequest.getMenuId());
        }

        //calculate price
        bill.setTotal(bill.getTotal() - oldQuantity * price + newQuantity * price);
    }

}
