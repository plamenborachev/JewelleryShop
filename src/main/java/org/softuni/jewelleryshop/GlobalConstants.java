package org.softuni.jewelleryshop;

public class GlobalConstants {

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

    public static final String PRODUCT_EXISTS_VALIDATION_MESSAGE = "Jewellery already exists!";

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
}
