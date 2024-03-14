package com.ariffugur.onlineFoodOrdering.service;

import com.ariffugur.onlineFoodOrdering.model.OrderItem;
import com.ariffugur.onlineFoodOrdering.repository.OrderItemRespository;
import org.springframework.stereotype.Service;

@Service
public class OrderItemService {
    private final OrderItemRespository orderItemRespository;

    public OrderItemService(OrderItemRespository orderItemRespository) {
        this.orderItemRespository = orderItemRespository;
    }

    public OrderItem save(OrderItem orderItem) {
        return orderItemRespository.save(orderItem);
    }
}
