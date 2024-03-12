package com.ariffugur.onlineFoodOrdering.controller;

import com.ariffugur.onlineFoodOrdering.dto.AddCartItemRequest;
import com.ariffugur.onlineFoodOrdering.dto.UpdateCartItemRequest;
import com.ariffugur.onlineFoodOrdering.model.Cart;
import com.ariffugur.onlineFoodOrdering.model.CartItem;
import com.ariffugur.onlineFoodOrdering.service.CartItemService;
import com.ariffugur.onlineFoodOrdering.service.CartService;
import com.ariffugur.onlineFoodOrdering.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final CartItemService cartItemService;
    private final UserService userService;

    public CartController(CartService cartService, CartItemService cartItemService, UserService userService) {
        this.cartService = cartService;
        this.cartItemService = cartItemService;
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResponseEntity<CartItem> addItemToCart(@RequestHeader("Authorization") String token, AddCartItemRequest request) {
        return new ResponseEntity<>(cartItemService.addItemToCart(token, request), HttpStatus.CREATED);
    }

    @PutMapping("cartitem/update")
    public ResponseEntity<CartItem> updateCartItemQuantity(@RequestHeader("Authorization") String token, @RequestBody UpdateCartItemRequest request) {
        return new ResponseEntity<>(cartItemService.updateCartItemQuantity(request.cartItemId(), request.quantity()), HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Cart> deleteCartItem(@RequestHeader("Authorization") String token, @RequestParam Long id) throws Exception {
        return new ResponseEntity<>(cartService.removeItemFromCart(id, token), HttpStatus.OK);
    }

    @PutMapping("clear")
    public ResponseEntity<Cart> updateCartItemQuantity(@RequestHeader("Authorization") String token) throws Exception {
        return new ResponseEntity<>(cartService.clearCart(token), HttpStatus.OK);
    }

    @GetMapping("clear")
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String token) throws Exception {
        return new ResponseEntity<>(cartService.findCartById(token), HttpStatus.OK);
    }
}
