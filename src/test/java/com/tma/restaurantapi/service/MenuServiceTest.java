package com.tma.restaurantapi.service;

import com.tma.restaurantapi.constant.ApiHandlingMessage;
import com.tma.restaurantapi.exception.ApiException;
import com.tma.restaurantapi.model.Menu;
import com.tma.restaurantapi.model.MenuStatus;
import com.tma.restaurantapi.model.dto.request.MenuRequest;
import com.tma.restaurantapi.repository.MenuRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;


import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MenuServiceTest {
    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuRepository menuRepository;

    private boolean initialize = false;

    @Before
    public void initUseCase() {
        if(!initialize) {
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
    public void save_WhenSave_ThenOk() throws ApiException {
        List<MenuRequest> menuRequestList = new ArrayList<>();
        MenuRequest menuRequest = MenuRequest.builder().name("khanh").price(30).build();

        menuRequestList.add(menuRequest);

        menuService.addMenuItem(menuRequestList);

        assertEquals(30, menuRepository.findByName("khanh").get().getPrice());
    }

    @Test
    public void save_NegativePrice_ExceptionThrown() {
        List<MenuRequest> menuRequestList = new ArrayList<>();
        MenuRequest menuRequest = MenuRequest.builder().name("meow").price(-20).build();
        menuRequestList.add(menuRequest);


        Exception exception = Assert.assertThrows(ApiException.class, () -> {
            menuService.addMenuItem(menuRequestList);
        });

        assertTrue(exception.getMessage().contains(ApiHandlingMessage.INVALID_PRICE));
    }

    @Test
    public void findAll_WhenFindAll_ThenOk() {
        assertNotNull(menuRepository.findAll());
    }

    @Test
    public void findById_WhenFound_ThenOk() throws ApiException {
        long id = 2;
        assertEquals(20, menuRepository.findById(id).get().getPrice());
        assertEquals("dish2", menuRepository.findById(id).get().getName());
    }

    @Test
    public void update_WhenUpdateMenuPrice_GetPriceUpdate() throws ApiException {
        MenuRequest menuRequest = MenuRequest.builder().price(100).build();
        long id = 1;

        menuService.update(id, menuRequest);
        Menu menu = menuRepository.findById(id).get();

        assertEquals(100, menu.getPrice());
    }

    @Test
    public void update_WhenUpdateNegativePrice_ExceptionThrown() {
        MenuRequest menuRequest = MenuRequest.builder().price(-100).build();
        long id = 1;

        Exception exception = Assert.assertThrows(ApiException.class, () -> {
            menuService.update(id, menuRequest);
        });

        assertTrue(exception.getMessage().contains(ApiHandlingMessage.INVALID_PRICE));
    }

    @Test
    public void findMenuByKeyword_WhenFound_ReturnListMenuMatched() {
        String keyword = "dish";
        assertTrue(menuService.findMenuByKeyword(keyword).size() > 0);
    }

}