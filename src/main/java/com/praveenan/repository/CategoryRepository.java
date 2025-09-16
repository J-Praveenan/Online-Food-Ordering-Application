package com.praveenan.repository;

import com.praveenan.model.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

  public List<Category> findByRestaurantId(Long id);
}
