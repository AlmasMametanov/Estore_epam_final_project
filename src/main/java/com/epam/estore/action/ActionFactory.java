package com.epam.estore.action;

import java.util.HashMap;
import java.util.Map;

import static com.epam.estore.action.ActionConstants.*;

public class ActionFactory {
    private static final Map<String, Action> ACTION_MAP = new HashMap<>();
    private static final ActionFactory ACTION_FACTORY = new ActionFactory();

    static {
        ACTION_MAP.put(LOGIN_ACTION, new LogInUserAction());
        ACTION_MAP.put(SIGNUP_ACTION, new SignUpUserAction());
        ACTION_MAP.put(LOGOUT_ACTION, new LogOutUserAction());
        ACTION_MAP.put(SEARCH_PRODUCT_ACTION, new GetAllProductsBySearchAction());
        ACTION_MAP.put(CHANGE_LOCALE, new ChangeLocaleAction());
        ACTION_MAP.put(GET_ALL_USERS, new GetAllUsersAction());
        ACTION_MAP.put(GET_USER_BY_ID, new GetUserByIdAction());
        ACTION_MAP.put(CHANGE_USER_BAN_STATUS, new ChangeUserBanStatusAction());
        ACTION_MAP.put(GET_CATEGORIES_ACTION, new GetCategoriesAction());
        ACTION_MAP.put(GET_PRODUCTS_BY_CATEGORY_ID_ACTION, new GetAllProductsByCategoryIdAction());
        ACTION_MAP.put(ADD_NEW_CATEGORY, new AddNewCategoryAction());
        ACTION_MAP.put(ADD_NEW_PRODUCT, new AddNewProductAction());
        ACTION_MAP.put(GET_ALL_COUNTRIES, new GetAllCountriesAction());
        ACTION_MAP.put(DELETE_PRODUCT, new DeleteProductAction());
        ACTION_MAP.put(DELETE_CATEGORY, new DeleteCategoryAction());
        ACTION_MAP.put(CHANGE_PRODUCT, new ChangeProductDataAction());
        ACTION_MAP.put(ADD_PRODUCT_IN_BASKET_ACTION, new AddProductInBasketAction());
        ACTION_MAP.put(REMOVE_PRODUCT_FROM_BASKET, new RemoveProductFromBasketAction());
        ACTION_MAP.put(GET_ALL_PRODUCTS_FROM_BASKET_BY_USER_ID, new GetAllProductsOfBasketByUserIdAction());
        ACTION_MAP.put(CREATE_ORDER_ACTION, new CreateOrderAction());
        ACTION_MAP.put(CONFIRM_ORDER, new CreateOrderDetailAction());
        ACTION_MAP.put(GET_ORDERS, new GetOrdersAction());
        ACTION_MAP.put(CHANGE_ORDER_STATUS_ACTION, new ChangeOrderStatusAction());
    }

    public static ActionFactory getInstance() {
        return ACTION_FACTORY;
    }

    public Action getAction(String request) {
        Action action = null;

        for (Map.Entry<String, Action> pair : ACTION_MAP.entrySet()) {
            if (request.equalsIgnoreCase(pair.getKey())) {
                action = ACTION_MAP.get(pair.getKey());
            }
        }
        return action;
    }

}
