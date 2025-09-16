package com.praveenan.service;

import com.praveenan.model.Category;
import java.util.List;

public interface CategoryService {

  public Category createCategory(String name, Long userId) throws Exception;

  public List<Category> findCategoryByRestaurantId(Long userId)throws Exception;

  public Category findCategoryById(Long id)throws Exception;
}
