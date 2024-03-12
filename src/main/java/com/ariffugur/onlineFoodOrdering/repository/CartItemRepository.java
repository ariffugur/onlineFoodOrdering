package com.ariffugur.onlineFoodOrdering.repository;

import com.ariffugur.onlineFoodOrdering.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    public Optional<CartItem> findByCustomerId(Long userId);
}
