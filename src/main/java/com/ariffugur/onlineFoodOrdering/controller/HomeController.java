package com.ariffugur.onlineFoodOrdering.controller;

import com.ariffugur.onlineFoodOrdering.model.User;
import com.ariffugur.onlineFoodOrdering.service.JwtService;
import com.ariffugur.onlineFoodOrdering.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class HomeController {

    private final JwtService jwtService;
    private final UserService userService;

    public HomeController(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @GetMapping("/welcome")
    public String home() {
        return "login success";
    }

    @GetMapping("/testRestaurant")
    public String testRestaurant(@RequestHeader("Authorization") String token) {
        try {
            String user = jwtService.extractUsername(token);
            User user1 = userService.findUserByJwtToken(user);

            if (user1 != null) {
                return user1.getFullName();
            } else {
                return "Kullanıcı bulunamadı.";
            }
        } catch (Exception e) {
            e.printStackTrace(); // Hata durumunda konsola hata mesajını yazdırabilirsiniz.
            return "Hata: " + e.getMessage();
        }
    }
}
