package com.ariffugur.onlineFoodOrdering.service;

import com.ariffugur.onlineFoodOrdering.dto.AddCartItemRequest;
import com.ariffugur.onlineFoodOrdering.model.Cart;
import com.ariffugur.onlineFoodOrdering.model.CartItem;
import com.ariffugur.onlineFoodOrdering.model.Food;
import com.ariffugur.onlineFoodOrdering.model.User;
import com.ariffugur.onlineFoodOrdering.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final UserService userService;
    private final FoodService foodService;
    private final CartItemService cartItemService;

    public CartService(CartRepository cartRepository, UserService userService, FoodService foodService, CartItemService cartItemService) {
        this.cartRepository = cartRepository;
        this.userService = userService;
        this.foodService = foodService;
        this.cartItemService = cartItemService;
    }

    public Cart findCartByCustomerId(Long id) {
        return cartRepository.findByCustomerId(id);
    }

    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartRepository.findByCustomerId(user.getId());
        CartItem cartItemOptional = cartItemService.findById(cartItemId);
        if (cartItemOptional == null) {
            throw new Exception("Cart item not found");
        }
        cart.getItems().remove(cartItemOptional);
        return cartRepository.save(cart);
    }

    public Long calculateCartTotal(Cart cart) throws Exception {
        Long total = 0L;
        for (CartItem cartItem : cart.getItems()) {
            total += cartItem.getFood().getPrice() * cartItem.getQuantity();
        }
        return total;
    }

    public Cart findCartById(String jwt) {
        User user = userService.findUserByJwtToken(jwt);
        Optional<Cart> cart = cartRepository.findById(user.getId());
        if (cart.isEmpty()) {
            throw new RuntimeException("Cart not found");
        }

        return cart.get();
    }

    public Cart findCartByUserId(String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        return cartRepository.findByCustomerId(user.getId());
    }

    public Cart clearCart(String jwt) throws Exception {
        Cart cart = findCartByUserId(jwt);
        cart.getItems().clear();
        return cartRepository.save(cart);
    }
}
