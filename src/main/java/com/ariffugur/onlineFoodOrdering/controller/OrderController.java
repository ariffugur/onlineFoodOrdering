package com.ariffugur.onlineFoodOrdering.controller;

import com.ariffugur.onlineFoodOrdering.dto.OrderRequest;
import com.ariffugur.onlineFoodOrdering.model.Order;
import com.ariffugur.onlineFoodOrdering.service.OrderItemService;
import com.ariffugur.onlineFoodOrdering.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final OrderItemService orderItemService;

    public OrderController(OrderService orderService, OrderItemService orderItemService) {
        this.orderService = orderService;
        this.orderItemService = orderItemService;
    }

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestHeader("Authorization") String jwt,@RequestBody OrderRequest request) {
        return ResponseEntity.ok(orderService.createOrder(request, jwt));
    }

    @GetMapping("/allorder")
    public ResponseEntity<List<Order>> getAllOrder(@RequestHeader("Authorization") String jwt) throws Exception {
        return ResponseEntity.ok(orderService.getUsersOrder(jwt));

    }
}
