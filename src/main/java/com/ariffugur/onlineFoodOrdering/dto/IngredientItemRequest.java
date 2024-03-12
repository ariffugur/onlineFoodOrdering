package com.ariffugur.onlineFoodOrdering.dto;

import lombok.Builder;

@Builder
public record IngredientItemRequest(
        Long id,
        String name,
        Long categoryId,
        Long restaurantId
) {
}
