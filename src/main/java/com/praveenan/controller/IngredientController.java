package com.praveenan.controller;

import com.praveenan.model.IngredientCategory;
import com.praveenan.model.IngredientsItem;
import com.praveenan.request.IngredientCategoryRequest;
import com.praveenan.request.IngredientItemRequest;
import com.praveenan.service.IngredientsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/ingredient")
public class IngredientController {

  @Autowired
  private IngredientsService ingredientsService;

  @PostMapping("/category")
  public ResponseEntity<IngredientCategory> createIngredientCategory(
      @RequestBody IngredientCategoryRequest request) throws Exception {
    IngredientCategory category = ingredientsService.createIngredientCategory(request.getName(),
        request.getRestaurantId());
    return new ResponseEntity<>(category, HttpStatus.CREATED);
  }

  @PostMapping("/item")
  public ResponseEntity<IngredientsItem> createIngredientItem(
      @RequestBody IngredientItemRequest request) throws Exception {
    IngredientsItem item = ingredientsService.createIngredientItem(request.getRestaurantId(),
        request.getName(), request.getCategoryId());
    return new ResponseEntity<>(item, HttpStatus.CREATED);
  }

  @PutMapping("/{id}/stoke")
  public ResponseEntity<IngredientsItem> updateIngredientStoke(@PathVariable Long id)
      throws Exception {
    IngredientsItem item = ingredientsService.updateStock(id);
    return new ResponseEntity<>(item, HttpStatus.OK);
  }

  @GetMapping("/item/restaurant/{id}")
  public ResponseEntity<List<IngredientsItem>> getRestaurantIngredientItems(@PathVariable Long id)
      throws Exception {
    List<IngredientsItem> items = ingredientsService.findRestaurantIngredients(id);
    return new ResponseEntity<>(items, HttpStatus.OK);
  }

  @GetMapping("/category/restaurant/{id}")
  public ResponseEntity<List<IngredientCategory>> getRestaurantIngredientCategory(
      @PathVariable Long id) throws Exception {
    List<IngredientCategory> categories = ingredientsService.findIngredientCategoryByRestaurantId(
        id);
    return new ResponseEntity<>(categories, HttpStatus.OK);
  }

}
