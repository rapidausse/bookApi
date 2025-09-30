package com.example.bookApi.controller;

import com.example.bookApi.model.entity.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.bookApi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }


    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getFirstName());
        existingUser.setRole(user.getRole());
        existingUser.setStatus(user.getStatus());

        return userRepository.save(existingUser);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "Book deleted";
    }
}
