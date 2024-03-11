package com.ariffugur.onlineFoodOrdering.dto;


import com.ariffugur.onlineFoodOrdering.model.Address;
import com.ariffugur.onlineFoodOrdering.model.ContactInformation;
import lombok.Builder;

import java.util.List;
@Builder
public record CreateRestaurantRequest(
        Long id,
        String name,
        String description,
        String cuisineType,
        Address address,
        ContactInformation contactInformation,
        String openingHours,
        List<String> images
) {
}
