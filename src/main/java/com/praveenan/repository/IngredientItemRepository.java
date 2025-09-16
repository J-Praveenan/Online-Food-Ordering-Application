package com.praveenan.repository;

import com.praveenan.model.IngredientsItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientItemRepository extends JpaRepository<IngredientsItem, Long> {

  public List<IngredientsItem> findByRestaurantId(Long id);
}
