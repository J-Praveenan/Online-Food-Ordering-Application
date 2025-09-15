package com.praveenan.request;

import com.praveenan.model.Category;
import com.praveenan.model.IngredientsItem;
import java.util.List;
import lombok.Data;

@Data
public class CreateFoodRequest {

  private String name;
  private String description;
  private Long price;
  private Category category;
  private List<String>images;
  private Long restaurantId;
  private boolean vegetarian;
  private boolean seasonal;
  private List<IngredientsItem> ingredients;

}
