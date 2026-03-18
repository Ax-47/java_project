package com.example.restservice.Controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.restservice.Auth.dto.SignInRequestDTO;
import com.example.restservice.Auth.usecases.SignInUsecase;
import com.example.restservice.Auth.usecases.SignOutUsecase;
import com.example.restservice.Exeptions.DomainException;
import com.example.restservice.Users.dto.CreateUserRequestDTO;
import com.example.restservice.Users.usecases.CreateUserUsecase;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class AuthController {

  private final SignInUsecase signInUsecase;
  private final SignOutUsecase signOutUsecase;
  private final CreateUserUsecase createUserUsecase;

  private final long accessTokenExpiredInSeconds;
  private final long refreshTokenExpiredInSeconds;

  public AuthController(
      SignOutUsecase signOutUsecase,
      SignInUsecase signInUsecase,
      CreateUserUsecase createUserUsecase,
      @Value("${token.access-token-expired-in-seconds}") long accessTokenExpiredInSeconds,
      @Value("${token.refresh-token-expired-in-seconds}") long refreshTokenExpiredInSeconds) {

    this.signOutUsecase = signOutUsecase;
    this.signInUsecase = signInUsecase;
    this.createUserUsecase = createUserUsecase;
    this.accessTokenExpiredInSeconds = accessTokenExpiredInSeconds;
    this.refreshTokenExpiredInSeconds = refreshTokenExpiredInSeconds;
  }

  @GetMapping("/signin")
  public String signin() {
    return "signin/index";
  }

  @GetMapping("/signup")
  public String signup() {
    return "signup/index";
  }

  @PostMapping("/signin")
  public String processLogin(
      @RequestParam("username") String username,
      @RequestParam("password") String password,
      HttpServletResponse response) {

    try {
      var tokens = signInUsecase.execute(new SignInRequestDTO(username, password));

      ResponseCookie accessCookie =
          ResponseCookie.from("access_token", tokens.access_token())
              .httpOnly(true)
              .secure(true)
              .path("/")
              .maxAge(accessTokenExpiredInSeconds)
              .sameSite("Lax")
              .build();
      response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());

      ResponseCookie refreshCookie =
          ResponseCookie.from("refresh_token", tokens.refresh_token())
              .httpOnly(true)
              .secure(true)
              .path("/")
              .maxAge(refreshTokenExpiredInSeconds)
              .sameSite("Lax")
              .build();
      response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());

      return "redirect:/";

    } catch (Exception e) {
      return "redirect:/signin?error=true";
    }
  }

  @PostMapping("/signout")
  public String processSignOut(
      // 💡 2. รับค่า Cookie มาจาก Browser โดยตรงเลยครับ
      @CookieValue(value = "refresh_token", required = false) String refreshToken,
      HttpServletResponse response) {

    try {
      if (refreshToken != null && !refreshToken.isBlank()) {
        signOutUsecase.execute(refreshToken); // 💡 ส่งไปลบออกจาก Database
      }
      clearCookies(response);
      return "redirect:/";

    } catch (Exception e) {
      return "redirect:/signin?error=true";
    }
  }

  private void clearCookies(HttpServletResponse response) {
    ResponseCookie accessCookie =
        ResponseCookie.from("access_token", "")
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(0)
            .sameSite("Lax")
            .build();
    response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());

    ResponseCookie refreshCookie =
        ResponseCookie.from("refresh_token", "")
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(0)
            .sameSite("Lax")
            .build();
    response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
  }

  @PostMapping("/signup")
  public String processSignup(
      @RequestParam("username") String username,
      @RequestParam("password") String password,
      @RequestParam("confirmPassword") String confirmPassword,
      RedirectAttributes redirectAttributes,
      HttpServletResponse response) {

    if (!password.equals(confirmPassword)) {
      redirectAttributes.addFlashAttribute("error", "รหัสผ่านและการยืนยันรหัสผ่านไม่ตรงกัน");
      return "redirect:/signup";
    }

    try {
      createUserUsecase.execute(new CreateUserRequestDTO(username, password));

      var tokens = signInUsecase.execute(new SignInRequestDTO(username, password));

      ResponseCookie accessCookie =
          ResponseCookie.from("access_token", tokens.access_token())
              .httpOnly(true)
              .secure(true)
              .path("/")
              .maxAge(accessTokenExpiredInSeconds)
              .sameSite("Lax")
              .build();
      response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());

      ResponseCookie refreshCookie =
          ResponseCookie.from("refresh_token", tokens.refresh_token())
              .httpOnly(true)
              .secure(true)
              .path("/")
              .maxAge(refreshTokenExpiredInSeconds)
              .sameSite("Lax")
              .build();
      response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
      return "redirect:/";

    } catch (IllegalArgumentException e) {
      redirectAttributes.addFlashAttribute("error", e.getMessage());
      return "redirect:/signup";
    } catch (DomainException e) {
      redirectAttributes.addFlashAttribute("error", e.getMessage());
      return "redirect:/signup";
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute(
          "error", "เกิดข้อผิดพลาดในการสมัครสมาชิก โปรดลองใหม่อีกครั้ง");
      return "redirect:/signup";
    }
  }
}
