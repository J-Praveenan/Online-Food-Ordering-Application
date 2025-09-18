package com.praveenan.repository;

import com.praveenan.model.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

  public List<Order> findByCustomerId(Long userId);
  public List<Order> findByRestaurantId(Long restaurantId);

}
