package com.praveenan.controller;

import com.praveenan.dto.RestaurantDto;
import com.praveenan.model.Restaurant;
import com.praveenan.model.User;
import com.praveenan.request.CreateRestaurantRequest;
import com.praveenan.service.RestaurantService;
import com.praveenan.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/restaurants")
public class CustomerRestaurantController {

  @Autowired
  private RestaurantService restaurantService;
  @Autowired
  private UserService userService;


  @GetMapping("/search")
  public ResponseEntity<List<Restaurant>> searchRestaurant(
      @RequestHeader("Authorization") String jwt,
      @RequestParam String keyword) throws Exception {

    User user = userService.findUserByJwtToken(jwt);

    List<Restaurant> restaurants = restaurantService.searchRestaurant(keyword);
    return new ResponseEntity<>(restaurants, HttpStatus.OK);
  }


  @GetMapping()
  public ResponseEntity<List<Restaurant>> getAllRestaurant(
      @RequestHeader("Authorization") String jwt) throws Exception {

    User user = userService.findUserByJwtToken(jwt);

    List<Restaurant> restaurants = restaurantService.getAllRestaurant();
    return new ResponseEntity<>(restaurants, HttpStatus.OK);
  }


  @GetMapping("/{id}")
  public ResponseEntity<Restaurant> findRestaurantById(
      @RequestHeader("Authorization") String jwt,
      @PathVariable Long id) throws Exception {

    User user = userService.findUserByJwtToken(jwt);

    Restaurant restaurant = restaurantService.findRestaurantById(id);
    return new ResponseEntity<>(restaurant, HttpStatus.OK);
  }


  @PutMapping("/{id}/add-favorites")
  public ResponseEntity<RestaurantDto> addToFavorites(
      @RequestHeader("Authorization") String jwt,
      @PathVariable Long id) throws Exception {

    User user = userService.findUserByJwtToken(jwt);

    RestaurantDto restaurant = restaurantService.addToFavorites(id,user);
    return new ResponseEntity<>(restaurant, HttpStatus.OK);
  }

  
}
