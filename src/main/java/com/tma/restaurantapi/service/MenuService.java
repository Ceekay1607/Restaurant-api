package com.tma.restaurantapi.service;

import com.tma.restaurantapi.constant.ApiHandlingMessage;
import com.tma.restaurantapi.model.dto.response.MenuResponse;
import com.tma.restaurantapi.model.dto.request.MenuRequest;
import com.tma.restaurantapi.exception.ApiException;
import com.tma.restaurantapi.model.Menu;
import com.tma.restaurantapi.model.MenuStatus;
import com.tma.restaurantapi.repository.MenuRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The MenuService class is responsible for providing operations related to menus.
 * It interacts with the MenuRepository to fetch menu data.
 */
@Service
public class MenuService implements RestaurantService<MenuResponse> {
    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Saves the provided Menu object.
     * Sets the status of the menu to INCLUDED by default.
     *
     * @param menuRequestList
     * @return
     */
    public void addMenuItem(List<MenuRequest> menuRequestList) throws ApiException {
        List<Menu> menuList = new ArrayList<>();
        for (MenuRequest menuRequest : menuRequestList) {
            if (menuRequest.getPrice() < 0) {
                throw new ApiException(ApiHandlingMessage.INVALID_PRICE, HttpStatus.BAD_REQUEST);
            }

            if (menuRequest.getName() == null) {
                menuRequest.setName("Empty");
            }
            if (menuRequest.getDescription() == null) {
                menuRequest.setDescription("Empty");
            }
            if (menuRequest.getImage() == null) {
                menuRequest.setImage("Empty");
            }
            if (menuRequest.getType() == null) {
                menuRequest.setType("Empty");
            }

            Optional<Menu> menu = menuRepository.findByName(menuRequest.getName());

            if (menu.isPresent()) {
                menu.get().setDescription(menuRequest.getDescription());
                menu.get().setPrice(menuRequest.getPrice());
                menu.get().setType(menuRequest.getType());

                //set default Status is ENABLE
                menu.get().setStatus(MenuStatus.ENABLE);

                //add to menu List
                menuList.add(menu.get());

            } else {
                //map menuDto parameter to Menu object
                Menu newMenu = modelMapper.map(menuRequest, Menu.class);

                //set default Status is INCLUDED
                newMenu.setStatus(MenuStatus.ENABLE);

                //add to menu list
                menuList.add(newMenu);
            }

        }

        //Save to database
        menuRepository.saveAll(menuList);
    }

    /**
     * Retrieves all menu
     *
     * @return
     */
    @Override
    public List<MenuResponse> findAll(Integer page, Integer size, String sortBy) {
        Pageable paging = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Menu> menus = menuRepository.findAllMenu(paging);
        return menus.stream().map(menu -> modelMapper.map(menu, MenuResponse.class)).collect(Collectors.toList());
    }

    /**
     * Retrieves a menu by its ID
     *
     * @param id The ID of the menu to retrieve
     * @return An Optional containing the retrieved Menu object, or an empty Optional if no menu is found.
     */
    @Override
    public MenuResponse findById(Long id) throws ApiException {
        Menu menu = menuRepository.findById(id).orElseThrow(() -> new ApiException(ApiHandlingMessage.MENU_NOT_FOUND, HttpStatus.NOT_FOUND));

        if (menu.getStatus().equals(MenuStatus.DISABLE)) {
            throw new ApiException(ApiHandlingMessage.MENU_DELETED, HttpStatus.NOT_FOUND);
        }

        return modelMapper.map(menu, MenuResponse.class);
    }

    /**
     * Delete a dish in menu by set its status to "DELETED"
     *
     * @param id
     * @return
     */
    public void delete(Long id) throws ApiException {
        Menu menu = menuRepository.findById(id).orElseThrow(() -> new ApiException(ApiHandlingMessage.MENU_NOT_FOUND, HttpStatus.NOT_FOUND));
        menu.setStatus(MenuStatus.DISABLE);
        menuRepository.save(menu);
    }

    /**
     * Update Menu object with given ID and menuDto
     *
     * @param id
     * @param menuRequest
     * @return
     */
    public void update(Long id, MenuRequest menuRequest) throws ApiException {
        Menu menu = menuRepository.findById(id).orElseThrow(() -> new ApiException(ApiHandlingMessage.MENU_NOT_FOUND, HttpStatus.NOT_FOUND));

        if (menuRequest.getPrice() < 0) {
            throw new ApiException(ApiHandlingMessage.INVALID_PRICE, HttpStatus.BAD_REQUEST);
        }

        if (menu.getStatus().equals(MenuStatus.ENABLE)) {
            if (!menuRequest.getName().equals("Empty")) {
                menu.setName(menuRequest.getName());
            }

            if (!menuRequest.getDescription().equals("Empty")) {
                menu.setDescription(menuRequest.getDescription());
            }

            if (menuRequest.getPrice() != 0) {
                menu.setPrice(menuRequest.getPrice());
            }

            if (!menuRequest.getType().equals("Empty")) {
                menu.setType(menuRequest.getType());
            }

            menuRepository.save(menu);
        } else {
            throw new ApiException(ApiHandlingMessage.MENU_DELETED, HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    /**
     * Find matched item with defining keyword
     * Find in field name, description and type of Menu
     *
     * @param keyword
     * @return
     */
    public List<MenuResponse> findMenuByKeyword(String keyword) {
        List<Menu> menuWithMatchedName = menuRepository.findByKeyword(keyword);
        return menuWithMatchedName.stream().map(menu -> modelMapper.map(menu, MenuResponse.class)).collect(Collectors.toList());
    }

}
