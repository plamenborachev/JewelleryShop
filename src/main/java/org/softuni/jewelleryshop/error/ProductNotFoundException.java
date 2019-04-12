package org.softuni.jewelleryshop.error;

import org.softuni.jewelleryshop.GlobalConstants;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = GlobalConstants.PRODUCT_NOT_FOUND_EXCEPTION_MESSAGE)
public class ProductNotFoundException extends RuntimeException {

    private int statusCode;

    public ProductNotFoundException() {
        this.statusCode = GlobalConstants.PAGE_NOT_FOUND_CODE;
    }

    public ProductNotFoundException(String message) {
        super(message);
        this.statusCode = GlobalConstants.PAGE_NOT_FOUND_CODE;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
