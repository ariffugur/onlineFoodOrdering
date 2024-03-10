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

    public Restaurant createRestaurant(CreateRestaurantRequest request, String jwt) {
        User user = userService.findUserByJwtToken(jwt);
        Address address = addressService.createAddress(request.address());
        log.info("User: " + user);
        return Restaurant.builder()
                .name(request.name())
                .cuisineType(request.cuisineType())
                .description(request.description())
                .openingHours(request.openingHours())
                .registrationDate(LocalDateTime.now())
                .contactInformation(request.contactInformation())
                .owner(user)
                .address(address)
                .images(request.images())
                .build();

    }

    public Restaurant updateRestaurant(Long id, CreateRestaurantRequest request, String jwt) {
        extractUsername(jwt);
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new RuntimeException("Restaurant not found"));
        if (request.cuisineType() != null) {
            restaurant.setCuisineType(request.cuisineType());
        }
        if (request.contactInformation() != null) {
            restaurant.setContactInformation(request.contactInformation());
        }
        if (request.description() != null) {
            restaurant.setDescription(request.description());
        }
        if (request.name() != null) {
            restaurant.setName(request.name());
        }
        if (request.openingHours() != null) {
            restaurant.setOpeningHours(request.openingHours());
        }
        if (request.images() != null) {
            restaurant.setImages(request.images());
        }
        return restaurantRepository.save(restaurant);

    }

    public void deleteRestaurant(Long id) throws Exception {
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);
        if (restaurant.isPresent()) {
            restaurantRepository.delete(restaurant.get());
        } else {
            throw new Exception("Restaurant not found");
        }
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public List<Restaurant> searchRestaurants(String query) {
        return restaurantRepository.findBySearchQuery(query);
    }

    public Restaurant findRestaurantById(Long id) throws Exception {
        Restaurant restaurant = restaurantRepository.findByOwnerId(id);
        if (restaurant != null) {
            return restaurant;
        } else {
            throw new Exception("Restaurant not found");
        }
    }

    public Restaurant getRestaurantById(Long id) throws Exception {
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);
        if (restaurant.isPresent()) {
            return restaurant.get();
        } else {
            throw new Exception("Restaurant not found");
        }
    }

    public RestaurantDto addToFavorites(String jwt, Long id) throws Exception {
        User user = extractUsername(jwt);
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

    public Restaurant updateRestaurantStatus(Long id) throws Exception {
        Restaurant restaurant = findRestaurantById(id);
        restaurant.setOpen(!restaurant.isOpen());
        return restaurantRepository.save(restaurant);
    }

    public User extractUsername(String jwt) {
        String token = jwt.substring(7);
        String username = jwtService.extractUsername(token);
        return userService.findUserByJwtToken(username);
    }
}
