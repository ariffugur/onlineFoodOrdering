package com.ariffugur.onlineFoodOrdering.service;

import com.ariffugur.onlineFoodOrdering.model.IngredientCategory;
import com.ariffugur.onlineFoodOrdering.model.IngredientsItem;
import com.ariffugur.onlineFoodOrdering.model.Restaurant;
import com.ariffugur.onlineFoodOrdering.repository.IngredientsItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientsItemService {
    private final IngredientsItemRepository ingredientsItemRepository;
    private final RestaurantService restaurantService;
    private final CategoryService categoryService;
    private final IngredientCategoryService ingredientCategoryService;

    public IngredientsItemService(IngredientsItemRepository ingredientsItemRepository, RestaurantService restaurantService, CategoryService categoryService, IngredientCategoryService ingredientCategoryService) {
        this.ingredientsItemRepository = ingredientsItemRepository;
        this.restaurantService = restaurantService;
        this.categoryService = categoryService;
        this.ingredientCategoryService = ingredientCategoryService;
    }

    public IngredientsItem createIngredientsItem(String ingredientName, Long restaurantId, Long categoryId) {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientCategory ingredientCategory = ingredientCategoryService.findIngredientCategoryById(categoryId);

        IngredientsItem ingredientsItem = IngredientsItem.builder()
                .name(ingredientName)
                .restaurant(restaurant)
                .ingredientCategory(ingredientCategory)
                .build();
        IngredientsItem savedIngredientsItem = ingredientsItemRepository.save(ingredientsItem);
        ingredientCategory.getIngredients().add(savedIngredientsItem);
        return savedIngredientsItem;
    }

    public List<IngredientsItem> findIngredientsItemByRestaurantId(Long id) {
        return ingredientsItemRepository.findByRestaurantId(id);
    }

    public IngredientsItem updateStock(Long id) throws Exception {
        Optional<IngredientsItem> OptionalIngredientsItem = ingredientsItemRepository.findById(id);
        if (OptionalIngredientsItem.isEmpty()) {
            throw new Exception("Ingredients Item not found");
        }
        IngredientsItem ingredientsItem = OptionalIngredientsItem.get();
        ingredientsItem.setInStoke(!ingredientsItem.isInStoke());
        return ingredientsItemRepository.save(ingredientsItem);
    }

}
