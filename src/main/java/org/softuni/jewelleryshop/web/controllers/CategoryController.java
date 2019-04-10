package org.softuni.jewelleryshop.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.jewelleryshop.domain.models.binding.CategoryAddBindingModel;
import org.softuni.jewelleryshop.domain.models.service.CategoryServiceModel;
import org.softuni.jewelleryshop.domain.models.view.CategoryViewModel;
import org.softuni.jewelleryshop.service.CategoryService;
import org.softuni.jewelleryshop.validation.CategoryAddValidator;
import org.softuni.jewelleryshop.validation.CategoryEditValidator;
import org.softuni.jewelleryshop.web.annotations.PageTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/categories")
public class CategoryController extends BaseController {

    private final CategoryService categoryService;
    private final ModelMapper modelMapper;
    private final CategoryAddValidator addValidator;
    private final CategoryEditValidator editValidator;

    @Autowired
    public CategoryController(CategoryService categoryService, ModelMapper modelMapper, CategoryAddValidator addValidator, CategoryEditValidator editValidator) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
        this.addValidator = addValidator;
        this.editValidator = editValidator;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PageTitle("Add Category")
    public ModelAndView addCategory(ModelAndView modelAndView, @ModelAttribute(name = "model") CategoryAddBindingModel model) {
        modelAndView.addObject("model", model);
        return view("category/add-category", modelAndView);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addCategoryConfirm(ModelAndView modelAndView,
                                           @ModelAttribute(name = "model") CategoryAddBindingModel model,
                                           BindingResult bindingResult) {
        this.addValidator.validate(model, bindingResult);
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("model", model);
            return view("category/add-category", modelAndView);
        }
        this.categoryService.addCategory(this.modelMapper.map(model, CategoryServiceModel.class));
        return redirect("/categories/all");
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PageTitle("All Categories")
    public ModelAndView allCategories(ModelAndView modelAndView) {
        modelAndView.addObject("categories",
                this.categoryService.findAllCategories()
                        .stream()
                        .map(c -> this.modelMapper.map(c, CategoryViewModel.class))
                        .collect(Collectors.toList())
        );

        return view("category/all-categories", modelAndView);
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PageTitle("Edit Category")
    public ModelAndView editCategory(@PathVariable String id,
                                     ModelAndView modelAndView,
                                     @ModelAttribute(name = "model") CategoryAddBindingModel model) {
        model = this.modelMapper
                .map(this.categoryService.findCategoryById(id), CategoryAddBindingModel.class);
        modelAndView.addObject("categoryId", id);
        modelAndView.addObject("model", model);
        return view("category/edit-category", modelAndView);
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView editCategoryConfirm(@PathVariable String id, ModelAndView modelAndView,
                                            @ModelAttribute(name = "model") CategoryAddBindingModel model,
                                            BindingResult bindingResult) {
        this.editValidator.validate(model, bindingResult);
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("categoryId", id);
            modelAndView.addObject("model", model);
            return view("category/edit-category", modelAndView);
        }
        this.categoryService.editCategory(id, this.modelMapper.map(model, CategoryServiceModel.class));
        return redirect("/categories/all");
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PageTitle("Delete Category")
    public ModelAndView deleteCategory(@PathVariable String id, ModelAndView modelAndView) {
        modelAndView.addObject("model",
                this.modelMapper.map(this.categoryService.findCategoryById(id), CategoryViewModel.class)
        );

        return view("category/delete-category", modelAndView);
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView deleteCategoryConfirm(@PathVariable String id) {
        this.categoryService.deleteCategory(id);

        return redirect("/categories/all");
    }

    @GetMapping("/fetch")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @ResponseBody
    public List<CategoryViewModel> fetchCategories() {
        return this.categoryService.findAllCategories()
                .stream()
                .map(c -> this.modelMapper.map(c, CategoryViewModel.class))
                .collect(Collectors.toList());
    }
}
