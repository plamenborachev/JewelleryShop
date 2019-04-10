package org.softuni.jewelleryshop.validation.implementations;

import org.softuni.jewelleryshop.domain.models.service.UserServiceModel;
import org.softuni.jewelleryshop.validation.UserValidationService;
import org.springframework.stereotype.Component;

@Component
public class UserValidationServiceImpl implements UserValidationService {
    @Override
    public boolean isValid(UserServiceModel user) {
        return user != null;
    }
}
