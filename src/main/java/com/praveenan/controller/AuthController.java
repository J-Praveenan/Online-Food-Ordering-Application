package com.praveenan.controller;

import com.praveenan.config.JwtProvider;
import com.praveenan.enums.USER_ROLE;
import com.praveenan.model.Cart;
import com.praveenan.model.User;
import com.praveenan.repository.CartRepository;
import com.praveenan.repository.UserRepository;
import com.praveenan.request.LoginRequest;
import com.praveenan.response.AuthResponse;
import com.praveenan.service.CustomUserDetailsService;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private JwtProvider jwtProvider;
  @Autowired
  private CustomUserDetailsService customUserDetailsService;
  @Autowired
  private CartRepository cartRepository;

  @PostMapping("/signup")
  public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {
    User isEmailExist = userRepository.findByEmail(user.getEmail());

    if (isEmailExist != null) {
      throw new Exception("Email is already used with an another account");
    }

    User createdUser = new User();
    createdUser.setEmail(user.getEmail());
    createdUser.setFullName(user.getFullName());
    createdUser.setRole(user.getRole());
    createdUser.setPassword(passwordEncoder.encode(user.getPassword()));

    User savedUser = userRepository.save(createdUser);

    Cart cart = new Cart();
    cart.setCustomer(savedUser);
    cartRepository.save(cart);

    Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(),
        user.getPassword());
    SecurityContextHolder.getContext().setAuthentication(authentication);

    String jwt = jwtProvider.generateToken(authentication);

    AuthResponse response = new AuthResponse();
    response.setJwt(jwt);
    response.setRole(createdUser.getRole());
    response.setMessage("Register Success.");

    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @PostMapping("/signIn")
  public ResponseEntity<AuthResponse>signIn(@RequestBody LoginRequest request){

    String username = request.getEmail();
    String password = request.getPassword();
    
    Authentication authentication = authenticate(username, password);

    String jwt = jwtProvider.generateToken(authentication);

    AuthResponse response = new AuthResponse();
    response.setJwt(jwt);

    Collection<? extends GrantedAuthority>authorities = authentication.getAuthorities();
    String role = authorities.isEmpty()?null:authorities.iterator().next().getAuthority();
    response.setRole(USER_ROLE.valueOf(role));
    response.setMessage("Login Success.");

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  private Authentication authenticate(String username, String password) {

    UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

    if(userDetails == null){
      throw new BadCredentialsException("Invalid username...");
    }

    if(!passwordEncoder.matches(password, userDetails.getPassword())){
      throw new BadCredentialsException("Invalid password...");
    }

    return new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());

  }

}
