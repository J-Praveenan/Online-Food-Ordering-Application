package com.praveenan.service;

import com.praveenan.model.Cart;
import com.praveenan.model.CartItem;
import com.praveenan.model.Food;
import com.praveenan.model.User;
import com.praveenan.repository.CartItemRepository;
import com.praveenan.repository.CartRepository;
import com.praveenan.request.AddCartItemRequest;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService{

  @Autowired
  private UserService userService;
  @Autowired
  private CartRepository cartRepository;
  @Autowired
  private CartItemRepository cartItemRepository;
  @Autowired
  private FoodService foodService;

  @Override
  public CartItem addItemToCart(AddCartItemRequest request, String jwt) throws Exception {
    User user = userService.findUserByJwtToken(jwt);
    Food food = foodService.findFoodById(request.getFoodId());

    Cart cart = cartRepository.findByCustomerId(user.getId());

    for(CartItem cartItem : cart.getItems()){
      if(cartItem.getFood().equals(food)){
        int newQuantity = cartItem.getQuantity() + 1;
        return updateCartItemQuantity(cartItem.getId(), newQuantity);
      }
    }

    CartItem newCartItem = new CartItem();
    newCartItem.setFood(food);
    newCartItem.setCart(cart);
    newCartItem.setQuantity(request.getQuantity());
    newCartItem.setIngredients(request.getIngredients());
    newCartItem.setTotalPrice(request.getQuantity() * food.getPrice());

    CartItem savedCartItem = cartItemRepository.save(newCartItem);
    cart.getItems().add(savedCartItem);

    return savedCartItem;
  }

  @Override
  public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {

    Optional<CartItem> optionalCartItem = cartItemRepository.findById(cartItemId);
    if(optionalCartItem.isEmpty()){
      throw new Exception("Cart item not found.");
    }

    CartItem item = optionalCartItem.get();
    item.setQuantity(quantity);
    item.setTotalPrice(item.getFood().getPrice() * quantity);

    return cartItemRepository.save(item);
  }

  @Override
  public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {
    User user = userService.findUserByJwtToken(jwt);
    Cart cart = cartRepository.findByCustomerId(user.getId());

    Optional<CartItem> optionalCartItem = cartItemRepository.findById(cartItemId);
    if(optionalCartItem.isEmpty()){
      throw new Exception("Cart item not found.");
    }

    CartItem item = optionalCartItem.get();
    cart.getItems().remove(item);

    return cartRepository.save(cart);
  }

  @Override
  public Long calculateCartTotals(Cart cart) throws Exception {
    Long totalPrice = 0L;

    for(CartItem cartItem: cart.getItems()){
      totalPrice += cartItem.getFood().getPrice() * cartItem.getQuantity();
    }

    return totalPrice;
  }

  @Override
  public Cart findCartById(Long id) throws Exception {
    Optional<Cart>optionalCart = cartRepository.findById(id);

    if(optionalCart.isEmpty()){
      throw new Exception("Cart not found with id "+ id);
    }
    return optionalCart.get();
  }

  @Override
  public Cart findCartByUserId(String  jwt) throws Exception {
    User user = userService.findUserByJwtToken(jwt);
    return cartRepository.findByCustomerId(user.getId());
  }

  @Override
  public Cart clearCart(String jwt) throws Exception {
    User user = userService.findUserByJwtToken(jwt);
    Cart cart = cartRepository.findByCustomerId(user.getId());
    cart.getItems().clear();

    return cartRepository.save(cart);
  }

}
