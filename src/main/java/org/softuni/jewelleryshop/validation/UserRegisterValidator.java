package org.softuni.jewelleryshop.validation;

import org.softuni.jewelleryshop.GlobalConstants;
import org.softuni.jewelleryshop.domain.models.binding.UserRegisterBindingModel;
import org.softuni.jewelleryshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserRegisterValidator implements Validator {

    private final UserRepository userRepository;

    @Autowired
    public UserRegisterValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UserRegisterBindingModel.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserRegisterBindingModel userRegisterBindingModel = (UserRegisterBindingModel) o;

        if (userRegisterBindingModel.getUsername() == null
                || userRegisterBindingModel.getUsername().equals("")) {
            errors.rejectValue("username",
                    GlobalConstants.USERNAME_NOT_NULL_OR_EMPTY_VALIDATION_MESSAGE,
                    GlobalConstants.USERNAME_NOT_NULL_OR_EMPTY_VALIDATION_MESSAGE);
        } else  if (userRegisterBindingModel.getUsername().length() < GlobalConstants.USERNAME_MIN_LENGTH
                || userRegisterBindingModel.getUsername().length() > GlobalConstants.USERNAME_MAX_LENGTH) {
            errors.rejectValue("username",
                    GlobalConstants.USERNAME_LENGTH_VALIDATION_MESSAGE,
                    GlobalConstants.USERNAME_LENGTH_VALIDATION_MESSAGE);
        }

        this.userRepository.findByUsername(userRegisterBindingModel.getUsername())
                .ifPresent((c) -> errors
                        .rejectValue("username",
                                GlobalConstants.USERNAME_EXISTS_VALIDATION_MESSAGE,
                                GlobalConstants.USERNAME_EXISTS_VALIDATION_MESSAGE));

        if (userRegisterBindingModel.getPassword() == null
                || userRegisterBindingModel.getPassword().equals("")) {
            errors.rejectValue("password",
                    GlobalConstants.PASSWORD_NOT_NULL_OR_EMPTY_VALIDATION_MESSAGE,
                    GlobalConstants.PASSWORD_NOT_NULL_OR_EMPTY_VALIDATION_MESSAGE);
        } else if (userRegisterBindingModel.getPassword().length() < GlobalConstants.PASSWORD_MIN_LENGTH) {
            errors.rejectValue("password",
                    GlobalConstants.PASSWORD_LENGTH_VALIDATION_MESSAGE,
                    GlobalConstants.PASSWORD_LENGTH_VALIDATION_MESSAGE);
        }

        if (!userRegisterBindingModel.getPassword()
                .equals(userRegisterBindingModel.getConfirmPassword())) {
            errors.rejectValue("confirmPassword",
                    GlobalConstants.PASSWORDS_MUST_MATCH_VALIDATION_MESSAGE,
                    GlobalConstants.PASSWORDS_MUST_MATCH_VALIDATION_MESSAGE);
        }

        if (userRegisterBindingModel.getEmail() == null
                || userRegisterBindingModel.getEmail().equals("")) {
            errors.rejectValue("email",
                    GlobalConstants.EMAIL_NOT_NULL_OR_EMPTY_VALIDATION_MESSAGE,
                    GlobalConstants.EMAIL_NOT_NULL_OR_EMPTY_VALIDATION_MESSAGE);
        }

    }
}
