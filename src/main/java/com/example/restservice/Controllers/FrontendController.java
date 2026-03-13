package com.example.restservice.Controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.restservice.Auth.dto.UserPrincipalDTO;

/** Controller for the home page. */
@Controller
public class FrontendController {

  @GetMapping("/dashboard")
  public String dashboard(@AuthenticationPrincipal UserPrincipalDTO userPrincipal, Model model) {

    model.addAttribute("user", userPrincipal);

    return "dashboard";
  }

  @GetMapping("/signin")
  public String signin() {
    return "signin";
  }

  @GetMapping("/")
  public String index(@AuthenticationPrincipal UserPrincipalDTO userPrincipal, Model model) {
    model.addAttribute("user", userPrincipal);
    return "index";
  }
}
