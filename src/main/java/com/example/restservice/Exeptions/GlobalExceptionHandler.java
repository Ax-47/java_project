package com.example.restservice.Exeptions;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.example.restservice.Exeptions.dto.ErrorResponse;
import com.example.restservice.Users.exceptions.UserNotFoundException;
import com.example.restservice.Users.exceptions.UsernameAlreadyExistsException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  private boolean isApiRequest(HttpServletRequest request) {
    String acceptHeader = request.getHeader("Accept");
    String xRequestedWith = request.getHeader("X-Requested-With");
    return (acceptHeader != null && acceptHeader.contains("application/json"))
        || "XMLHttpRequest".equals(xRequestedWith)
        || request.getRequestURI().startsWith("/api/");
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Object handleIllegalArgument(
      MethodArgumentNotValidException ex, HttpServletRequest request) {
    if (isApiRequest(request)) {
      var errors =
          ex.getBindingResult().getFieldErrors().stream()
              .collect(
                  Collectors.toMap(error -> error.getField(), error -> error.getDefaultMessage()));
      return ResponseEntity.badRequest().body(errors);
    }

    ModelAndView mav = new ModelAndView("error/400");
    mav.addObject("errorMessage", "ข้อมูลที่ส่งมาไม่ถูกต้อง กรุณาตรวจสอบอีกครั้ง");
    return mav;
  }

  @ExceptionHandler(UsernameAlreadyExistsException.class)
  public Object handleUsernameExists(
      UsernameAlreadyExistsException ex, HttpServletRequest request) {
    if (isApiRequest(request)) {
      ErrorResponse response = new ErrorResponse("USERNAME_ALREADY_EXISTS", ex.getMessage());
      return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    ModelAndView mav = new ModelAndView("error/400");
    mav.addObject("errorMessage", ex.getMessage());
    return mav;
  }

  @ExceptionHandler(UserNotFoundException.class)
  public Object handleUserNotFound(UserNotFoundException ex, HttpServletRequest request) {
    if (isApiRequest(request)) {
      ErrorResponse response = new ErrorResponse("USER_NOT_FOUND", ex.getMessage());
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    ModelAndView mav = new ModelAndView("error/404");
    mav.addObject("errorMessage", ex.getMessage());
    return mav;
  }

  @ExceptionHandler(DomainException.class)
  public Object handleDomainException(DomainException ex, HttpServletRequest request) {
    log.warn("Domain Rule Violation: {}", ex.getMessage());

    if (isApiRequest(request)) {
      ErrorResponse response = new ErrorResponse("DOMAIN_RULE_VIOLATION", ex.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    ModelAndView mav = new ModelAndView("error/400");
    mav.addObject("errorMessage", ex.getMessage());
    return mav;
  }

  // ==========================================
  // 🔴 5. Catch-All for Unknown Errors (500 Internal Server Error)
  // ==========================================
  @ExceptionHandler(Exception.class)
  public Object handleAllUncaughtExceptions(Exception ex, HttpServletRequest request) {
    // 💡 ต้อง Log Error เสมอ เพื่อให้รู้ว่าระบบพังตรงไหน
    log.error("Unknown Error Occurred at {}: ", request.getRequestURI(), ex);

    if (isApiRequest(request)) {
      ErrorResponse response =
          new ErrorResponse("INTERNAL_SERVER_ERROR", "เกิดข้อผิดพลาดที่ระบบ โปรดลองใหม่อีกครั้ง");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    // โชว์หน้า 500
    return new ModelAndView("error/500");
  }
}
