package com.ariffugur.onlineFoodOrdering.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record AddCartItemRequest(
        Long foodId,
        int quantity,
        List<String> ingredients
) {

}
