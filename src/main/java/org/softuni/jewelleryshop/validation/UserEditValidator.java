package org.softuni.jewelleryshop.validation;

import org.softuni.jewelleryshop.GlobalConstants;
import org.softuni.jewelleryshop.domain.entities.User;
import org.softuni.jewelleryshop.domain.models.binding.UserEditBindingModel;
import org.softuni.jewelleryshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserEditValidator implements Validator {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserEditValidator(UserRepository userRepository,
                             BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UserEditBindingModel.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        UserEditBindingModel userEditBindingModel = (UserEditBindingModel) o;
        User user = this.userRepository.findByUsername(userEditBindingModel.getUsername())
                .orElse(null);
        String oldPassword = "";
        if (user != null){
            oldPassword = user.getPassword();
        }

        if (userEditBindingModel.getUsername() == null
                || userEditBindingModel.getUsername().equals("")) {
            errors.rejectValue("username",
                    GlobalConstants.USERNAME_NOT_NULL_OR_EMPTY_VALIDATION_MESSAGE,
                    GlobalConstants.USERNAME_NOT_NULL_OR_EMPTY_VALIDATION_MESSAGE);
        } else  if (userEditBindingModel.getUsername().length() < GlobalConstants.USERNAME_MIN_LENGTH
                || userEditBindingModel.getUsername().length() > GlobalConstants.USERNAME_MAX_LENGTH) {
            errors.rejectValue("username",
                    GlobalConstants.USERNAME_LENGTH_VALIDATION_MESSAGE,
                    GlobalConstants.USERNAME_LENGTH_VALIDATION_MESSAGE);
        }

        if (userEditBindingModel.getPassword() != null
                && !userEditBindingModel.getPassword().equals("")
                && !this.bCryptPasswordEncoder
                    .matches(userEditBindingModel.getOldPassword(), oldPassword)) {
            errors.rejectValue("oldPassword",
                    GlobalConstants.WRONG_PASSWORD_VALIDATION_MESSAGE,
                    GlobalConstants.WRONG_PASSWORD_VALIDATION_MESSAGE);
        }

        if (userEditBindingModel.getPassword() != null
                && !userEditBindingModel.getPassword().equals("")
                && userEditBindingModel.getPassword().length() < GlobalConstants.PASSWORD_MIN_LENGTH) {
            errors.rejectValue("password",
                    GlobalConstants.PASSWORD_LENGTH_VALIDATION_MESSAGE,
                    GlobalConstants.PASSWORD_LENGTH_VALIDATION_MESSAGE);
        }

        if (userEditBindingModel.getPassword() != null
                && !userEditBindingModel.getPassword().equals("")
                && !userEditBindingModel.getPassword().equals(userEditBindingModel.getConfirmPassword())) {
            errors.rejectValue("confirmPassword",
                    GlobalConstants.PASSWORDS_MUST_MATCH_VALIDATION_MESSAGE,
                    GlobalConstants.PASSWORDS_MUST_MATCH_VALIDATION_MESSAGE);
        }

        if (userEditBindingModel.getEmail() == null
                || userEditBindingModel.getEmail().equals("")) {
            errors.rejectValue("email",
                    GlobalConstants.EMAIL_NOT_NULL_OR_EMPTY_VALIDATION_MESSAGE,
                    GlobalConstants.EMAIL_NOT_NULL_OR_EMPTY_VALIDATION_MESSAGE);
        }

    }
}
