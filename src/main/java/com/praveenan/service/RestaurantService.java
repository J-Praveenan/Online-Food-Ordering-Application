package com.praveenan.service;

import com.praveenan.dto.RestaurantDto;
import com.praveenan.model.Restaurant;
import com.praveenan.model.User;
import com.praveenan.request.CreateRestaurantRequest;
import java.util.List;

public interface RestaurantService {

  public Restaurant createRestaurant(CreateRestaurantRequest request, User user);

  public Restaurant updateRestaurant(Long restaurantId,
      CreateRestaurantRequest updateRestaurantRequest) throws Exception;

  public void deleteRestaurant(Long restaurantId) throws Exception;

  public List<Restaurant> getAllRestaurant();

  public List<Restaurant> searchRestaurant(String keyword);

  public Restaurant findRestaurantById(Long id) throws Exception;

  public Restaurant getRestaurantByUserId(Long userId) throws Exception;

  public RestaurantDto addToFavorites(Long restaurantId, User user) throws Exception;

  public Restaurant updateRestaurantStatus(Long id) throws Exception;
}
