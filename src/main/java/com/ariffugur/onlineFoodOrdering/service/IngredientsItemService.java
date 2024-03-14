package com.ariffugur.onlineFoodOrdering.service;

import com.ariffugur.onlineFoodOrdering.model.IngredientCategory;
import com.ariffugur.onlineFoodOrdering.model.IngredientsItem;
import com.ariffugur.onlineFoodOrdering.model.Restaurant;
import com.ariffugur.onlineFoodOrdering.model.User;
import com.ariffugur.onlineFoodOrdering.repository.IngredientsItemRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IngredientsItemService {
    private final IngredientsItemRepository ingredientsItemRepository;
    private final RestaurantService restaurantService;
    private final CategoryService categoryService;
    private final IngredientCategoryService ingredientCategoryService;
    private final UserService userService;

    public IngredientsItemService(IngredientsItemRepository ingredientsItemRepository, RestaurantService restaurantService, CategoryService categoryService, IngredientCategoryService ingredientCategoryService, UserService userService) {
        this.ingredientsItemRepository = ingredientsItemRepository;
        this.restaurantService = restaurantService;
        this.categoryService = categoryService;
        this.ingredientCategoryService = ingredientCategoryService;
        this.userService = userService;
    }

    public IngredientsItem createIngredientsItem(String jwt, String ingredientName, Long categoryId, Long restaurantId) {
        User user = userService.findUserByJwtToken(jwt);
        if (user == null) {
            throw new UsernameNotFoundException("User not found for JWT token: " + jwt);
        }
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        if (restaurant == null) {
            throw new UsernameNotFoundException("Restaurant not found for ID: " + restaurantId);
        }
        IngredientCategory ingredientCategory = ingredientCategoryService.findIngredientCategoryById(categoryId);
        if (ingredientCategory == null) {
            throw new UsernameNotFoundException("Ingredient category not found for ID: " + categoryId);
        }
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

    public IngredientsItem updateStock(String jwt, Long id) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Optional<IngredientsItem> OptionalIngredientsItem = ingredientsItemRepository.findById(id);
        if (OptionalIngredientsItem.isEmpty()) {
            throw new Exception("Ingredients Item not found");
        }
        IngredientsItem ingredientsItem = OptionalIngredientsItem.get();
        ingredientsItem.setInStock(!ingredientsItem.isInStock());
        return ingredientsItemRepository.save(ingredientsItem);
    }

    public List<IngredientsItem> saveInredientsItemList(List<IngredientsItem> ingredientsItems) {
        List<IngredientsItem> savedIngredientsItems = new ArrayList<>();
        for (IngredientsItem ingredientsItem : ingredientsItems) {
            IngredientsItem savedIngredientsItem = ingredientsItemRepository.save(ingredientsItem);
            savedIngredientsItems.add(savedIngredientsItem);
        }
        return savedIngredientsItems;
    }
}
