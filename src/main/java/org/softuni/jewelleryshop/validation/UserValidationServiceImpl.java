package org.softuni.jewelleryshop.validation;

import org.softuni.jewelleryshop.domain.models.service.UserServiceModel;
import org.springframework.stereotype.Component;

@Component
public class UserValidationServiceImpl implements UserValidationService {
    @Override
    public boolean isValid(UserServiceModel user) {
        return user != null;
    }
}
