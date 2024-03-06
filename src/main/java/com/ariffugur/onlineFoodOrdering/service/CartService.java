package com.ariffugur.onlineFoodOrdering.service;

import com.ariffugur.onlineFoodOrdering.model.Cart;
import com.ariffugur.onlineFoodOrdering.repository.CartRepository;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }
}
