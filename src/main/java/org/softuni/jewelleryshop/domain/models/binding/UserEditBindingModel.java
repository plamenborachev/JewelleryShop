package org.softuni.jewelleryshop.domain.models.binding;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserEditBindingModel {

    private String username;
    private String oldPassword;
    private String password;
    private String confirmPassword;
    private String email;

    public UserEditBindingModel() {
    }

    @NotNull
    @NotEmpty
    @Size(min = 5, max = 20, message = "Username must be between 5 and 20 characters!")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotNull
    @NotEmpty
    @Size(min = 5, message = "Password must be at least 5 symbols!")
    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    @NotNull
    @NotEmpty
    @Size(min = 5, message = "Password must be at least 5 symbols!")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotNull
    @NotEmpty
    @Size(min = 5, message = "Confirm password must be at least 5 symbols!")
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @NotNull
    @NotEmpty
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
