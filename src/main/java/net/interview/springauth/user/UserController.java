package net.interview.springauth.user;

import org.springframework.web.bind.annotation.RequestMapping;

public class UserController {
  @RequestMapping("/")
  public String hello(){}
}
