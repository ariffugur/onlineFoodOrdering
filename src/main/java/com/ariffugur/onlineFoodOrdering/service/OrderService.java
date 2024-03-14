package com.ariffugur.onlineFoodOrdering.service;

import com.ariffugur.onlineFoodOrdering.dto.OrderRequest;
import com.ariffugur.onlineFoodOrdering.model.*;
import com.ariffugur.onlineFoodOrdering.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final AddressService addressService;
    private final UserService userService;
    private final RestaurantService restaurantService;
    private final CartService cartService;
    private final OrderItemService orderItemService;

    public OrderService(OrderRepository orderRepository, AddressService addressService, UserService userService, RestaurantService restaurantService, CartService cartService,  OrderItemService orderItemService) {
        this.orderRepository = orderRepository;
        this.addressService = addressService;
        this.userService = userService;
        this.restaurantService = restaurantService;
        this.cartService = cartService;
        this.orderItemService = orderItemService;
    }

    public Order createOrder(OrderRequest request, String jwt) {
        User user = userService.findUserByJwtToken(jwt);
        Address address = request.deliveryAddress();
        addressService.save(address);
        if (!user.getAddresses().contains(address)) {
            userService.addAdressToUser(user, address);
        }
        Restaurant restaurant = restaurantService.findRestaurantById(request.restaurantId());
        Order order = Order.builder()
                .createdAt(new Date())
                .orderStatus("PENDING")
                .customer(user)
                .deliveryAddress(address)
                .restaurant(restaurant)
                .build();
        Cart cart = cartService.findCartById(jwt);
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = OrderItem.builder()
                    .food(cartItem.getFood())
                    .ingredients(cartItem.getIngredients())
                    .totalPrice(cartItem.getTotalPrice())
                    .quantity(cartItem.getQuantity())
                    .build();
            OrderItem savedOrderItem = orderItemService.save(orderItem);
            orderItems.add(savedOrderItem);
        }

        order.setItems(orderItems);
        order.setTotalPrice(cart.getTotal());
        Order savedOrder = orderRepository.save(order);
        restaurant.getOrders().add(savedOrder);
        return order;
    }

    public void cancelOrder(Long orderId) throws Exception {
        Optional<Order> order = findOrderById(orderId);
        orderRepository.deleteById(orderId);


    }

    public Optional<Order> findOrderById(Long orderId) throws Exception {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isEmpty()) {
            throw new Exception("Order not found");
        }
        return order;
    }

    public Order updateOrder(Long orderId, String orderStatus,String jwt) throws Exception {
        Optional<Order> order = findOrderById(orderId);
        if (orderStatus.equals("OUT_FOR_DELIVERY")
                || orderStatus.equals("DELIVERED")
                || orderStatus.equals("COMPLETED")
                || orderStatus.equals("PENDING")) {
            order.get().setOrderStatus(orderStatus);
            return orderRepository.save(order.get());
        } else {
            throw new Exception("Invalid order status");

        }
    }

    public List<Order> getUsersOrder(String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        return orderRepository.findByCustomerId(user.getId());
    }

    public List<Order> getRestaurantsOrder(Long restaurantId, String orderStatus, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Order> orders = orderRepository.findByRestaurantId(restaurantId);
        if (orderStatus != null) {
            orders = orders.stream().filter(order -> order.getOrderStatus().equals(orderStatus)).collect(Collectors.toList());
        }
        if (orders.isEmpty()) {
            throw new Exception("Order not found");
        }
        return orders;
    }


}