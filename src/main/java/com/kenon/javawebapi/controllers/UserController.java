package com.kenon.javawebapi.controllers;

import com.kenon.javawebapi.entities.User;
import com.kenon.javawebapi.exceptions.ResourceNotFound;
import com.kenon.javawebapi.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping
    public List<User> getAllUsers(){
        return this.userRepo.findAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable (value = "id") long userId){
        return this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFound("User not found"));
    }

    @PostMapping
    public User createUser(@RequestBody User user){
        return this.userRepo.save(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@RequestBody User user, @PathVariable (name = "id") long userId){
        User existingUser = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFound("User not found"));
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        return this.userRepo.save(existingUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable (name = "id") long userId){
        User existingUser = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFound("User not found"));
        this.userRepo.delete(existingUser);
        return ResponseEntity.ok().build();
    }
}
