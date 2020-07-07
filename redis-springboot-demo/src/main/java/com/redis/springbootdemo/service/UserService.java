package com.redis.springbootdemo.service;

import com.redis.springbootdemo.entity.User;
import java.util.List;

public interface UserService {

  User saveUser(User user);

  User updateUser(User user);

  User getUserById(Long id);

  List<User> getAllUsers();

  void deleteUserById(Long id);
}
