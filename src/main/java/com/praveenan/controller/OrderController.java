package com.praveenan.controller;

import com.praveenan.model.Order;
import com.praveenan.model.User;
import com.praveenan.request.OrderRequest;
import com.praveenan.service.OrderService;
import com.praveenan.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OrderController {

  @Autowired
  private OrderService orderService;
  @Autowired
  private UserService userService;

  @PostMapping("/order")
  public ResponseEntity<Order> createOrder(@RequestBody OrderRequest request,
      @RequestHeader("Authorization") String jwt) throws Exception {

    User user = userService.findUserByJwtToken(jwt);
    Order order = orderService.createOrder(request, user);

    return new ResponseEntity<>(order, HttpStatus.CREATED);
  }


  @GetMapping("/order/user")
  public ResponseEntity<List<Order>> getOrderHistory(
      @RequestHeader("Authorization") String jwt) throws Exception {

    User user = userService.findUserByJwtToken(jwt);
    List<Order> orders = orderService.getUsersOrder(user.getId());

    return new ResponseEntity<>(orders, HttpStatus.OK);
  }


}
