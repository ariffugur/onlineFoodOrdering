package com.ariffugur.onlineFoodOrdering.controller;

import com.ariffugur.onlineFoodOrdering.model.Category;
import com.ariffugur.onlineFoodOrdering.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<Category> createCategory(@RequestHeader("Authorization") String token, @RequestBody Category category) {
        return ResponseEntity.ok(categoryService.createRestaurantCategory(token, category));
    }

    @GetMapping("/getRestaurantCategory")
    public List<Category> getRestaurantCategory(@RequestHeader("Authorization") String token, @RequestBody Category category) throws Exception {
        return categoryService.findCategoryByRestaurantId(token, category);
    }
}
