package com.example.restservice.Controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.restservice.Auth.dto.UserPrincipalDTO;

/** Controller for the home page. */
@Controller
public class FrontendController {
  @GetMapping("/")
  public String index(@AuthenticationPrincipal UserPrincipalDTO user, Model model) {
    model.addAttribute("user", user);
    return "index";
  }

  @GetMapping("/sign-in")
  public String signin() {
    return "sign-in";
  }

  @GetMapping("/sign-up")
  public String signup() {
    return "sign-up";
  }

  /*
   * =========================
   * Protected pages
   * =========================
   */

  @GetMapping("/dashboard")
  public String dashboard(@AuthenticationPrincipal UserPrincipalDTO user, Model model) {

    if (user == null) {
      return "redirect:/sign-in";
    }

    model.addAttribute("user", user);
    return "dashboard";
  }

  @GetMapping("/profile")
  public String profile(@AuthenticationPrincipal UserPrincipalDTO user, Model model) {

    if (user == null) {
      return "redirect:/sign-in";
    }

    model.addAttribute("user", user);
    return "profile";
  }

  @GetMapping("/order")
  public String order(@AuthenticationPrincipal UserPrincipalDTO user, Model model) {

    if (user == null) {
      return "redirect:/sign-in";
    }

    model.addAttribute("user", user);
    return "order";
  }

  @GetMapping("/address")
  public String address(@AuthenticationPrincipal UserPrincipalDTO user, Model model) {

    if (user == null) {
      return "redirect:/sign-in";
    }

    model.addAttribute("user", user);
    return "address";
  }

  @GetMapping("/cash")
  public String cash(@AuthenticationPrincipal UserPrincipalDTO user, Model model) {

    if (user == null) {
      return "redirect:/sign-in";
    }

    model.addAttribute("user", user);
    return "cash";
  }
}
