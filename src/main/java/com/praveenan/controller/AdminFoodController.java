package com.praveenan.controller;

import com.praveenan.model.Food;
import com.praveenan.model.Restaurant;
import com.praveenan.model.User;
import com.praveenan.request.CreateFoodRequest;
import com.praveenan.response.MessageResponse;
import com.praveenan.service.FoodService;
import com.praveenan.service.RestaurantService;
import com.praveenan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {

  @Autowired
  private FoodService foodService;
  @Autowired
  private UserService userService;
  @Autowired
  private RestaurantService restaurantService;

  @PostMapping
  public ResponseEntity<Food>createFood(@RequestBody CreateFoodRequest request,
      @RequestHeader("Authorization") String jwt) throws Exception {

    User user = userService.findUserByJwtToken(jwt);
    Restaurant restaurant = restaurantService.findRestaurantById(request.getRestaurantId());
    Food food = foodService.createFood(request,request.getCategory(),restaurant);

    return new ResponseEntity<>(food, HttpStatus.CREATED);
  }


  @DeleteMapping("/{id}")
  public ResponseEntity<MessageResponse>deleteFood(@PathVariable Long id,
      @RequestHeader("Authorization") String jwt) throws Exception {

    User user = userService.findUserByJwtToken(jwt);
    foodService.deleteFood(id);

    MessageResponse response = new MessageResponse();
    response.setMessage("Food deleted successfully...");

    return new ResponseEntity<>(response, HttpStatus.OK);
  }


  @PutMapping("/{id}")
  public ResponseEntity<Food>updateFoodAvailability(@PathVariable Long id,
      @RequestHeader("Authorization") String jwt) throws Exception {

    User user = userService.findUserByJwtToken(jwt);
    Food food = foodService.updateAvailability(id);

    return new ResponseEntity<>(food, HttpStatus.OK);
  }


}
