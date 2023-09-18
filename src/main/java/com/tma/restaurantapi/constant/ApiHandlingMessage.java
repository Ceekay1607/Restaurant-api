package com.tma.restaurantapi.constant;

/**
 * This class contains static messages for API handling.
 */
public class ApiHandlingMessage {

    //Exception message
    public static final String MENU_NOT_FOUND = "Menu with your given ID doesn't exist";
    public static final String BILL_NOT_FOUND = "Bill with your given ID doesn't exist";
    public static final String BILL_DETAIL_NOT_FOUND = "Bill detail doesn't exist";
    public static final String INVALID_PRICE = "Field for 'price' must be in positive number";
    public static final String BILL_PAID = "Bill is already paid";
    public static final String MENU_DELETED = "Menu is deleted";
    public static final String NEGATIVE_NUMBER = "Quantity must greater than 0";

    //Success message
    public static final String CREATE_SUCCESS = "Create successfully";
    public static final String DELETE_SUCCESS = "Delete successfully";
    public static final String UPDATE_SUCCESS = "Update successfully";
    public static final String PAID_SUCCESS = "Paid successfully";
    public static final String ADD_SUCCESS = "Add successfully";

    //Message
    public static final String EMPTY_MENU = "There are nothing in menu!";
    public static final String NONE_MATCHED_DISHES = "We can't find any dish that match your keyword!";

}
