package com.praveenan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.praveenan.dto.RestaurantDto;
import com.praveenan.enums.USER_ROLE;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String fullName;
  private String email;

  @JsonProperty(access = Access.WRITE_ONLY)
  private String password;
  private USER_ROLE role = USER_ROLE.ROLE_CUSTOMER;

  @JsonIgnore
  @OneToMany(cascade = CascadeType.ALL,mappedBy = "customer", orphanRemoval = true)
  private List<Order> orders = new ArrayList<>();

  @ElementCollection
  private List<RestaurantDto> favorites = new ArrayList<>();

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Address> addresses = new ArrayList<>();

}
