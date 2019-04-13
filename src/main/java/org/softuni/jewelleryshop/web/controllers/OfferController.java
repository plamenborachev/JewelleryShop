package org.softuni.jewelleryshop.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.jewelleryshop.domain.models.view.CategoryViewModel;
import org.softuni.jewelleryshop.domain.models.view.OfferViewModel;
import org.softuni.jewelleryshop.service.CategoryService;
import org.softuni.jewelleryshop.service.OfferService;
import org.softuni.jewelleryshop.web.annotations.PageTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class OfferController extends BaseController {

    private final OfferService offerService;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public OfferController(OfferService offerService, CategoryService categoryService, ModelMapper modelMapper) {
        this.offerService = offerService;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/top-offers")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Top Offers")
    public ModelAndView topOffers(ModelAndView modelAndView) {
        List<CategoryViewModel> categories = this.categoryService
                .findAllCategories()
                .stream()
                .map(category -> this.modelMapper.map(category, CategoryViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("categories", categories);
        return view("offer/top-offers", modelAndView);
    }

    @GetMapping("/top-offers/fetch/{category}")
    @ResponseBody
    public List<OfferViewModel> fetchByCategory(@PathVariable String category) {
        if (category.equals("all")) {
            return this.offerService.findAllOffers()
                    .stream()
                    .map(offers -> this.modelMapper.map(offers, OfferViewModel.class))
                    .collect(Collectors.toList());
        }
        return this.offerService.findAllByCategory(category)
                .stream()
                .map(offers -> this.modelMapper.map(offers, OfferViewModel.class))
                .collect(Collectors.toList());
    }
}
