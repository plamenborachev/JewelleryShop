package org.softuni.jewelleryshop.error;

import org.softuni.jewelleryshop.GlobalConstants;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = GlobalConstants.PRODUCT_NAME_EXISTS_EXCEPTION_MESSAGE)
public class ProductNameAlreadyExistsException extends RuntimeException {

    private int statusCode;

    public ProductNameAlreadyExistsException() {
        this.statusCode = GlobalConstants.CONFLICT_CODE;
    }

    public ProductNameAlreadyExistsException(String message) {
        super(message);
        this.statusCode = GlobalConstants.CONFLICT_CODE;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
