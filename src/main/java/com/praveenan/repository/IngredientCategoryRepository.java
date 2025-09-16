package com.praveenan.repository;

import com.praveenan.model.IngredientCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientCategoryRepository extends JpaRepository<IngredientCategory, Long> {

  public List<IngredientCategory> findByRestaurantId(Long restaurantId)throws Exception;
}
