package com.ariffugur.onlineFoodOrdering.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddCartItemRequest {
    private Long foodId;
    private int quantity;
    private List<String> ingredients;

}
