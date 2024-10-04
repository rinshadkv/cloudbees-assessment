package com.cloudbees.trainbooking.Controller;

import com.cloudbees.trainbooking.Domain.User;
import com.cloudbees.trainbooking.Repository.UserRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/users")
public class UserController {


    private final  UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("")
    public User createUser(@RequestBody User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email Exist,please use another one");
        }
        User result = new User(user.getFirstName(), user.getLastName(), user.getEmail());
        userRepository.save(result);
        return result;
    }
}
