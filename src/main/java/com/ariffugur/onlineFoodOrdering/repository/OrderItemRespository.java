package com.ariffugur.onlineFoodOrdering.repository;

import com.ariffugur.onlineFoodOrdering.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRespository extends JpaRepository<OrderItem, Long> {
}
