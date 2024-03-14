package com.ariffugur.onlineFoodOrdering.dto;

import com.ariffugur.onlineFoodOrdering.model.Category;
import com.ariffugur.onlineFoodOrdering.model.IngredientsItem;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CreateFoodRequest {
    private String name;
    private Long price;
    private String description;
    private Category category;
    private List<String> images;
    private Long restaurantId;
    private boolean vegan;
    private boolean seasonal;
    @JsonProperty
    private List<IngredientsItem> ingredients;
}