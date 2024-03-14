package com.ariffugur.onlineFoodOrdering.dto;

import com.ariffugur.onlineFoodOrdering.model.Address;
import lombok.Builder;

@Builder
public record OrderRequest(
        Long restaurantId,
        Address deliveryAddress
) {
}
