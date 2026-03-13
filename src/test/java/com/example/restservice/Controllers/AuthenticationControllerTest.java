package com.example.restservice.Controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.restservice.Auth.controllers.AuthenticationController;
import com.example.restservice.Auth.dto.SignInRequestDTO;
import com.example.restservice.Auth.dto.TokenResponseDTO;
import com.example.restservice.Auth.usecases.RefreshTokenUsecase;
import com.example.restservice.Auth.usecases.SignInUsecase;
import com.example.restservice.Auth.usecases.SignOutUsecase;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.Cookie;

@WebMvcTest(AuthenticationController.class)
@Import(ObjectMapper.class)
class AuthenticationControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private SignInUsecase signInUsecase;
  @MockitoBean
  private RefreshTokenUsecase refreshTokenUsecase;
  @MockitoBean
  private SignOutUsecase signOutUsecase;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void signin_should_return_tokens() throws Exception {

    SignInRequestDTO request = new SignInRequestDTO("alex", "password123");
    TokenResponseDTO response = new TokenResponseDTO("access123", "refresh123");

    Mockito.when(signInUsecase.execute(request)).thenReturn(response);

    mockMvc
        .perform(
            post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.access_token").value("access123"))
        .andExpect(jsonPath("$.refresh_token").value("refresh123"));
  }

  @Test
  @AutoConfigureMockMvc(addFilters = false)
  void refresh_should_return_new_tokens() throws Exception {

    TokenResponseDTO response = new TokenResponseDTO("newAccess", "newRefresh");

    Mockito.when(refreshTokenUsecase.execute("refresh123")).thenReturn(response);

    mockMvc
        .perform(post("/api/auth/refresh").cookie(new Cookie("refresh_token", "refresh123")))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.access_token").value("newAccess"));
  }

  @Test
  @AutoConfigureMockMvc(addFilters = false)
  void signout_should_return_204() throws Exception {

    mockMvc
        .perform(post("/api/auth/signout").cookie(new Cookie("refresh_token", "refresh123")))
        .andExpect(status().isNoContent());
  }
}
