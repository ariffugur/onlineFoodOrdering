package com.ariffugur.onlineFoodOrdering.dto;

import lombok.Builder;

@Builder
public record UpdateCartItemRequest(
        Long cartItemId,
        int quantity
) {
}
