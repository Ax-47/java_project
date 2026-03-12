package com.example.restservice.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/** Controller for the home page. */
@Controller
public class FrontendController {

  @GetMapping("/dashboard")
  public String dashboard() {
    return "dashboard";
  }

  @GetMapping("/signin")
  public String signin() {
    return "signin";
  }
}
