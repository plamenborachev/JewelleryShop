package org.softuni.jewelleryshop.validation;

import org.softuni.jewelleryshop.GlobalConstants;
import org.softuni.jewelleryshop.domain.models.binding.ProductAddBindingModel;
import org.softuni.jewelleryshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;

@Component
public class ProductAddValidator implements Validator {

    private final ProductRepository productRepository;

    @Autowired
    public ProductAddValidator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return ProductAddBindingModel.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ProductAddBindingModel productAddBindingModel = (ProductAddBindingModel) o;

        if (productAddBindingModel.getName() == null
                || productAddBindingModel.getName().equals("")) {
            errors.rejectValue("name",
                    GlobalConstants.PRODUCT_NAME_NOT_NULL_OR_EMPTY_VALIDATION_MESSAGE,
                    GlobalConstants.PRODUCT_NAME_NOT_NULL_OR_EMPTY_VALIDATION_MESSAGE);
        }

        if (productAddBindingModel.getName().length() < GlobalConstants.PRODUCT_NAME_MIN_LENGTH
                || productAddBindingModel.getName().length() > GlobalConstants.PRODUCT_NAME_MAX_LENGTH) {
            errors.rejectValue("name",
                    GlobalConstants.PRODUCT_NAME_LENGTH_VALIDATION_MESSAGE,
                    GlobalConstants.PRODUCT_NAME_LENGTH_VALIDATION_MESSAGE);
        }

        this.productRepository.findByName(productAddBindingModel.getName())
                .ifPresent((c) -> errors
                        .rejectValue("name",
                                GlobalConstants.PRODUCT_EXISTS_MESSAGE,
                                GlobalConstants.PRODUCT_EXISTS_MESSAGE));

        if (productAddBindingModel.getDescription() == null
                || productAddBindingModel.getDescription().equals("")) {
            errors.rejectValue("description",
                    GlobalConstants.DESCRIPTION_NOT_NULL_OR_EMPTY_VALIDATION_MESSAGE,
                    GlobalConstants.DESCRIPTION_NOT_NULL_OR_EMPTY_VALIDATION_MESSAGE);
        }

        if (productAddBindingModel.getDescription().length() < GlobalConstants.DESCRIPTION_MIN_LENGTH
                || productAddBindingModel.getDescription().length() > GlobalConstants.DESCRIPTION_MAX_LENGTH) {
            errors.rejectValue("description",
                    GlobalConstants.DESCRIPTION_LENGTH_VALIDATION_MESSAGE,
                    GlobalConstants.DESCRIPTION_LENGTH_VALIDATION_MESSAGE);
        }

        if (productAddBindingModel.getPrice() == null
                || productAddBindingModel.getPrice().toString().equals("")) {
            errors.rejectValue("price",
                    GlobalConstants.PRICE_NOT_NULL_OR_EMPTY_VALIDATION_MESSAGE,
                    GlobalConstants.PRICE_NOT_NULL_OR_EMPTY_VALIDATION_MESSAGE);
        }

        if (productAddBindingModel.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            errors.rejectValue("price",
                    GlobalConstants.PRICE_POSITIVE_VALIDATION_MESSAGE,
                    GlobalConstants.PRICE_POSITIVE_VALIDATION_MESSAGE);
        }

        if (productAddBindingModel.getQuantity() == null) {
            errors.rejectValue("quantity",
                    GlobalConstants.QUANTITY_NOT_NULL_OR_EMPTY_VALIDATION_MESSAGE,
                    GlobalConstants.QUANTITY_NOT_NULL_OR_EMPTY_VALIDATION_MESSAGE);
        }

        if (productAddBindingModel.getQuantity() < 0) {
            errors.rejectValue("quantity",
                    GlobalConstants.QUANTITY_POSITIVE_VALIDATION_MESSAGE,
                    GlobalConstants.QUANTITY_POSITIVE_VALIDATION_MESSAGE);
        }

        if (productAddBindingModel.getCategories() == null
                || productAddBindingModel.getCategories().isEmpty()) {
            errors.rejectValue("categories",
                    GlobalConstants.CATEGORY_NOT_NULL_OR_EMPTY_VALIDATION_MESSAGE,
                    GlobalConstants.CATEGORY_NOT_NULL_OR_EMPTY_VALIDATION_MESSAGE);
        }

        if (productAddBindingModel.getImage() == null
                || productAddBindingModel.getImage().isEmpty()) {
            errors.rejectValue("image",
                    GlobalConstants.IMAGE_NOT_NULL_OR_EMPTY_VALIDATION_MESSAGE,
                    GlobalConstants.IMAGE_NOT_NULL_OR_EMPTY_VALIDATION_MESSAGE);
        }
    }
}
