package com.ariffugur.onlineFoodOrdering.controller;

import com.ariffugur.onlineFoodOrdering.dto.CreateRestaurantRequest;
import com.ariffugur.onlineFoodOrdering.dto.RestaurantDto;
import com.ariffugur.onlineFoodOrdering.model.Restaurant;
import com.ariffugur.onlineFoodOrdering.model.User;
import com.ariffugur.onlineFoodOrdering.response.MessageResponse;
import com.ariffugur.onlineFoodOrdering.service.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {
    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }



    @PostMapping("/create")
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody CreateRestaurantRequest restaurant, @RequestHeader("Authorization") String token) {
        Restaurant createdRestaurant = restaurantService.createRestaurant(restaurant, token);
        return new ResponseEntity<>(createdRestaurant, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@RequestBody CreateRestaurantRequest restaurant, @RequestHeader("Authorization") String token, @RequestParam Long id) {
        Restaurant updateRestaurant = restaurantService.updateRestaurant(id, restaurant, token);
        return new ResponseEntity<>(updateRestaurant, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessageResponse> deleteRestaurant(@RequestHeader("Authorization") String token, @RequestParam Long id) throws Exception {
        restaurantService.deleteRestaurant(id);
        MessageResponse messageResponse = MessageResponse.builder()
                .message("Restaurant deleted successfully!").build();
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<Restaurant> updateRestaurantStatus(@RequestParam Long id, @RequestHeader("Authorization") String token) throws Exception {
        Restaurant restaurant = restaurantService.updateRestaurantStatus(id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<Restaurant> findRestaurantByUserId(@RequestHeader("Authorization") String token, @RequestParam Long id) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurants(@RequestHeader("Authorization") String token, @RequestParam String query) throws Exception {
        List<Restaurant> restaurants = restaurantService.searchRestaurants(query);
        return new ResponseEntity<>(restaurants, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> findRestaurantById(@RequestHeader("Authorization") String token, @PathVariable Long id) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);

    }

    @GetMapping("/addFavorites/{id}")
    public ResponseEntity<RestaurantDto> addFavoritesByUserId(@RequestHeader("Authorization") String token, @PathVariable Long id) throws Exception {
        RestaurantDto restaurant = restaurantService.addToFavorites(token, id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);

    }
}