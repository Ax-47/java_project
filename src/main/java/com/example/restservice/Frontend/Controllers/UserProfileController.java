package com.example.restservice.Frontend.Controllers;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.restservice.Auth.dto.UserPrincipalDTO;
import com.example.restservice.Orders.usecases.CancelOrderUsecase;
import com.example.restservice.Orders.usecases.GetUserOrdersUsecase;
import com.example.restservice.TransactionStatements.domain.TransactionStatementsMethod;
import com.example.restservice.TransactionStatements.dto.GetTransactionStatementsByUserRequestDTO;
import com.example.restservice.TransactionStatements.usecases.GetTransactionStatementsByUserUsecase;
import com.example.restservice.Users.usecases.FindUserByIdUsecase;
import com.example.restservice.Users.usecases.FindUserProfileUsecase;
import com.example.restservice.Users.usecases.TopupCreditUsecase;
import com.example.restservice.common.PageQuery;

@Controller
public class UserProfileController {
  private final FindUserProfileUsecase findUserProfileUsecase;
  private final FindUserByIdUsecase findUserByIdUsecase;
  private final GetUserOrdersUsecase getUserOrdersUsecase;
  private final GetTransactionStatementsByUserUsecase getTransactionStatementsByUserUsecase;
  private final TopupCreditUsecase topupCreditUsecase;
  private final CancelOrderUsecase cancelOrderUsecase;

  public UserProfileController(
      FindUserProfileUsecase findUserProfileUsecase,
      FindUserByIdUsecase findUserByIdUsecase,
      GetUserOrdersUsecase getUserOrdersUsecase,
      TopupCreditUsecase topupCreditUsecase,
      CancelOrderUsecase cancelOrderUsecase,
      GetTransactionStatementsByUserUsecase getTransactionStatementsByUserUsecase) {
    this.findUserProfileUsecase = findUserProfileUsecase;
    this.findUserByIdUsecase = findUserByIdUsecase;
    this.getUserOrdersUsecase = getUserOrdersUsecase;
    this.getTransactionStatementsByUserUsecase = getTransactionStatementsByUserUsecase;
    this.topupCreditUsecase = topupCreditUsecase;
    this.cancelOrderUsecase = cancelOrderUsecase;
  }

  @GetMapping("/profile")
  public String profile(@AuthenticationPrincipal UserPrincipalDTO user, Model model) {
    model.addAttribute("principal", user);
    model.addAttribute("detail", findUserProfileUsecase.execute(user.id()));
    return "profile/index";
  }

  @GetMapping("/profile/orders")
  public String viewOrders(
      @AuthenticationPrincipal UserPrincipalDTO user,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      Model model) {
    PageQuery query = new PageQuery(page, size, "createdAt", false);
    model.addAttribute("orders", getUserOrdersUsecase.execute(user.id(), query));
    return "profile/orders/index";
  }

  @GetMapping("/profile/transactions")
  public String viewTransactions(
      @AuthenticationPrincipal UserPrincipalDTO user,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      Model model) {
    PageQuery query = new PageQuery(page, size, "createdAt", false);
    var requestDTO = new GetTransactionStatementsByUserRequestDTO(user.id(), query);
    model.addAttribute("transactions", getTransactionStatementsByUserUsecase.execute(requestDTO));
    return "profile/transactions/index";
  }

  @GetMapping("/topup")
  public String cash(@AuthenticationPrincipal UserPrincipalDTO user, Model model) {
    model.addAttribute("detail", findUserByIdUsecase.execute(user.id()));
    return "topup/index";
  }

  @PostMapping("/topup")
  public String processTopup(
      @AuthenticationPrincipal UserPrincipalDTO user,
      @RequestParam("amount") BigDecimal amount,
      @RequestParam("method") TransactionStatementsMethod method) {

    try {
      if (amount.compareTo(new BigDecimal("10")) < 0) {
        return "redirect:/topup?error=true";
      }

      topupCreditUsecase.execute(user.id(), amount, method);

      return "redirect:/topup?success=true";

    } catch (Exception e) {
      return "redirect:/topup?error=true";
    }
  }

  @PostMapping("/profile/orders/{orderId}/cancel")
  public String cancelOrderAndRefund(
      @PathVariable UUID orderId,
      @AuthenticationPrincipal UserPrincipalDTO user,
      RedirectAttributes redirectAttributes) {
    try {
      cancelOrderUsecase.execute(orderId);

      redirectAttributes.addFlashAttribute(
          "successMessage", "ยกเลิกคำสั่งซื้อและคืนเงินเรียบร้อยแล้ว");
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute(
          "errorMessage", "ไม่สามารถยกเลิกคำสั่งซื้อได้: " + e.getMessage());
    }
    return "redirect:/profile/orders";
  }
}
