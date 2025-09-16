package com.praveenan.service;

import com.praveenan.model.Category;
import com.praveenan.model.Restaurant;
import com.praveenan.repository.CategoryRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService{

  @Autowired
  private CategoryRepository categoryRepository;
  @Autowired
  private RestaurantService restaurantService;

  @Override
  public Category createCategory(String name, Long userId) throws Exception{
    Restaurant restaurant = restaurantService.getRestaurantByUserId(userId);
    Category category = new Category();
    category.setName(name);
    category.setRestaurant(restaurant);
    return categoryRepository.save(category);
  }

  @Override
  public List<Category> findCategoryByRestaurantId(Long userId) throws Exception {
    Restaurant restaurant = restaurantService.getRestaurantByUserId(userId);
    return categoryRepository.findByRestaurantId(restaurant.getId());
  }

  @Override
  public Category findCategoryById(Long id) throws Exception {
    Optional<Category> category = categoryRepository.findById(id);

    if(category.isEmpty()){
      throw new Exception("Category not found");
    }
    return category.get();
  }
}
