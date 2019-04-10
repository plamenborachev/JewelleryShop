package org.softuni.jewelleryshop.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.jewelleryshop.domain.models.view.CategoryViewModel;
import org.softuni.jewelleryshop.service.CategoryService;
import org.softuni.jewelleryshop.web.annotations.PageTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController extends BaseController {

    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public HomeController(CategoryService categoryService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    @PreAuthorize("isAnonymous()")
    @PageTitle("Index")
    public ModelAndView index() {
        return view("index");
    }

    @GetMapping("/home")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Home")
    public ModelAndView home(ModelAndView modelAndView) {
        List<CategoryViewModel> categories = this.categoryService
                .findAllCategories()
                .stream()
                .map(category -> this.modelMapper.map(category, CategoryViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("categories", categories);
        return view("/users/home", modelAndView);
    }
}
