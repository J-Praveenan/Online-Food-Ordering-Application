package com.praveenan.service;

import com.praveenan.dto.RestaurantDto;
import com.praveenan.model.Address;
import com.praveenan.model.Restaurant;
import com.praveenan.model.User;
import com.praveenan.repository.AddressRepository;
import com.praveenan.repository.RestaurantRepository;
import com.praveenan.repository.UserRepository;
import com.praveenan.request.CreateRestaurantRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantServiceImpl implements RestaurantService{

  @Autowired
  private RestaurantRepository restaurantRepository;
  @Autowired
  private AddressRepository addressRepository;
  @Autowired
  private UserService userService;
  @Autowired
  private UserRepository userRepository;

  @Override
  public Restaurant createRestaurant(CreateRestaurantRequest request, User user) {

    Address address = addressRepository.save(request.getAddress());

    Restaurant restaurant = new Restaurant();
    restaurant.setAddress(address);
    restaurant.setContactInformation(request.getContactInformation());
    restaurant.setCuisineType(request.getCuisineType());
    restaurant.setDescription(request.getDescription());
    restaurant.setImages(request.getImages());
    restaurant.setName(request.getName());
    restaurant.setOpeningHours(request.getOpeningHours());
    restaurant.setRegistrationDate(LocalDateTime.now());
    restaurant.setOwner(user);

    return restaurantRepository.save(restaurant);
  }

  @Override
  public Restaurant updateRestaurant(Long restaurantId,
      CreateRestaurantRequest updateRestaurantRequest) throws Exception {

    Restaurant restaurant = findRestaurantById(restaurantId);

    if(restaurant.getCuisineType() != null){
      restaurant.setCuisineType(updateRestaurantRequest.getCuisineType());
    }
    if(restaurant.getDescription() != null){
      restaurant.setDescription(updateRestaurantRequest.getDescription());
    }
    if(restaurant.getName() != null){
      restaurant.setName(updateRestaurantRequest.getName());
    }
    return restaurantRepository.save(restaurant);
  }

  @Override
  public void deleteRestaurant(Long restaurantId) throws Exception {

    Restaurant restaurant =  findRestaurantById(restaurantId);
    restaurantRepository.delete(restaurant);

  }

  @Override
  public List<Restaurant> getAllRestaurant() {
    return restaurantRepository.findAll();
  }

  @Override
  public List<Restaurant> searchRestaurant(String keyword) {
    return restaurantRepository.findBySearchQuery(keyword);
  }

  @Override
  public Restaurant findRestaurantById(Long id) throws Exception {

    Optional<Restaurant> restaurant = restaurantRepository.findById(id);

    if(restaurant.isEmpty()){
      throw new Exception("Restaurant not found with id "+id);
    }

    return restaurant.get();
  }

  @Override
  public Restaurant getRestaurantByUserId(Long userId) throws Exception {

    Restaurant restaurant =  restaurantRepository.findByOwnerId(userId);
    if(restaurant == null){
      throw new Exception("Restaurant not found with ownerId "+userId);
    }
    return restaurant;
  }

  @Override
  public RestaurantDto addToFavorites(Long restaurantId, User user) throws Exception {

    Restaurant restaurant = findRestaurantById(restaurantId);

    RestaurantDto dto = new RestaurantDto();
    dto.setDescription(restaurant.getDescription());
    dto.setImages(restaurant.getImages());
    dto.setId(restaurantId);
    dto.setTitle(restaurant.getName());

    boolean isFavorite = false;
    List<RestaurantDto> favorites = user.getFavorites();
    for(RestaurantDto favorite : favorites){
      if(favorite.getId().equals(restaurantId)){
        isFavorite = true;
        break;
      }
    }

    if(isFavorite){
      favorites.removeIf(favorite->favorite.getId().equals(restaurantId));
    }else{
      favorites.add(dto);
    }
    userRepository.save(user);

    return dto;
  }

  @Override
  public Restaurant updateRestaurantStatus(Long id) throws Exception {

    Restaurant restaurant = findRestaurantById(id);
    restaurant.setOpen(!restaurant.isOpen());

    return restaurantRepository.save(restaurant);
  }
}
