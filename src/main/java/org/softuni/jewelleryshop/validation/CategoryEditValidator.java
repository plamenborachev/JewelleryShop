package org.softuni.jewelleryshop.validation;

import org.softuni.jewelleryshop.GlobalConstants;
import org.softuni.jewelleryshop.domain.models.binding.CategoryAddBindingModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CategoryEditValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return CategoryAddBindingModel.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CategoryAddBindingModel categoryAddBindingModel = (CategoryAddBindingModel) o;

        if (categoryAddBindingModel.getName() == null || categoryAddBindingModel.getName().equals("")) {
            errors.rejectValue("name",
                    GlobalConstants.CATEGORY_NAME_NOT_NULL_OR_EMPTY_VALIDATION_MESSAGE,
                    GlobalConstants.CATEGORY_NAME_NOT_NULL_OR_EMPTY_VALIDATION_MESSAGE);
        }

        if (categoryAddBindingModel.getName().length() < GlobalConstants.CATEGORY_NAME_MIN_LENGTH
                || categoryAddBindingModel.getName().length() > GlobalConstants.CATEGORY_NAME_MAX_LENGTH) {
            errors.rejectValue("name",
                    GlobalConstants.CATEGORY_NAME_LENGTH_VALIDATION_MESSAGE,
                    GlobalConstants.CATEGORY_NAME_LENGTH_VALIDATION_MESSAGE);
        }
    }
}
