package com.praveenan.request;

import com.praveenan.model.Address;
import lombok.Data;

@Data
public class OrderRequest {
  private Long restaurantId;
  private Address deliveryAddress;
}
