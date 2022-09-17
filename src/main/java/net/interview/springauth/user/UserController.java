package net.interview.springauth.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {
  @Autowired
  UserRepository userRepository;

  @RequestMapping("/")
  public String hello(){
    return "Hello SJAKNSAKNLDKJA";
  }

  @PostMapping(
          path = "login",
          consumes = MediaType.APPLICATION_JSON_VALUE,
          produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<?> login(@RequestBody() User user) {
    return new ResponseEntity(HttpStatus.OK);
  }

  @PostMapping(
          path = "register",
          consumes = MediaType.APPLICATION_JSON_VALUE,
          produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<?> register(@Valid @RequestBody User user, BindingResult result) {
    try {
      if(result.hasErrors()){
        return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
      }

      userRepository.save(user);

      return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (Exception e) {
      System.out.println(e.toString());
      return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }
  }
}
