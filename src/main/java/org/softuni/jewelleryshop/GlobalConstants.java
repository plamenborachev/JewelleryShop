package org.softuni.jewelleryshop;

public class GlobalConstants {

    public static final String USERNAME_NOT_NULL_OR_EMPTY_VALIDATION_MESSAGE
            = "Username cannot be null or empty!";
    public static final String USERNAME_LENGTH_VALIDATION_MESSAGE
            = "Username must be between 5 and 20 characters!";
    public static final String USERNAME_EXISTS_VALIDATION_MESSAGE
            = "Username already exists";
    public static final int USERNAME_MIN_LENGTH = 5;
    public static final int USERNAME_MAX_LENGTH = 20;

    public static final String PASSWORD_NOT_NULL_OR_EMPTY_VALIDATION_MESSAGE
            = "Password cannot be null or empty!";
    public static final String PASSWORD_LENGTH_VALIDATION_MESSAGE
            = "Password must be at least 5 symbols!";
    public static final int PASSWORD_MIN_LENGTH = 5;

    public static final String PASSWORDS_MUST_MATCH_VALIDATION_MESSAGE
            = "Password and Confirm Password are not equal!";

    public static final String WRONG_PASSWORD_MESSAGE = "Wrong password!";

    public static final String EMAIL_NOT_NULL_OR_EMPTY_VALIDATION_MESSAGE
            = "Email cannot be null or empty!";

    public static final String CATEGORY_NAME_NOT_NULL_OR_EMPTY_VALIDATION_MESSAGE
            = "Category name cannot be null or empty!";
    public static final String CATEGORY_NAME_LENGTH_VALIDATION_MESSAGE
            = "Category name must be between 3 and 20 characters!";
    public static final String CATEGORY_EXISTS_VALIDATION_MESSAGE
            = "Category already exists";
    public static final int CATEGORY_NAME_MIN_LENGTH = 3;
    public static final int CATEGORY_NAME_MAX_LENGTH = 20;

    public static final String PRODUCT_NAME_NOT_NULL_OR_EMPTY_VALIDATION_MESSAGE
            = "Jewellery name cannot be null or empty!";
    public static final String PRODUCT_NAME_LENGTH_VALIDATION_MESSAGE
            = "Jewellery name must be between 3 and 40 characters!";
    public static final int PRODUCT_NAME_MIN_LENGTH = 3;
    public static final int PRODUCT_NAME_MAX_LENGTH = 40;
    public static final String PRODUCT_EXISTS_MESSAGE = "Jewellery already exists!";

    public static final String DESCRIPTION_NOT_NULL_OR_EMPTY_VALIDATION_MESSAGE
            = "Description cannot be null or empty!";
    public static final String DESCRIPTION_LENGTH_VALIDATION_MESSAGE
            = "Description must be between 15 and 100 characters!";
    public static final int DESCRIPTION_MIN_LENGTH = 15;
    public static final int DESCRIPTION_MAX_LENGTH = 100;

    public static final String PRICE_NOT_NULL_OR_EMPTY_VALIDATION_MESSAGE = "Price cannot be null or empty!";
    public static final String PRICE_POSITIVE_VALIDATION_MESSAGE = "Price must be greater than zero!";

    public static final String CATEGORY_NOT_NULL_OR_EMPTY_VALIDATION_MESSAGE
            = "You have to choose at least one category!";

    public static final String IMAGE_NOT_NULL_OR_EMPTY_VALIDATION_MESSAGE = "You have to upload an image!";

    public static int PAGE_NOT_FOUND_CODE = 404;
    public static int CONFLICT_CODE = 409;

    public static final double DISCOUNT_RATIO = 0.8;

    public static final String ORDER_NOT_FOUND_EXCEPTION_MESSAGE = "No such Order exists!";
    public static final String PRODUCT_NOT_FOUND_EXCEPTION_MESSAGE = "No such Product exists!";
    public static final String CATEGORY_NOT_FOUND_EXCEPTION_MESSAGE = "No such Category exists!";
    public static final String USER_NOT_FOUND_EXCEPTION_MESSAGE = "No such User exists!";
    public static final String QUANTITY_EXCEPTION_MESSAGE = "You have to choose quantity first!";
    public static final String POSITIVE_QUANTITY_EXCEPTION_MESSAGE = "You have to choose quantity greater than 0!";
    public static final String CART_EMPTY_EXCEPTION_MESSAGE = "The cart is empty!";

    public static final String ROLE_ROOT = "ROLE_ROOT";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_MODERATOR = "ROLE_MODERATOR";
    public static final String ROLE_USER = "ROLE_USER";

    public static final String CASE_USER = "user";
    public static final String CASE_MODERATOR = "moderator";
    public static final String CASE_ADMIN = "admin";

    public static final String FAVICON_LINK = "https://www.shareicon.net/data/32x32/2016/12/05/862039_diamond_512x512.png";
    public static final String SITE_TITLE = "Jewelery Shop";
}
