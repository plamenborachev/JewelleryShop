package org.softuni.jewelleryshop.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.jewelleryshop.domain.models.binding.ProductAddBindingModel;
import org.softuni.jewelleryshop.domain.models.service.CategoryServiceModel;
import org.softuni.jewelleryshop.domain.models.service.ProductServiceModel;
import org.softuni.jewelleryshop.domain.models.view.ProductAllViewModel;
import org.softuni.jewelleryshop.domain.models.view.ProductDetailsViewModel;
import org.softuni.jewelleryshop.error.ProductNameAlreadyExistsException;
import org.softuni.jewelleryshop.error.ProductNotFoundException;
import org.softuni.jewelleryshop.service.CategoryService;
import org.softuni.jewelleryshop.service.CloudinaryService;
import org.softuni.jewelleryshop.service.ProductService;
import org.softuni.jewelleryshop.web.annotations.PageTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductController extends BaseController {

    private final ProductService productService;
    private final CloudinaryService cloudinaryService;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductController(ProductService productService, CloudinaryService cloudinaryService, CategoryService categoryService, ModelMapper modelMapper) {
        this.productService = productService;
        this.cloudinaryService = cloudinaryService;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PageTitle("Add Product")
    public ModelAndView addProduct() {
        return super.view("product/add-product");
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addProductConfirm(@ModelAttribute ProductAddBindingModel model) throws IOException {
        ProductServiceModel productServiceModel = this.modelMapper.map(model, ProductServiceModel.class);
        productServiceModel.setCategories(
                this.categoryService.findAllCategories()
                        .stream()
                        .filter(c -> model.getCategories().contains(c.getId()))
                        .collect(Collectors.toList())
        );
        productServiceModel.setImageUrl(
                this.cloudinaryService.uploadImage(model.getImage())
        );
        this.productService.createProduct(productServiceModel);
        return redirect("/products/all");
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PageTitle("All Products")
    public ModelAndView allProducts(ModelAndView modelAndView) {
        modelAndView.addObject("products",
                this.productService.findAllProducts()
                        .stream()
                        .map(p -> this.modelMapper.map(p, ProductAllViewModel.class))
                        .collect(Collectors.toList()));
        return view("product/all-products", modelAndView);
    }

    @GetMapping("/details/{id}")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Product Details")
    public ModelAndView detailsProduct(@PathVariable String id, ModelAndView modelAndView) {
        ProductDetailsViewModel model = this.modelMapper
                .map(this.productService.findProductById(id), ProductDetailsViewModel.class);
        modelAndView.addObject("product", model);
        return view("product/details", modelAndView);
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PageTitle("Edit Product")
    public ModelAndView editProduct(@PathVariable String id, ModelAndView modelAndView) {
        ProductServiceModel productServiceModel = this.productService.findProductById(id);
        ProductAddBindingModel model = this.modelMapper.map(productServiceModel, ProductAddBindingModel.class);
        model.setCategories(productServiceModel.getCategories().stream().map(c -> c.getName()).collect(Collectors.toList()));

        modelAndView.addObject("product", model);
        modelAndView.addObject("productId", id);
        return view("product/edit-product", modelAndView);
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView editProductConfirm(@PathVariable String id,
                                           @ModelAttribute ProductAddBindingModel model) {
        ProductServiceModel productServiceModel = this.modelMapper.map(model, ProductServiceModel.class);
        productServiceModel.setCategories(model.getCategories().stream()
                .map(c -> this.modelMapper
                        .map(this.categoryService.findCategoryById(c), CategoryServiceModel.class))
                .collect(Collectors.toList()));
        this.productService.editProduct(id, productServiceModel);
        return redirect("/products/details/" + id);
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PageTitle("Delete Product")
    public ModelAndView deleteProduct(@PathVariable String id, ModelAndView modelAndView) {
        ProductServiceModel productServiceModel = this.productService.findProductById(id);
        ProductAddBindingModel model = this.modelMapper.map(productServiceModel, ProductAddBindingModel.class);
        model.setCategories(productServiceModel.getCategories().stream().map(c -> c.getName()).collect(Collectors.toList()));

        modelAndView.addObject("product", model);
        modelAndView.addObject("productId", id);

        return view("product/delete-product", modelAndView);
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView deleteProductConfirm(@PathVariable String id) {
        this.productService.deleteProduct(id);
        return redirect("/products/all");
    }

    @GetMapping("/fetch/{category}")
    @ResponseBody
    public List<ProductAllViewModel> fetchByCategory(@PathVariable String category) {
        if (category.equals("all")) {
            return this.productService.findAllProducts()
                    .stream()
                    .map(product -> this.modelMapper.map(product, ProductAllViewModel.class))
                    .collect(Collectors.toList());
        }

        return this.productService.findAllByCategory(category)
                .stream()
                .map(product -> this.modelMapper.map(product, ProductAllViewModel.class))
                .collect(Collectors.toList());
    }

    @ExceptionHandler({ProductNotFoundException.class})
    public ModelAndView handleProductNotFound(ProductNotFoundException e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", e.getMessage());
        modelAndView.addObject("statusCode", e.getStatusCode());
        return modelAndView;
    }

    @ExceptionHandler({ProductNameAlreadyExistsException.class})
    public ModelAndView handleProductNameALreadyExist(ProductNameAlreadyExistsException e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", e.getMessage());
        modelAndView.addObject("statusCode", e.getStatusCode());
        return modelAndView;
    }
}
