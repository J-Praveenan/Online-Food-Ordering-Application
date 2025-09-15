package com.praveenan.controller;

import com.praveenan.model.Food;
import com.praveenan.model.Restaurant;
import com.praveenan.model.User;
import com.praveenan.request.CreateFoodRequest;
import com.praveenan.service.FoodService;
import com.praveenan.service.RestaurantService;
import com.praveenan.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/food")
public class CustomerFoodController {

  @Autowired
  private FoodService foodService;
  @Autowired
  private UserService userService;
  @Autowired
  private RestaurantService restaurantService;

  @GetMapping("/search")
  public ResponseEntity<List<Food>> searchFood(@RequestParam String keyword,
      @RequestHeader("Authorization") String jwt) throws Exception {

    User user = userService.findUserByJwtToken(jwt);
    List<Food> foods = foodService.searchFood(keyword);

    return new ResponseEntity<>(foods, HttpStatus.OK);
  }

  @GetMapping("/restaurant/{restaurantId}")
  public ResponseEntity<List<Food>> getRestaurantFood(@PathVariable Long restaurantId,
      @RequestParam boolean isVegetarian,
      @RequestParam boolean seasonal,
      @RequestParam boolean isNonVeg,
      @RequestParam(required = false) String foodCategory,
      @RequestHeader("Authorization") String jwt) throws Exception {

    User user = userService.findUserByJwtToken(jwt);
    List<Food> foods = foodService.getRestaurantFood(restaurantId,isVegetarian,isNonVeg,seasonal,foodCategory);

    return new ResponseEntity<>(foods, HttpStatus.OK);
  }


}
