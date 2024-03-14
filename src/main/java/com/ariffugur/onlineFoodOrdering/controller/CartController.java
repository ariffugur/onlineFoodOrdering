package com.ariffugur.onlineFoodOrdering.controller;

import com.ariffugur.onlineFoodOrdering.dto.AddCartItemRequest;
import com.ariffugur.onlineFoodOrdering.dto.UpdateCartItemRequest;
import com.ariffugur.onlineFoodOrdering.model.Cart;
import com.ariffugur.onlineFoodOrdering.model.CartItem;
import com.ariffugur.onlineFoodOrdering.service.CartService;
import com.ariffugur.onlineFoodOrdering.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<CartItem> addItemToCart(@RequestHeader("Authorization") String token, @RequestBody AddCartItemRequest request) {
        CartItem cartItem = cartService.addItemToCart(token, request);
        return new ResponseEntity<>(cartItem, HttpStatus.CREATED);
    }

    @PutMapping("/cartitem/update")
    public ResponseEntity<CartItem> updateCartItemQuantity(@RequestHeader("Authorization") String token, @RequestBody UpdateCartItemRequest request) {
        return new ResponseEntity<>(cartService.updateCartItemQuantity(request.cartItemId(), request.quantity()), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Cart> deleteCartItem(@RequestHeader("Authorization") String token, @PathVariable Long id) throws Exception {
        return new ResponseEntity<>(cartService.removeItemFromCart(id, token), HttpStatus.OK);
    }

    @PutMapping("/clear")
    public ResponseEntity<Cart> updateCartItemQuantity(@RequestHeader("Authorization") String token) throws Exception {
        return new ResponseEntity<>(cartService.clearCart(token), HttpStatus.OK);
    }

    @GetMapping("/findCartByUser")
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String token) throws Exception {
        return new ResponseEntity<>(cartService.findCartById(token), HttpStatus.OK);
    }
}
