package com.praveenan.service;

import com.praveenan.model.IngredientCategory;
import com.praveenan.model.IngredientsItem;
import com.praveenan.model.Restaurant;
import com.praveenan.repository.IngredientCategoryRepository;
import com.praveenan.repository.IngredientItemRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngredientsServiceImpl implements IngredientsService {

  @Autowired
  private IngredientCategoryRepository ingredientCategoryRepository;
  @Autowired
  private IngredientItemRepository ingredientItemRepository;
  @Autowired
  private RestaurantService restaurantService;

  @Override
  public IngredientCategory createIngredientCategory(String name, Long restaurantId)
      throws Exception {

    Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);

    IngredientCategory category = new IngredientCategory();
    category.setRestaurant(restaurant);
    category.setName(name);

    return ingredientCategoryRepository.save(category);
  }

  @Override
  public IngredientCategory findIngredientCategoryById(Long id) throws Exception {
    Optional<IngredientCategory> category = ingredientCategoryRepository.findById(id);

    if(category.isEmpty()){
      throw new Exception("Ingredient category not found.");
    }
    return category.get();
  }

  @Override
  public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception {
    restaurantService.findRestaurantById(id);
    return ingredientCategoryRepository.findByRestaurantId(id);
  }

  @Override
  public IngredientsItem createIngredientItem(Long restaurantId, String ingredientName,
      Long categoryId) throws Exception {
    Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
    IngredientCategory category = findIngredientCategoryById(categoryId);

    IngredientsItem item = new IngredientsItem();
    item.setName(ingredientName);
    item.setRestaurant(restaurant);
    item.setCategory(category);

    IngredientsItem saveIngredientItem = ingredientItemRepository.save(item);
    category.getIngredientsItems().add(saveIngredientItem);

    return saveIngredientItem;
  }

  @Override
  public List<IngredientsItem> findRestaurantIngredients(Long restaurantId) throws Exception {
    return ingredientItemRepository.findByRestaurantId(restaurantId);
  }

  @Override
  public IngredientsItem updateStock(Long id) throws Exception {

    Optional<IngredientsItem>item = ingredientItemRepository.findById(id);

    if(item.isEmpty()){
      throw new Exception("Ingredient not found.");
    }

    item.get().setStoke( !item.get().isStoke());

    return ingredientItemRepository.save(item.get());
  }
}
