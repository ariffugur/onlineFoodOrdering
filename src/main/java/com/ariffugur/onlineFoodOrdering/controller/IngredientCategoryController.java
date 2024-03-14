package com.ariffugur.onlineFoodOrdering.controller;

import com.ariffugur.onlineFoodOrdering.dto.IngredientCategoryRequest;
import com.ariffugur.onlineFoodOrdering.model.IngredientCategory;
import com.ariffugur.onlineFoodOrdering.service.IngredientCategoryService;
import com.ariffugur.onlineFoodOrdering.service.IngredientsItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredientcategory")
public class IngredientCategoryController {
    private final IngredientCategoryService ingredientCategoryService;

    public IngredientCategoryController(IngredientCategoryService ingredientCategoryService) {
        this.ingredientCategoryService = ingredientCategoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<IngredientCategory> createIngredientCategory(@RequestHeader("Authorization") String token, @RequestBody IngredientCategoryRequest request) {
        IngredientCategory ingredientCategory = ingredientCategoryService.createIngredientCategory(request.name(), request.restaurantId());
        return new ResponseEntity<>(ingredientCategory, HttpStatus.CREATED);

    }
    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<IngredientCategory>> findIngredientCategoryByRestaurantId(@PathVariable("id") Long id) {
        List<IngredientCategory> ingredientCategories = ingredientCategoryService.findIngredientCategoryByRestaurantId(id);
        return new ResponseEntity<>(ingredientCategories, HttpStatus.OK);
    }
}
