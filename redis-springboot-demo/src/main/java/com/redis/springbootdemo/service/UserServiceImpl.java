package com.redis.springbootdemo.service;

import com.redis.springbootdemo.entity.User;
import com.redis.springbootdemo.exception.CustomException;
import com.redis.springbootdemo.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  @Transactional
  public User saveUser(User user) {
    log.info("Save User {}", user);
    if (user.getId() != null) {
      throw new CustomException("New User Shouldn't Have An Id", HttpStatus.BAD_REQUEST);
    }
    return userRepository.save(user);
  }

  @Override
  @Transactional
  public User updateUser(User user) {
    log.info("Update User {} ", user);
    if (user.getId() == null) {
      throw new CustomException("New User Must Have An Id", HttpStatus.BAD_REQUEST);
    }
    getUserById(user.getId());
    return userRepository.save(user);
  }

  @Override
  @Transactional(readOnly = true)
  public User getUserById(Long id) {
    log.info("Get User For The Id {} ", id);
    return userRepository
        .findById(id)
        .orElseThrow(() -> new CustomException("User Not Found For The Id "
            .concat(String.valueOf(id)), HttpStatus.NOT_FOUND));
  }

  @Override
  @Transactional(readOnly = true)
  public List<User> getAllUsers() {
    log.info("Get All Users");
    return userRepository
        .findAll();
  }

  @Override
  @Transactional
  public void deleteUserById(Long id) {
    log.info("Delete User For The Id {} ", id);
    userRepository
        .delete(getUserById(id));
  }
}
