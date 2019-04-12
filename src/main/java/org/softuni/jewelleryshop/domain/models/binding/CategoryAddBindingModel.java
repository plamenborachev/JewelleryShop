package org.softuni.jewelleryshop.domain.models.binding;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CategoryAddBindingModel {

    private String name;

    public CategoryAddBindingModel() {
    }

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 20, message = "Category name must be between 3 and 20 characters!")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
