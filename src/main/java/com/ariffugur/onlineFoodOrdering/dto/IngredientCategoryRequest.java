package com.ariffugur.onlineFoodOrdering.dto;

import com.ariffugur.onlineFoodOrdering.model.Address;
import com.ariffugur.onlineFoodOrdering.model.ContactInformation;
import lombok.Builder;

import java.util.List;

@Builder
public record IngredientCategoryRequest(
        Long id,
        String name,
        Long restaurantId
        ) {
}
