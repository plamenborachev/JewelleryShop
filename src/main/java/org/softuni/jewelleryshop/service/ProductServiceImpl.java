package org.softuni.jewelleryshop.service;

import org.modelmapper.ModelMapper;
import org.softuni.jewelleryshop.GlobalConstants;
import org.softuni.jewelleryshop.domain.entities.Category;
import org.softuni.jewelleryshop.domain.entities.Product;
import org.softuni.jewelleryshop.domain.models.service.ProductServiceModel;
import org.softuni.jewelleryshop.error.ProductNameAlreadyExistsException;
import org.softuni.jewelleryshop.error.ProductNotFoundException;
import org.softuni.jewelleryshop.repository.OfferRepository;
import org.softuni.jewelleryshop.repository.ProductRepository;
import org.softuni.jewelleryshop.validation.ProductValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final OfferRepository offerRepository;
    private final CategoryService categoryService;
    private final ProductValidationService productValidation;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl(
            ProductRepository productRepository,
            OfferRepository offerRepository, CategoryService categoryService,
            ProductValidationService productValidation,
            ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.offerRepository = offerRepository;
        this.categoryService = categoryService;
        this.productValidation = productValidation;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductServiceModel createProduct(ProductServiceModel productServiceModel) {
        if (!productValidation.isValid(productServiceModel)) {
            throw new IllegalArgumentException();
        }
        Product product = this.productRepository
                .findByName(productServiceModel.getName())
                .orElse(null);

        if (product != null) {
            throw new ProductNameAlreadyExistsException(GlobalConstants.PRODUCT_EXISTS_MESSAGE);
        }

        product = this.modelMapper.map(productServiceModel, Product.class);
        product = this.productRepository.save(product);

        return this.modelMapper.map(product, ProductServiceModel.class);
    }

    @Override
    public List<ProductServiceModel> findAllProducts() {
        return this.productRepository.findAll()
                .stream()
                .map(p -> this.modelMapper.map(p, ProductServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductServiceModel findProductById(String id) {
        return this.productRepository.findById(id)
                .map(p -> {
                    ProductServiceModel productServiceModel = this.modelMapper
                            .map(p, ProductServiceModel.class);
                    this.offerRepository
                            .findByProduct_Id(productServiceModel.getId())
                            .ifPresent(o -> productServiceModel.setDiscountedPrice(o.getPrice()));
                    return productServiceModel;
                })
                .orElseThrow(() -> new ProductNotFoundException(GlobalConstants.PRODUCT_NOT_FOUND_EXCEPTION_MESSAGE));
    }

    @Override
    public ProductServiceModel editProduct(String id, ProductServiceModel productServiceModel) {
        Product product = this.productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(GlobalConstants.PRODUCT_NOT_FOUND_EXCEPTION_MESSAGE));

        product.setName(productServiceModel.getName());
        product.setDescription(productServiceModel.getDescription());
        product.setPrice(productServiceModel.getPrice());
        product.setCategories(
                productServiceModel.getCategories()
                        .stream()
                        .map(c -> this.modelMapper.map(c, Category.class))
                        .collect(Collectors.toList())
        );

        this.offerRepository.findByProduct_Id(product.getId())
                .ifPresent((o) -> {
                    o.setPrice(product.getPrice().multiply(new BigDecimal(GlobalConstants.DISCOUNT_RATIO)));
                    this.offerRepository.save(o);
                });

        return this.modelMapper
                .map(this.productRepository.saveAndFlush(product), ProductServiceModel.class);
    }

    @Override
    public void deleteProduct(String id) {
        Product product = this.productRepository
                .findById(id)
                .orElseThrow(() -> new ProductNotFoundException(GlobalConstants.PRODUCT_NOT_FOUND_EXCEPTION_MESSAGE));
        this.productRepository.delete(product);
    }

    @Override
    public List<ProductServiceModel> findAllByCategory(String category) {
        return this.productRepository.findAll()
                .stream()
                .filter(product -> product.getCategories().stream().anyMatch(categoryStream -> categoryStream.getName().equals(category)))
                .map(product -> this.modelMapper.map(product, ProductServiceModel.class))
                .collect(Collectors.toList());
    }


}
