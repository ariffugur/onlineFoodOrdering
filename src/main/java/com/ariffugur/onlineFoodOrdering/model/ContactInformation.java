package com.ariffugur.onlineFoodOrdering.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactInformation {
    private String email;
    private String mobile;
    private String twitter;
    private String instagram;
}
