package com.example.restservice.Frontend.Controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.restservice.Address.usecases.FindAddressesByUserIdUsecase;
import com.example.restservice.Address.usecases.FindAddressesUsecase;
import com.example.restservice.Auth.dto.UserPrincipalDTO;
import com.example.restservice.Categories.usecases.FindCategoriesUsecase;
import com.example.restservice.Frontend.dto.CategorySectionDTO;
import com.example.restservice.Frontend.usecases.GetCategoryPageUsecase;
import com.example.restservice.Frontend.usecases.GetHomePageUsecase;
import com.example.restservice.ProductCategories.usecases.FindProductsByCategoryIdUsecase;
import com.example.restservice.Products.usecases.FindProductUsecase;
import com.example.restservice.Users.usecases.FindUserProfileUsecase;
import com.example.restservice.common.PageQuery;

/** Controller for the home page. */
@Controller
public class FrontendController {
  private final FindProductsByCategoryIdUsecase findProductsByCategoryIdUsecase;
  private final FindCategoriesUsecase findCategoriesUsecase;
  private final FindAddressesByUserIdUsecase findAddressesByUserIdUsecase;
  private final FindAddressesUsecase findAddressesUsecase;
  private final FindUserProfileUsecase findUserProfileUsecase;
  private final FindProductUsecase findProductUsecase;
  private final GetHomePageUsecase getHomePageUsecase;
  private final GetCategoryPageUsecase getCategoryPageUsecase;

  public FrontendController(
      FindProductsByCategoryIdUsecase findProductsByCategoryIdUsecase,
      FindProductUsecase findProductUsecase,
      GetHomePageUsecase getHomePageUsecase,
      FindAddressesUsecase findAddressesUsecase,
      FindUserProfileUsecase findUserProfileUsecase,
      FindAddressesByUserIdUsecase findAddressesByUserIdUsecase,
      GetCategoryPageUsecase getCategoryPageUsecase,
      FindCategoriesUsecase findCategoriesUsecase) {
    this.findProductsByCategoryIdUsecase = findProductsByCategoryIdUsecase;
    this.findCategoriesUsecase = findCategoriesUsecase;
    this.getHomePageUsecase = getHomePageUsecase;
    this.getCategoryPageUsecase = getCategoryPageUsecase;
    this.findProductUsecase = findProductUsecase;
    this.findAddressesByUserIdUsecase = findAddressesByUserIdUsecase;
    this.findAddressesUsecase = findAddressesUsecase;
    this.findUserProfileUsecase = findUserProfileUsecase;
  }

  @GetMapping("/")
  public String index(
      @AuthenticationPrincipal UserPrincipalDTO user,
      @RequestParam(required = false) UUID activeCategoryId,
      Model model) {
    model.addAttribute("user", user);
    PageQuery query = new PageQuery(0, 4, "product.productName", true);
    List<CategorySectionDTO> categories = getHomePageUsecase.execute(activeCategoryId, query);
    model.addAttribute("activeCategoryId", activeCategoryId);
    model.addAttribute("categories", categories);
    return "index";
  }

  @GetMapping("/products/{productId}")
  public String product(
      @PathVariable UUID productId, @AuthenticationPrincipal UserPrincipalDTO user, Model model) {
    model.addAttribute("user", user);
    var product = findProductUsecase.execute(productId);
    model.addAttribute("product", product);
    return "products/productId";
  }

  @GetMapping("/categories/{categoryId}")
  public String viewAllProductsByCategory(
      @PathVariable UUID categoryId,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "productName") String sortBy,
      @RequestParam(defaultValue = "true") boolean asc,
      @AuthenticationPrincipal UserPrincipalDTO user,
      Model model) {

    model.addAttribute("user", user);
    List<String> allowedSortFields = List.of("productName", "productPrice", "createdAt");

    if (!allowedSortFields.contains(sortBy)) {
      sortBy = "productName";
    }
    int pageSize = 12;
    PageQuery query = new PageQuery(page, pageSize, sortBy, asc);

    List<CategorySectionDTO> categories = getCategoryPageUsecase.execute(categoryId, query);

    CategorySectionDTO activeCategory =
        categories.stream().filter(c -> c.id().equals(categoryId)).findFirst().orElse(null);

    if (activeCategory == null) return "redirect:/";

    model.addAttribute("categoryName", activeCategory.name());
    model.addAttribute("products", activeCategory.products());
    model.addAttribute("categories", categories);
    model.addAttribute("activeCategoryId", categoryId);
    model.addAttribute("currentPage", page);
    model.addAttribute("sortBy", sortBy);
    model.addAttribute("asc", asc);
    model.addAttribute("hasMore", activeCategory.products().size() == pageSize);

    return "categories/categoryId";
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
    var userDetail = findUserProfileUsecase.execute(user.id());
    model.addAttribute("principal", user);
    model.addAttribute("detail", userDetail);
    return "profile/index";
  }

  @GetMapping("/order")
  public String order(@AuthenticationPrincipal UserPrincipalDTO user, Model model) {

    if (user == null) {
      return "redirect:/sign-in";
    }

    model.addAttribute("user", user);
    return "order";
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
