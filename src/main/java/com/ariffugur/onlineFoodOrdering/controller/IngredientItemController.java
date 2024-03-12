package com.ariffugur.onlineFoodOrdering.controller;

import com.ariffugur.onlineFoodOrdering.dto.IngredientCategoryRequest;
import com.ariffugur.onlineFoodOrdering.dto.IngredientItemRequest;
import com.ariffugur.onlineFoodOrdering.model.IngredientCategory;
import com.ariffugur.onlineFoodOrdering.model.IngredientsItem;
import com.ariffugur.onlineFoodOrdering.service.IngredientsItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredient")
public class IngredientItemController {
    private final IngredientsItemService ingredientItemService;

    public IngredientItemController(IngredientsItemService ingredientItemService) {
        this.ingredientItemService = ingredientItemService;
    }

    @PostMapping("/create")
    public ResponseEntity<IngredientsItem> createIngredient(@RequestHeader("Authorization") String token, @RequestBody IngredientItemRequest request) {
        IngredientsItem ingredientItem = ingredientItemService.createIngredientsItem(request.name(), request.categoryId(), request.restaurantId());
        return new ResponseEntity(ingredientItem, HttpStatus.CREATED);

    }

    @PutMapping("/stock/{id}")
    public ResponseEntity<IngredientsItem> updateIngredient(@RequestHeader("Authorization") String token, @RequestBody Long id) throws Exception {
        IngredientsItem ingredientItem = ingredientItemService.updateStock(id);
        return new ResponseEntity(ingredientItem, HttpStatus.OK);

    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<IngredientsItem>> getRestaurantIngredient(@RequestHeader("Authorization") String token, @PathVariable Long id) throws Exception {
        List<IngredientsItem> ingredientItem = ingredientItemService.findIngredientsItemByRestaurantId(id);
        return new ResponseEntity<>(ingredientItem, HttpStatus.OK);

    }
}
