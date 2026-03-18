package com.example.restservice.Controllers.Admin;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.restservice.Orders.usecases.*;

@Controller
@RequestMapping("/admin/orders")
public class AdminOrderController {

  private final GetUserOrderUsecase getUserOrderUsecase;
  private final CompleteOrderUsecase completeOrderUsecase;
  private final ShipOrderUsecase shipOrderUsecase;
  private final CancelOrderUsecase cancelOrderUsecase;

  public AdminOrderController(
      GetUserOrderUsecase getUserOrderUsecase,
      CompleteOrderUsecase completeOrderUsecase,
      ShipOrderUsecase shipOrderUsecase,
      CancelOrderUsecase cancelOrderUsecase) {
    this.getUserOrderUsecase = getUserOrderUsecase;
    this.completeOrderUsecase = completeOrderUsecase;
    this.shipOrderUsecase = shipOrderUsecase;
    this.cancelOrderUsecase = cancelOrderUsecase;
  }

  @GetMapping("/{orderId}/view")
  public String viewOrderDetail(@PathVariable UUID orderId, Model model) {
    var order = getUserOrderUsecase.execute(orderId);
    if (order == null) return "redirect:/admin?tab=orders";

    model.addAttribute("order", order);
    return "admin/orders/view/index";
  }

  @PatchMapping("/{orderId}/ship")
  public String markOrderAsShipped(
      @PathVariable UUID orderId, RedirectAttributes redirectAttributes) {
    try {
      shipOrderUsecase.execute(orderId);
      redirectAttributes.addFlashAttribute(
          "successMessage", "อัปเดตสถานะเป็นจัดส่งแล้ว (SHIPPED) เรียบร้อย");
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute(
          "errorMessage", "ไม่สามารถอัปเดตสถานะได้: " + e.getMessage());
    }
    return "redirect:/admin/orders/" + orderId + "/view";
  }

  @PatchMapping("/{orderId}/cancel")
  public String markOrderAsCancelled(
      @PathVariable UUID orderId, RedirectAttributes redirectAttributes) {
    try {
      cancelOrderUsecase.execute(orderId);
      redirectAttributes.addFlashAttribute("successMessage", "ยกเลิกคำสั่งซื้อ (CANCELLED) สำเร็จ");
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute(
          "errorMessage", "ไม่สามารถยกเลิกคำสั่งซื้อได้: " + e.getMessage());
    }
    return "redirect:/admin/orders/" + orderId + "/view";
  }

  @PatchMapping("/{orderId}/complete")
  public String markOrderAsCompleted(
      @PathVariable UUID orderId, RedirectAttributes redirectAttributes) {
    try {
      completeOrderUsecase.execute(orderId);
      redirectAttributes.addFlashAttribute("successMessage", "ปิดคำสั่งซื้อ (COMPLETED) สมบูรณ์");
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("errorMessage", "เกิดข้อผิดพลาด: " + e.getMessage());
    }
    return "redirect:/admin/orders/" + orderId + "/view";
  }
}
