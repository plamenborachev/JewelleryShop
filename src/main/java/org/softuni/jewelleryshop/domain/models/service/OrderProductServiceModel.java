package org.softuni.jewelleryshop.domain.models.service;

import java.math.BigDecimal;

public class OrderProductServiceModel extends BaseServiceModel {

    private ProductServiceModel product;
    private BigDecimal price;
    private Integer quantity;

    public OrderProductServiceModel() {
    }

    public ProductServiceModel getProduct() {
        return product;
    }

    public void setProduct(ProductServiceModel product) {
        this.product = product;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
