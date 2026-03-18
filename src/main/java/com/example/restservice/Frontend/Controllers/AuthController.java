package com.example.restservice.Frontend.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

  @GetMapping("/signin")
  public String signin() {
    return "signin/index";
  }

  @GetMapping("/signup")
  public String signup() {
    return "signup/index";
  }
}
