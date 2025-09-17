package com.praveenan.controller;

import com.praveenan.model.Cart;
import com.praveenan.model.CartItem;
import com.praveenan.request.AddCartItemRequest;
import com.praveenan.request.UpdateCartItemRequest;
import com.praveenan.service.CartService;
import com.praveenan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CartController {

  @Autowired
  private CartService cartService;
  @Autowired
  private UserService userService;

  @PutMapping("/cart/add")
  public ResponseEntity<CartItem> addItemToCart(@RequestBody AddCartItemRequest request,
      @RequestHeader("Authorization") String jwt) throws Exception {

    CartItem cartItem = cartService.addItemToCart(request, jwt);
    return new ResponseEntity<>(cartItem, HttpStatus.OK);
  }

  @PutMapping("/cart-item/update")
  public ResponseEntity<CartItem> updateCartItemQuantity(@RequestBody UpdateCartItemRequest request,
      @RequestHeader("Authorization") String jwt) throws Exception {

    CartItem cartItem = cartService.updateCartItemQuantity(request.getCartItemId(), request.getQuantity());
    return new ResponseEntity<>(cartItem, HttpStatus.OK);
  }

  @DeleteMapping("/cart-item/{id}/remove")
  public ResponseEntity<Cart> removeItemFormCart(@PathVariable Long id,
      @RequestHeader("Authorization") String jwt) throws Exception {

    Cart cart = cartService.removeItemFromCart(id,jwt);
    return new ResponseEntity<>(cart, HttpStatus.OK);
  }

  @PutMapping("/cart/clear")
  public ResponseEntity<Cart> clearCart(@RequestHeader("Authorization") String jwt)
      throws Exception {

    Cart cart = cartService.clearCart(jwt);
    return new ResponseEntity<>(cart, HttpStatus.OK);
  }

  @GetMapping("/cart")
  public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt)
      throws Exception {

    Cart cart = cartService.findCartByUserId(jwt);
    return new ResponseEntity<>(cart, HttpStatus.OK);
  }

}
