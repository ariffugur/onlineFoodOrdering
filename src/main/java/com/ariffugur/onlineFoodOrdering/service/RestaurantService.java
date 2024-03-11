package com.ariffugur.onlineFoodOrdering.service;

import com.ariffugur.onlineFoodOrdering.dto.CreateRestaurantRequest;
import com.ariffugur.onlineFoodOrdering.dto.RestaurantDto;
import com.ariffugur.onlineFoodOrdering.model.Address;
import com.ariffugur.onlineFoodOrdering.model.ContactInformation;
import com.ariffugur.onlineFoodOrdering.model.Restaurant;
import com.ariffugur.onlineFoodOrdering.model.User;
import com.ariffugur.onlineFoodOrdering.repository.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final UserService userService;
    private final JwtService jwtService;
    private final AddressService addressService;

    public RestaurantService(RestaurantRepository restaurantRepository, UserService userService, JwtService jwtService, AddressService addressService) {
        this.restaurantRepository = restaurantRepository;
        this.userService = userService;
        this.jwtService = jwtService;
        this.addressService = addressService;
    }

    public Restaurant createRestaurant(CreateRestaurantRequest request, String jwt) throws Exception {
        Address address = addressService.save(request.address());
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = Restaurant.builder()
                .name(request.name())
                .cuisineType(request.cuisineType())
                .description(request.description())
                .openingHours(request.openingHours())
                .contactInformation(request.contactInformation())
                .address(request.address())
                .owner(user)
                .registrationDate(LocalDateTime.now())
                .open(true)
                .images(request.images())
                .build();
        return restaurantRepository.save(restaurant);


    }

    public Restaurant updateRestaurant(Long id, CreateRestaurantRequest request, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = findRestaurantById(id);
        if (restaurant == null) {
            throw new Exception("User not authorized to update this restaurant");
        }
        if (request.name() != null) {
            restaurant.setName(request.name());
        }
        if (request.cuisineType() != null) {
            restaurant.setCuisineType(request.cuisineType());
        }
        if (request.description() != null) {
            restaurant.setDescription(request.description());
        }
        if (request.openingHours() != null) {
            restaurant.setOpeningHours(request.openingHours());
        }
        if (request.contactInformation() != null) {
            restaurant.setContactInformation(request.contactInformation());
        }
        if (request.images() != null) {
            restaurant.setImages(request.images());
        }
        return restaurantRepository.save(restaurant);
    }

    public void deleteRestaurant(Long id) throws Exception {
        Restaurant restaurant = findRestaurantById(id);
        try {
            restaurantRepository.delete(restaurant);
        } catch (Exception e) {
            throw new Exception("Restaurant not found");
        }

    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public List<Restaurant> searchRestaurants(String query) {
        return restaurantRepository.findBySearchQuery(query);
    }

    public Restaurant findRestaurantByUserId(Long id) throws Exception {
        Restaurant restaurant = restaurantRepository.findByOwnerId(id);
        if (restaurant != null) {
            return restaurant;
        } else {
            throw new Exception("Restaurant not found");
        }
    }

    public RestaurantDto addToFavorites(String jwt, Long id) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = findRestaurantById(id);
        RestaurantDto restaurantDto = RestaurantDto.builder()
                .description(restaurant.getDescription())
                .title(restaurant.getName())
                .images(restaurant.getImages())
                .id(restaurant.getId())
                .build();
        if (user.getFavorites().contains(restaurantDto)) {
            user.getFavorites().remove(restaurantDto);
        } else user.getFavorites().add(restaurantDto);

        userService.save(user);
        return restaurantDto;

    }

    public Restaurant findRestaurantById(Long id) {
        return restaurantRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Restaurant not found"));
    }

    public Restaurant updateRestaurantStatus(Long id, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = findRestaurantById(id);
        restaurant.setOpen(!restaurant.isOpen());
        return restaurantRepository.save(restaurant);
    }

}
