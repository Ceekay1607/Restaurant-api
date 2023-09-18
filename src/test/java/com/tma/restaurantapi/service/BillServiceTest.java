package com.tma.restaurantapi.service;

import com.tma.restaurantapi.constant.ApiHandlingMessage;
import com.tma.restaurantapi.exception.ApiException;
import com.tma.restaurantapi.model.Menu;
import com.tma.restaurantapi.model.MenuStatus;
import com.tma.restaurantapi.model.dto.request.BillDetailRequest;
import com.tma.restaurantapi.repository.BillRepository;
import com.tma.restaurantapi.repository.MenuRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class BillServiceTest {
    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private BillService billService;

    @Autowired
    private BillRepository billRepository;

    private boolean initialize = false;

    @Before
    public void initUseCase() {
        if (!initialize) {
            billService.createNewBill();

            Menu menu1 = Menu.builder().name("dish1").price(10).status(MenuStatus.ENABLE).build();
            Menu menu2 = Menu.builder().name("dish2").price(20).status(MenuStatus.ENABLE).build();
            Menu menu3 = Menu.builder().name("dish3").price(30).status(MenuStatus.ENABLE).build();
            Menu menu4 = Menu.builder().name("dish4").price(40).status(MenuStatus.ENABLE).build();

            List<Menu> menus = Arrays.asList(menu1, menu2, menu3, menu4);
            menuRepository.saveAll(menus);
            initialize = true;
        }
    }

    @Test
    public void create_WhenCreate_ThenOk() {
        billService.createNewBill();

        assertTrue(billRepository.findAll().size() > 0);
    }

    @Test
    public void addBillDetail_WhenAdd_ThenOk() throws ApiException {
        BillDetailRequest billDetailRequest1 = BillDetailRequest.builder().menuId((long) 1).quantity(1).build();
        BillDetailRequest billDetailRequest2 = BillDetailRequest.builder().menuId((long) 2).quantity(1).build();
        BillDetailRequest billDetailRequest3 = BillDetailRequest.builder().menuId((long) 3).quantity(1).build();

        List<BillDetailRequest> billDetailRequestList = Arrays.asList(billDetailRequest1, billDetailRequest2, billDetailRequest3);

        billService.addBillDetail((long) 1, billDetailRequestList);

        assertEquals(3, billRepository.findById((long) 1).get().getBillDetails().size());
    }

    @Test
    public void addBillDetail_WhenAddWithInvalidValue_ThrowException() throws ApiException {
        BillDetailRequest billDetailRequest1 = BillDetailRequest.builder().menuId((long) 1).quantity(-1).build();

        List<BillDetailRequest> billDetailRequestList = Arrays.asList(billDetailRequest1);

        Exception exception = Assert.assertThrows(ApiException.class, () -> {
            billService.addBillDetail((long) 1, billDetailRequestList);
        });

        assertTrue(exception.getMessage().contains(ApiHandlingMessage.NEGATIVE_NUMBER));
    }
}
