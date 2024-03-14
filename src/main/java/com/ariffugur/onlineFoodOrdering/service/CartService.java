package com.ariffugur.onlineFoodOrdering.service;

import com.ariffugur.onlineFoodOrdering.dto.AddCartItemRequest;
import com.ariffugur.onlineFoodOrdering.model.Cart;
import com.ariffugur.onlineFoodOrdering.model.CartItem;
import com.ariffugur.onlineFoodOrdering.model.Food;
import com.ariffugur.onlineFoodOrdering.model.User;
import com.ariffugur.onlineFoodOrdering.repository.CartItemRepository;
import com.ariffugur.onlineFoodOrdering.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final FoodService foodService;

    private final UserService userService;

    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository, FoodService foodService, UserService userService) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.foodService = foodService;
        this.userService = userService;
    }

    public Cart findCartByCustomerId(Long id) {
        return cartRepository.findByCustomerId(id);
    }

    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartRepository.findByCustomerId(user.getId());
        Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);

        cartItemOptional.orElseThrow(() -> new Exception("Cart item not found"));

        cartItemOptional.ifPresent(cartItem -> cart.getItems().remove(cartItem));

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
        Cart cart = cartRepository.findByCustomerId(user.getId());
        cart.setTotal(calculateCartTotal(cart));
        return cart;
    }

    public Cart clearCart(String jwt) throws Exception {
        Cart cart = findCartByUserId(jwt);
        cart.getItems().clear();
        return cartRepository.save(cart);
    }



    public CartItem addItemToCart(String jwt, AddCartItemRequest request) {
        User user = userService.findUserByJwtToken(jwt);
        Food food = foodService.findFoodById(jwt, request.getFoodId());
        Cart cart = cartRepository.findByCustomerId(user.getId());
        for (CartItem cartItem : cart.getItems()) {
            if (cartItem.getFood().equals(food)) {
                int newQuantity = cartItem.getQuantity() + request.getQuantity();
                return updateCartItemQuantity(cartItem.getId(), newQuantity);
            }
        }

        CartItem newCartItem = CartItem.builder()
                .food(food)
                .quantity(request.getQuantity())
                .cart(cart)
                .ingredients(request.getIngredients())
                .totalPrice(request.getQuantity() * food.getPrice())
                .build();
        CartItem savedCartItem = cartItemRepository.save(newCartItem);
        cart.getItems().add(savedCartItem);
        return savedCartItem;
    }


    public CartItem updateCartItemQuantity(Long id, int quantity) {
        Optional<CartItem> cartItemOptional = cartItemRepository.findById(id);

        if (cartItemOptional.isEmpty()) {
            throw new RuntimeException("Cart item not found");
        }
        CartItem cartItem = cartItemOptional.get();
        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice(cartItem.getFood().getPrice() * quantity);
        return cartItemRepository.save(cartItem);
    }


}
