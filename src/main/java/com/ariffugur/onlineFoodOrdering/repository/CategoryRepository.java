package com.ariffugur.onlineFoodOrdering.repository;

import com.ariffugur.onlineFoodOrdering.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    public List<Category> findByRestaurantId(Long restaurantId);

}
