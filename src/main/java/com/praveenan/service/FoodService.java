package com.praveenan.service;

import com.praveenan.model.Category;
import com.praveenan.model.Food;
import com.praveenan.model.Restaurant;
import com.praveenan.request.CreateFoodRequest;
import java.util.List;

public interface FoodService {

  public Food createFood(CreateFoodRequest request, Category category, Restaurant restaurant);

  public void deleteFood(Long foodId) throws Exception;

  public List<Food> getRestaurantFood(Long restaurantId, boolean isVegetarian, boolean isNonVeg, boolean isSeasonal, String foodCategory);

  public List<Food>searchFood(String keyword);

  public Food findFoodById(Long foodId) throws Exception;

  public Food updateAvailability(Long foodId) throws Exception;


}
