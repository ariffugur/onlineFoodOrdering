package com.ariffugur.onlineFoodOrdering.dto;

import com.ariffugur.onlineFoodOrdering.model.Category;
import com.ariffugur.onlineFoodOrdering.model.IngredientsItem;
import lombok.Data;

import java.util.List;


public record CreateFoodRequest(
        String name,
        Long price,
        String description,
        Category category,
        List<String> images,
        Long restaurantId,
        boolean vegan,
        boolean seasonal,
        List<IngredientsItem> ingredients
) {
}
