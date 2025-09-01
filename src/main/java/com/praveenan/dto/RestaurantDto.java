package com.praveenan.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.List;
import lombok.Data;

@Data
@Embeddable
public class RestaurantDto {

  private Long id;
  private String title;

  @Column(length = 1000)
  private List<String> images;

  private String description;

}
