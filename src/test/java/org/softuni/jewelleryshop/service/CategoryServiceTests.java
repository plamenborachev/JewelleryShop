package org.softuni.jewelleryshop.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.jewelleryshop.domain.entities.Category;
import org.softuni.jewelleryshop.domain.models.service.CategoryServiceModel;
import org.softuni.jewelleryshop.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.Validation;
import javax.validation.Validator;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CategoryServiceTests {

    @Autowired
    private CategoryRepository categoryRepository;
    private Validator validator;
    private ModelMapper modelMapper;
    private CategoryService categoryService;
    private Category testCategory;

    @Before
    public void init() {
        this.modelMapper = new ModelMapper();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.categoryService
                = new CategoryServiceImpl(this.categoryRepository, this.modelMapper, this.validator);
        this.testCategory = new Category();
        testCategory.setName("TestCategory");
    }

    @Test
    public void categoryService_SaveCategoryWithCorrectValue_ReturnsCorrect() {
        CategoryServiceModel toBeSaved = this.modelMapper.map(this.testCategory, CategoryServiceModel.class);

        CategoryServiceModel actual = this.categoryService.addCategory(toBeSaved);
        CategoryServiceModel expected = this.modelMapper
                .map(this.categoryRepository.findAll().get(0), CategoryServiceModel.class);

        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getName(), actual.getName());
    }

    @Test(expected = Exception.class)
    public void categoryService_SaveCategoryWithNullValue_ThrowsException() {
        CategoryServiceModel toBeSaved = new CategoryServiceModel();
        toBeSaved.setName(null);

        this.categoryService.addCategory(toBeSaved);
    }

    @Test(expected = Exception.class)
    public void categoryService_SaveCategoryWithEmptyValue_ThrowsException() {
        CategoryServiceModel toBeSaved = new CategoryServiceModel();
        toBeSaved.setName("");

        this.categoryService.addCategory(toBeSaved);
    }

    @Test
    public void categoryService_EditCategoryWithCorrectValue_ReturnsCorrect() {
        Category category = this.categoryRepository.saveAndFlush(this.testCategory);

        CategoryServiceModel toBeEdited = new CategoryServiceModel();
        toBeEdited.setId(category.getId());
        toBeEdited.setName("new name");

        CategoryServiceModel actual = this.categoryService.editCategory(category.getId(), toBeEdited);
        CategoryServiceModel expected = this.modelMapper
                .map(this.categoryRepository.findAll().get(0), CategoryServiceModel.class);

        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getName(), actual.getName());
    }

    @Test(expected = Exception.class)
    public void categoryService_EditCategoryWithNullValue_ThrowsException() {
        Category category = this.categoryRepository.saveAndFlush(this.testCategory);

        CategoryServiceModel toBeEdited = new CategoryServiceModel();
        toBeEdited.setId(category.getId());
        toBeEdited.setName(null);

        this.categoryService.editCategory(category.getId(), toBeEdited);
    }

    @Test
    public void categoryService_DeleteCategoryWithCorrectValue_ReturnsCorrect() {
        Category category = this.categoryRepository.saveAndFlush(this.testCategory);

        this.categoryService.deleteCategory(category.getId());

        long expectedCount = 0;
        long actualCount = this.categoryRepository.count();

        Assert.assertEquals(expectedCount, actualCount);
    }

    @Test(expected = Exception.class)
    public void categoryService_DeleteCategoryWithInvalidId_ThrowsException() {
        Category category = this.categoryRepository.saveAndFlush(this.testCategory);
        this.categoryService.deleteCategory("invalidId");
    }


    @Test
    public void categoryService_FindCategoryByIdWithCorrectId_ReturnsCorrect() {
        Category category = this.categoryRepository.saveAndFlush(this.testCategory);

        CategoryServiceModel actual = this.categoryService.findCategoryById(category.getId());
        CategoryServiceModel expected = this.modelMapper.map(category, CategoryServiceModel.class);

        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getName(), actual.getName());
    }

    @Test(expected = Exception.class)
    public void categoryService_FindCategoryByIdWithWithInvalidId_ThrowsException() {
        Category category = this.categoryRepository.saveAndFlush(this.testCategory);
        this.categoryService.findCategoryById("invalidId");
    }

    @Test
    public void categoryService_FindAllCategories_ReturnsCorrect() {
        Category category = this.categoryRepository.saveAndFlush(this.testCategory);

        long expectedCount = 1;
        long actualCount = this.categoryService.findAllCategories().size();

        Assert.assertEquals(expectedCount, actualCount);
    }
}
