package com.ariffugur.onlineFoodOrdering.dto;


import com.ariffugur.onlineFoodOrdering.model.Address;
import com.ariffugur.onlineFoodOrdering.model.ContactInformation;

import java.util.List;

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
