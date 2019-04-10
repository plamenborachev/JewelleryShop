package org.softuni.jewelleryshop.validation;

import org.softuni.jewelleryshop.domain.entities.Product;
import org.softuni.jewelleryshop.domain.models.service.ProductServiceModel;

public interface ProductValidationService {
    boolean isValid(Product product);

    boolean isValid(ProductServiceModel product);
}
