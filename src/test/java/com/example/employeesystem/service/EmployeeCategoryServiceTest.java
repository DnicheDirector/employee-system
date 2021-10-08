package com.example.employeesystem.service;

import com.example.employeesystem.data.EmployeeCategoryRepository;
import com.example.employeesystem.domain.EmployeeCategory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class EmployeeCategoryServiceTest {

    @MockBean
    private EmployeeCategoryRepository categoryRepository;

    @Autowired
    private EmployeeCategoryService categoryService;

    private static List<EmployeeCategory> categories;

    @BeforeAll
    public static void initCategories(){
        EmployeeCategory category1 = new EmployeeCategory("PM");
        EmployeeCategory category2 = new EmployeeCategory("Director");

        categories = new ArrayList<>();
        categories.add(category1);
        categories.add(category2);
    }

    @Test
    void save() {
        EmployeeCategory category = categories.get(1);

        when(categoryRepository.save(category))
                .thenReturn(category);

        EmployeeCategory saved = categoryService.save(categories.get(1));

        assertNotNull(saved);
        assertEquals(category, saved);

        verify(categoryRepository, times(1))
                .save(category);
    }

    @Test
    void getAll() {
        when(categoryRepository.findAll())
                .thenReturn(categories);

        Iterable<EmployeeCategory> all = categoryService.getAll();

        assertNotNull(all);
        assertEquals(categories, all);

        verify(categoryRepository, times(1))
                .findAll();
    }

    @Test
    void findCategoryById() {
        EmployeeCategory category = categories.get(1);

        when(categoryRepository.findById(category.getId()))
                .thenReturn(Optional.of(category));

        EmployeeCategory returned = categoryService.findCategoryById(category.getId());

        assertNotNull(returned);
        assertEquals(category, returned);

        verify(categoryRepository, times(1))
                .findById(category.getId());
    }

    @Test
    void deleteCategory() {
        Long id = 1L;

        doNothing().when(categoryRepository).deleteById(id);

        categoryService.deleteCategory(id);

        verify(categoryRepository, times(1))
                .deleteById(id);
    }
}