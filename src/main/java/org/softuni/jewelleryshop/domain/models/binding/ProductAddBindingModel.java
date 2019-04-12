package org.softuni.jewelleryshop.domain.models.binding;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

public class ProductAddBindingModel {

    private String name;
    private String description;
    private BigDecimal price;
    private MultipartFile image;
    private List<String> categories;

    public ProductAddBindingModel() {
    }

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 40, message = "Jewellery name must be between 3 and 40 characters!")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 40, message = "Description must be between 15 and 100 characters!")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull
    @NotEmpty
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @NotNull
    @NotEmpty
    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    @NotNull
    @NotEmpty
    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}
