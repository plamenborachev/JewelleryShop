package org.softuni.jewelleryshop.error;

import org.softuni.jewelleryshop.GlobalConstants;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = GlobalConstants.ORDER_NOT_FOUND_EXCEPTION_MESSAGE)
public class OrderNotFoundException extends RuntimeException {

    private int statusCode;

    public OrderNotFoundException() {
        this.statusCode = GlobalConstants.PAGE_NOT_FOUND_CODE;
    }

    public OrderNotFoundException(String message) {
        super(message);
        this.statusCode = GlobalConstants.PAGE_NOT_FOUND_CODE;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
