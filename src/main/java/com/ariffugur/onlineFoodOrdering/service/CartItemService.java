package com.ariffugur.onlineFoodOrdering.service;

import com.ariffugur.onlineFoodOrdering.dto.AddCartItemRequest;
import com.ariffugur.onlineFoodOrdering.model.Cart;
import com.ariffugur.onlineFoodOrdering.model.CartItem;
import com.ariffugur.onlineFoodOrdering.model.Food;
import com.ariffugur.onlineFoodOrdering.model.User;
import com.ariffugur.onlineFoodOrdering.repository.CartItemRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final UserService userService;
    private final FoodService foodService;
    private final CartService cartService;

    public CartItemService(CartItemRepository cartItemRepository, UserService userService, FoodService foodService, CartService cartService) {
        this.cartItemRepository = cartItemRepository;
        this.userService = userService;
        this.foodService = foodService;
        this.cartService = cartService;
    }

    public CartItem addItemToCart(String jwt, AddCartItemRequest request) {
        User user = userService.findUserByJwtToken(jwt);
        Food food = foodService.findFoodById(jwt, request.foodId());
        Cart cart = cartService.findCartByCustomerId(user.getId());
        for (CartItem cartItem : cart.getItems()) {
            if (cartItem.getFood().equals(food)) {
                int newQuantity = cartItem.getQuantity() + request.quantity();
                return updateCartItemQuantity(cartItem.getId(), newQuantity);
            }
        }

        CartItem newCartItem = CartItem.builder()
                .quantity(request.quantity())
                .food(food)
                .cart(cart)
                .ingredients(request.ingredients())
                .totalPrice(request.quantity() * food.getPrice())
                .build();
        CartItem savedCartItem = cartItemRepository.save(newCartItem);
        cart.getItems().add(savedCartItem);
        return savedCartItem;
    }

    public CartItem updateCartItemQuantity(Long id, int quantity) {
        Optional<CartItem> cartItemOptional = cartItemRepository.findByCustomerId(id);

        if (cartItemOptional.isEmpty()) {
            throw new RuntimeException("Cart item not found");
        }
        CartItem cartItem = cartItemOptional.get();
        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice(cartItem.getFood().getPrice() * quantity);
        return cartItemRepository.save(cartItem);
    }

    public CartItem findById(Long id) throws Exception {
        Optional<CartItem> cartItem = cartItemRepository.findById(id);

        if (cartItem.isEmpty()) {
            throw new RuntimeException("Cart item not found");
        }
        return cartItem.get();
    }


}