package com.ariffugur.onlineFoodOrdering.controller;

import com.ariffugur.onlineFoodOrdering.model.Order;
import com.ariffugur.onlineFoodOrdering.service.OrderItemService;
import com.ariffugur.onlineFoodOrdering.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/order")
public class AdminOrderController {
    private final OrderService orderService;
    private final OrderItemService orderItemService;

    public AdminOrderController(OrderService orderService, OrderItemService orderItemService) {
        this.orderService = orderService;
        this.orderItemService = orderItemService;
    }

    @GetMapping("/allOrder/{id}")
    public ResponseEntity<List<Order>> getAllOrder(@RequestHeader("Authorization") Long id,
                                                   @RequestParam(required = false) String status,
                                                   @PathVariable String jwt) throws Exception {
        return ResponseEntity.ok(orderService.getRestaurantsOrder(id, status, jwt));
    }

    @GetMapping("/status/{id}/{status}")
    public ResponseEntity<Order> updateOrderStatus(@RequestHeader("Authorization") Long id,
                                                   @RequestParam String status,
                                                   @PathVariable String jwt) throws Exception {
        return ResponseEntity.ok(orderService.updateOrder(id, status, jwt));
    }
}
