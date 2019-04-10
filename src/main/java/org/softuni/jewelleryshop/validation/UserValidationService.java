package org.softuni.jewelleryshop.validation;

import org.softuni.jewelleryshop.domain.models.service.UserServiceModel;

public interface UserValidationService {
    boolean isValid(UserServiceModel user);
}
