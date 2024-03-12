package com.ariffugur.onlineFoodOrdering.service;

import com.ariffugur.onlineFoodOrdering.model.Category;
import com.ariffugur.onlineFoodOrdering.model.Restaurant;
import com.ariffugur.onlineFoodOrdering.model.User;
import com.ariffugur.onlineFoodOrdering.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final RestaurantService restaurantService;
    private final UserService userService;

    public CategoryService(CategoryRepository categoryRepository, RestaurantService restaurantService, UserService userService) {
        this.categoryRepository = categoryRepository;
        this.restaurantService = restaurantService;
        this.userService = userService;
    }

    public Category createRestaurantCategory(String jwt, Category category) {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.findRestaurantById(user.getId());
        Category newCategory = Category.builder()
                .name(category.getName())
                .restaurant(restaurant)
                .build();
        return categoryRepository.save(newCategory);
    }

    public List<Category> findCategoryByRestaurantId(String jwt, Category category) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.findRestaurantById(user.getId());
        return categoryRepository.findByRestaurantId(restaurant.getId());


    }

    public Category findCategoryById(Long id) throws Exception {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            return category.get();
        } else {
            throw new Exception("Category not found");
        }
    }
}
