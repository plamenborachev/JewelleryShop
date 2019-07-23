package org.softuni.jewelleryshop.web.controllers;

import org.modelmapper.ModelMapper;

import org.softuni.jewelleryshop.domain.models.binding.UserEditBindingModel;
import org.softuni.jewelleryshop.domain.models.binding.UserRegisterBindingModel;
import org.softuni.jewelleryshop.domain.models.service.RoleServiceModel;
import org.softuni.jewelleryshop.domain.models.service.UserServiceModel;
import org.softuni.jewelleryshop.domain.models.view.UserAllViewModel;
import org.softuni.jewelleryshop.domain.models.view.UserProfileViewModel;
import org.softuni.jewelleryshop.service.UserService;
import org.softuni.jewelleryshop.validation.UserEditValidator;
import org.softuni.jewelleryshop.validation.UserRegisterValidator;
import org.softuni.jewelleryshop.web.annotations.PageTitle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {

    private final UserService userService;
    private final UserRegisterValidator userRegisterValidator;
    private final UserEditValidator userEditValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, UserRegisterValidator userRegisterValidator, UserEditValidator userEditValidator, ModelMapper modelMapper) {
        this.userService = userService;
        this.userRegisterValidator = userRegisterValidator;
        this.userEditValidator = userEditValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    @PageTitle("Register")
    public ModelAndView register(ModelAndView modelAndView,
                                 @ModelAttribute (name = "model") UserRegisterBindingModel model) {
        modelAndView.addObject("model", model);
        return view("users/register", modelAndView);
    }

    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView registerConfirm(ModelAndView modelAndView,
                                        @ModelAttribute (name = "model") UserRegisterBindingModel model,
                                        BindingResult bindingResult) {
        this.userRegisterValidator.validate(model, bindingResult);
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("model", model);
            return view("users/register", modelAndView);
        }
        this.userService.registerUser(this.modelMapper.map(model, UserServiceModel.class));
        return redirect("/users/login");
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    @PageTitle("Login")
    public ModelAndView login() {
        return view("/users/login.html");
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Profile")
    public ModelAndView profile(Principal principal, ModelAndView modelAndView) {
        modelAndView
                .addObject("model", this.modelMapper
                        .map(this.userService.findUserByUserName(principal.getName()), UserProfileViewModel.class));
        return view("/users/profile", modelAndView);
    }

    @GetMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Edit Profile")
    public ModelAndView editProfile(Principal principal,
                                    ModelAndView modelAndView,
                                    @ModelAttribute (name = "model") UserEditBindingModel model) {
        UserServiceModel userServiceModel = this.userService.findUserByUserName(principal.getName());
        model = this.modelMapper.map(userServiceModel, UserEditBindingModel.class);
        modelAndView.addObject("model", model);
        return view("/users/edit-profile", modelAndView);
    }

    @PatchMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editProfileConfirm(Principal principal,
                                           ModelAndView modelAndView,
                                           @ModelAttribute (name = "model") UserEditBindingModel model,
                                           BindingResult bindingResult) {
        this.userEditValidator.validate(model, bindingResult);
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("model", model);
            return view("/users/edit-profile", modelAndView);
        }
        this.userService.editUserProfile(this.modelMapper
                .map(model, UserServiceModel.class), model.getOldPassword());
        return redirect("/users/profile");
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle("All Users")
    public ModelAndView allUsers(ModelAndView modelAndView) {
        List<UserAllViewModel> users = this.userService.findAllUsers()
                .stream()
                .map(u -> {
                    UserAllViewModel user = this.modelMapper.map(u, UserAllViewModel.class);
                    user.setAuthorities(u.getAuthorities()
                            .stream()
                            .map(RoleServiceModel::getAuthority)
                            .collect(Collectors.toSet()));
                    return user;
                })
                .collect(Collectors.toList());
        modelAndView.addObject("users", users);
        return view("/users/all-users", modelAndView);
    }

    @PostMapping("/set-user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView setUser(@PathVariable String id) {
        this.userService.setUserRole(id, "user");
        return redirect("/users/all");
    }

    @PostMapping("/set-moderator/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView setModerator(@PathVariable String id) {
        this.userService.setUserRole(id, "moderator");
        return redirect("/users/all");
    }

    @PostMapping("/set-admin/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView setAdmin(@PathVariable String id) {
        this.userService.setUserRole(id, "admin");
        return redirect("/users/all");
    }

    @InitBinder
    private void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
