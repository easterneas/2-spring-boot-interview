package net.interview.springauth.user;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.interview.springauth.helper.HashHelper;
import net.interview.springauth.user.dto.UserLoginDto;
import net.interview.springauth.user.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {
  @Autowired
  UserRepository userRepository;

  @PostMapping(
          path = "login",
          consumes = MediaType.APPLICATION_JSON_VALUE,
          produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<?> login(@Valid @RequestBody UserLoginDto user, BindingResult result) {
    HashHelper hh = new HashHelper();
    String jwToken;

    try {
      if(result.hasErrors()){
        return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
      }

      Users _user = userRepository.findOneByUsername(user.getUsername()).orElseThrow(
              () -> new UsernameNotFoundException("Sorry, username is not found.")
      );

      if(hh.checkPassword(user.getPassword().toCharArray(), _user.getPassword())) {
        jwToken = Jwts.builder()
                .setIssuer("SpringTest")
                .setSubject("UsernameTokenIdentifier")
                .claim("username", _user.getUsername())
                .signWith(
                        SignatureAlgorithm.HS256,
                        "SuperSecretThingyThatShouldWorkAfterGeneratingAFewMoreWordsIntoItAndItShouldWorkThisTime."
                )
                .compact();
      } else {
        // this will ensure that the stored is secure, even if it's found.
        throw new UsernameNotFoundException("Sorry, username is not found.");
      }

      return new ResponseEntity<String>(jwToken, HttpStatus.OK);
    } catch (Exception e) {
      System.out.println(e.toString());
      return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping(
          path = "register",
          consumes = MediaType.APPLICATION_JSON_VALUE,
          produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<?> register(@Valid @RequestBody UserRegistrationDto user, BindingResult result) {
    try {
      if(result.hasErrors()){
        return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
      }

      userRepository.save(user.toUser());

      return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (Exception e) {
      System.out.println(e.toString());
      return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }
  }
}
