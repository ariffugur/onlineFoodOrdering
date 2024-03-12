package com.ariffugur.onlineFoodOrdering.service;

import com.ariffugur.onlineFoodOrdering.model.IngredientCategory;
import com.ariffugur.onlineFoodOrdering.model.Restaurant;
import com.ariffugur.onlineFoodOrdering.repository.IngredientCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientCategoryService {
    private final IngredientCategoryRepository ingredientCategoryRepository;
    private final RestaurantService restaurantService;

    public IngredientCategoryService(IngredientCategoryRepository ingredientCategoryRepository, RestaurantService restaurantService) {
        this.ingredientCategoryRepository = ingredientCategoryRepository;
        this.restaurantService = restaurantService;
    }

    public IngredientCategory createIngredientCategory(String name, Long restaurantId) {
        {
            Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
            IngredientCategory ingredientCategory = IngredientCategory.builder()
                    .restaurant(restaurant)
                    .name(name)
                    .build();

            return ingredientCategoryRepository.save(ingredientCategory);
        }
    }

    public IngredientCategory findIngredientCategoryById(Long id) {
        Optional<IngredientCategory> ingredientCategory = ingredientCategoryRepository.findById(id);
        if (ingredientCategory.isPresent()) {
            return ingredientCategory.get();
        } else {
            throw new RuntimeException("Ingredient Category not found");
        }
    }

    public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id) {
        Restaurant restaurant = restaurantService.findRestaurantById(id);
        return ingredientCategoryRepository.findByRestaurantId(restaurant.getId());
    }
}
