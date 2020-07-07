package com.redis.springbootdemo.controller;

import com.redis.springbootdemo.entity.User;
import com.redis.springbootdemo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Log4j2
public class UserController {

  private final UserService userService;

  @PostMapping
  public User saveUser(@Valid @RequestBody User user) {
    log.info("Rest API To Save User {} ", user);
    return userService.saveUser(user);
  }

  @PutMapping
  @CachePut(value = "users", key = "#result.id", unless = "#result.followers < 12000")
  @CacheEvict(value = "users", key = "#user.id" ,condition = "#user.followers < 12000")
  public User updateUser(@Valid @RequestBody User user) {
    log.info("Rest API To Update User {} ", user);
    return userService.updateUser(user);
  }

  @GetMapping("/{id}")
  @Cacheable(value = "users", key = "#id", unless = "#result.followers < 12000")
  public User findUserById(@PathVariable Long id) {
    log.info("Rest Api To Get User By Id {} ", id);
    return userService.getUserById(id);
  }

  @GetMapping
  public List<User> getAllUsers() {
    log.info("Rest Api To Get All Users");
    return userService.getAllUsers();
  }

  @DeleteMapping("/{id}")
  @CacheEvict(value = "users", key = "#id")
  public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
    log.info("Rest Api To Delete User By Id {} ", id);
    userService.deleteUserById(id);
    return ResponseEntity.ok("User Deleted Successfully");
  }

}
