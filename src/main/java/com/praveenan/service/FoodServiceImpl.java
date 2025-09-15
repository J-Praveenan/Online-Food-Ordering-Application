package com.praveenan.service;

import com.praveenan.model.Category;
import com.praveenan.model.Food;
import com.praveenan.model.Restaurant;
import com.praveenan.repository.FoodRepository;
import com.praveenan.request.CreateFoodRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoodServiceImpl implements FoodService{

  @Autowired
  private FoodRepository foodRepository;

  @Override
  public Food createFood(CreateFoodRequest request, Category category, Restaurant restaurant) {
    Food food = new Food();

    food.setFoodCategory(category);
    food.setImages(request.getImages());
    food.setDescription(request.getDescription());
    food.setName(request.getName());
    food.setPrice(request.getPrice());
    food.setRestaurant(restaurant);
    food.setIngredients(request.getIngredients());
    food.setSeasonal(request.isSeasonal());
    food.setVegetarian(request.isVegetarian());

    Food savedFood = foodRepository.save(food);

    restaurant.getFoods().add(savedFood);

    return savedFood;
  }

  @Override
  public void deleteFood(Long foodId) throws Exception {

    Food food = findFoodById(foodId);
    food.setRestaurant(null);
    foodRepository.save(food);
  }

  @Override
  public List<Food> getRestaurantFood(Long restaurantId, boolean isVegetarian, boolean isNonVeg,
      boolean isSeasonal, String foodCategory) {

    List<Food>foods = foodRepository.findByRestaurantId(restaurantId);

    if(isVegetarian){
      foods = filterByVegetarian(foods, isVegetarian);
    }
    if(isNonVeg){
      foods = filterByNonVeg(foods, isNonVeg);
    }
    if(isSeasonal){
      foods = filterByISeasonal(foods, isSeasonal);
    }
    if(foodCategory!=null && !foodCategory.equals("")){
      foods = filterByCategory(foods, foodCategory);
    }

    return foods;
  }

  private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
    return foods.stream().filter(food -> {
      if(food.getFoodCategory()!=null){
        return food.getFoodCategory().getName().equals(foodCategory);
      }
      return false;
    }).collect(Collectors.toList());
  }

  private List<Food> filterByISeasonal(List<Food> foods, boolean isSeasonal) {
    return foods.stream().filter(food -> food.isSeasonal()==isSeasonal).collect(
        Collectors.toList());
  }

  private List<Food> filterByNonVeg(List<Food> foods, boolean isNonVeg) {
    return foods.stream().filter(food -> !food.isVegetarian()).collect(
        Collectors.toList());
  }

  private List<Food> filterByVegetarian(List<Food> foods, boolean isVegetarian) {
    return foods.stream().filter(food -> food.isVegetarian()==isVegetarian).collect(
        Collectors.toList());
  }

  @Override
  public List<Food> searchFood(String keyword) {
    return foodRepository.searchFood(keyword);
  }

  @Override
  public Food findFoodById(Long foodId) throws Exception {
    Optional<Food>food = foodRepository.findById(foodId);
    if(food.isEmpty()){
      throw new Exception("Food not found with id "+foodId);
    }
    return food.get();
  }

  @Override
  public Food updateAvailability(Long foodId) throws Exception {
    Food food = findFoodById(foodId);
    food.setAvailable(!food.isAvailable());

    return foodRepository.save(food);
  }
}
