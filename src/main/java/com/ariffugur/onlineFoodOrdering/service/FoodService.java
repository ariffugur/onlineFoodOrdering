package com.ariffugur.onlineFoodOrdering.service;

import com.ariffugur.onlineFoodOrdering.dto.CreateFoodRequest;
import com.ariffugur.onlineFoodOrdering.model.*;
import com.ariffugur.onlineFoodOrdering.repository.FoodRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Date;

@Service
public class FoodService {
    private final FoodRepository foodRepository;
    private final RestaurantService restaurantService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final IngredientsItemService ingredientsItemService;

    public FoodService(FoodRepository foodRepository, RestaurantService restaurantService, UserService userService, CategoryService categoryService, IngredientsItemService ingredientsItemService) {
        this.foodRepository = foodRepository;
        this.restaurantService = restaurantService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.ingredientsItemService = ingredientsItemService;
    }

    public Food createFood(String jwt, CreateFoodRequest createFoodRequest) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.findRestaurantByUserId(user.getId());
        Category category = categoryService.createRestaurantCategory(jwt, createFoodRequest.getCategory());
        List<IngredientsItem> ingredientsItems = ingredientsItemService.saveInredientsItemList(createFoodRequest.getIngredients());
        Food food = Food.builder()
                .name(createFoodRequest.getName())
                .price(createFoodRequest.getPrice())
                .description(createFoodRequest.getDescription())
                .foodCategory(category)
                .creationDate(new Date())
                .images(createFoodRequest.getImages())
                .restaurant(restaurant)
                .isVegan(createFoodRequest.isVegan())
                .isSeasonal(createFoodRequest.isSeasonal())
                .ingredients(ingredientsItems)
                .build();
        Food savedFood = foodRepository.save(food);
        restaurant.getFoods().add(savedFood);
        return savedFood;
    }

    public void deleteFood(String jwt, Long id) {
        User user = userService.findUserByJwtToken(jwt);
        Food food = findFoodById(jwt, id);
        try {
            foodRepository.delete(food);
        } catch (Exception e) {
            throw new UsernameNotFoundException("Food not found");
        }

    }

    public Food findFoodById(String jwt, Long id) {
        User user = userService.findUserByJwtToken(jwt);
        return foodRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Food not found"));
    }

    public List<Food> getRestaurantFood(String jwt, Long restaurantId, boolean isVegan, boolean isSeasonal, String foodCategory) {
        User user = userService.findUserByJwtToken(jwt);
        List<Food> foods = foodRepository.findByRestaurantId(restaurantId);
        if (isVegan) {
            foods = filtlerByVegan(foods, isVegan);
        }
        if (isSeasonal) {
            foods = filterBySeason(foods, isSeasonal);
        }
        if (foodCategory != null && !foodCategory.equals("")) {
            foods = filterByCategory(foods, foodCategory);
        }
        return foods;
    }

    private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
        return foods.stream().filter(x -> {
            if (x.getFoodCategory() != null) {
                return x.getFoodCategory().getName().equals(foodCategory);
            }
            return false;
        }).collect(Collectors.toList());
    }

    private List<Food> filtlerByVegan(List<Food> foods, boolean isVegan) {
        return foods.stream().filter(x -> x.isVegan() == isVegan).collect(Collectors.toList());
    }

    private List<Food> filterBySeason(List<Food> foods, boolean isSeasonal) {
        return foods.stream().filter(x -> x.isSeasonal() == isSeasonal).collect(Collectors.toList());
    }

    public List<Food> searchFood(String jwt, String query) {
        User user = userService.findUserByJwtToken(jwt);
        return foodRepository.searchFood(query);
    }

    public Food updateAvailibilitiyStatus(String jwt, Long id) throws Exception {
        Food food = findFoodById(jwt, id);
        food.setAvailable(!food.isAvailable());
        return foodRepository.save(food);
    }
}