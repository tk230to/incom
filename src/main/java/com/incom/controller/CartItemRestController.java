package com.incom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incom.model.CartItem;
import com.incom.model.CartItemRepository;

/**
 * カート商品コントローラクラス。
 */
@RestController
@RequestMapping("/cartitems")
public class CartItemRestController extends BaseRestController<CartItemRepository, CartItem, Long> {

    @Autowired
    public CartItemRestController(CartItemRepository repository) {
        super(repository);
    }
}
