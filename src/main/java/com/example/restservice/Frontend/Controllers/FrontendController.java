package com.example.restservice.Frontend.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.restservice.Auth.dto.UserPrincipalDTO;
import com.example.restservice.Categories.usecases.FindCategoriesUsecase;
import com.example.restservice.Frontend.dto.CategoryWithProducts;
import com.example.restservice.ProductCategories.usecases.FindProductsByCategoryIdUsecase;
import com.example.restservice.Products.usecases.FindProductUsecase;
import com.example.restservice.common.PageQuery;

/** Controller for the home page. */
@Controller
public class FrontendController {
  private final FindProductsByCategoryIdUsecase findProductsByCategoryIdUsecase;
  private final FindCategoriesUsecase findCategoriesUsecase;
  private final FindProductUsecase findProductUsecase;

  public FrontendController(
      FindProductsByCategoryIdUsecase findProductsByCategoryIdUsecase,
      FindProductUsecase findProductUsecase,
      FindCategoriesUsecase findCategoriesUsecase) {
    this.findProductsByCategoryIdUsecase = findProductsByCategoryIdUsecase;
    this.findCategoriesUsecase = findCategoriesUsecase;
    this.findProductUsecase = findProductUsecase;
  }

  @GetMapping("/")
  public String index(
      @AuthenticationPrincipal UserPrincipalDTO user,
      @RequestParam(required = false) UUID activeCategoryId,
      Model model) {
    model.addAttribute("user", user);
    PageQuery categoryQuery = new PageQuery(0, 5, "categoryName", true);
    var categories = findCategoriesUsecase.execute(categoryQuery);
    PageQuery productQuery = new PageQuery(0, 50, "productName", true);
    List<CategoryWithProducts> categoryWithProducts = new ArrayList<>();
    for (var category : categories.content()) {
      var products = findProductsByCategoryIdUsecase.execute(category.id(), productQuery);
      categoryWithProducts.add(
          new CategoryWithProducts(category.id(), category.name(), products.content()));
    }
    model.addAttribute("activeCategoryId", activeCategoryId);
    model.addAttribute("categories", categoryWithProducts);
    return "index";
  }

  @GetMapping("/products/{productId}")
  public String product(
      @PathVariable UUID productId, @AuthenticationPrincipal UserPrincipalDTO user, Model model) {
    model.addAttribute("user", user);
    var product = findProductUsecase.execute(productId);
    model.addAttribute("product", product);
    return "products/index";
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
