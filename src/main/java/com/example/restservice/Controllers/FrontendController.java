package com.example.restservice.Controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller for the home page.
 */
@Controller
public class FrontendController {

  // @GetMapping("/")
  // public String home(Model model, Principal principal) {
  // if (principal != null) {
  // model.addAttribute("username", principal.getName());
  // }
  // return "index";
  // }

  @GetMapping("/signin")
  public String signin() {
    return "signin";
  }
}
