package com.ariffugur.onlineFoodOrdering.controller;

import com.ariffugur.onlineFoodOrdering.dto.CreateFoodRequest;
import com.ariffugur.onlineFoodOrdering.model.Food;
import com.ariffugur.onlineFoodOrdering.response.MessageResponse;
import com.ariffugur.onlineFoodOrdering.service.FoodService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/food")
public class FoodController {
    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @PostMapping("/create")
    public ResponseEntity<Food> createFood(@RequestHeader("Authorization") String token, @RequestBody CreateFoodRequest request) throws Exception {
        return new ResponseEntity<>(foodService.createFood(token, request), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessageResponse> deleteFood(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        foodService.deleteFood(token, id);
        MessageResponse messageResponse = MessageResponse.builder()
                .message("Food deleted")
                .build();
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

    @GetMapping("/findfoodbyid/{id}")
    public ResponseEntity<Food> findFoodById(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        return new ResponseEntity<>(foodService.findFoodById(token, id), HttpStatus.OK);
    }

    @GetMapping("search/{query}")
    public ResponseEntity<List<Food>> searchFood(@RequestHeader("Authorization") String token, @PathVariable String query) {
        return new ResponseEntity<>(foodService.searchFood(token, query), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Food> updateAvailibilitiyStatus(@RequestHeader("Authorization") String token, @PathVariable Long id) throws Exception {
        return new ResponseEntity<>(foodService.updateAvailibilitiyStatus(token, id), HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> findFoodsByRestaurantId(@RequestHeader("Authorization") String token,
                                                              @RequestParam Long restaurantId,
                                                              @RequestParam boolean isVegan,
                                                              @RequestParam boolean isSeasonal,
                                                              @RequestParam(required = false) String foodCategories) throws Exception {
        List<Food> foods = foodService.getRestaurantFood(token, restaurantId, isVegan, isSeasonal, foodCategories);
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

}
