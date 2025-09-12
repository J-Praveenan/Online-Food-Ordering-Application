package com.praveenan.service;

import com.praveenan.config.JwtProvider;
import com.praveenan.model.User;
import com.praveenan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private JwtProvider jwtProvider;

  @Override
  public User findUserByJwtToken(String jwt) throws Exception {
    String email = jwtProvider.getEmailFormJwtToken(jwt);
    User user = userRepository.findByEmail(email);

    if(user==null){
      throw new Exception("User not found.");
    }
    return user;

  }

  @Override
  public User findUserByEmail(String email) throws Exception {

    User user = userRepository.findByEmail(email);

    if(user==null){
      throw new Exception("User not found.");
    }
    return user;
  }
}
