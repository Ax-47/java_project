package com.example.restservice.Frontend.Controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.restservice.Address.models.AddressSortField;
import com.example.restservice.Address.usecases.FindAddressesByUserIdUsecase;
import com.example.restservice.Auth.dto.UserPrincipalDTO;
import com.example.restservice.Frontend.dto.CategorySectionDTO;
import com.example.restservice.Frontend.usecases.GetCategoryPageUsecase;
import com.example.restservice.Frontend.usecases.GetHomePageUsecase;
import com.example.restservice.Products.usecases.FindProductUsecase;
import com.example.restservice.Products.usecases.PurchaseProductUsecase;
import com.example.restservice.Reviews.usecases.FindReveiwByProductUsecase;
import com.example.restservice.common.PageQuery;

@Controller
public class StoreController {
  private final GetHomePageUsecase getHomePageUsecase;
  private final GetCategoryPageUsecase getCategoryPageUsecase;
  private final FindProductUsecase findProductUsecase;
  private final FindReveiwByProductUsecase findReveiwByProductUsecase;
  private final FindAddressesByUserIdUsecase findAddressesByUserIdUsecase;
  private final PurchaseProductUsecase purchaseProductUsecase;

  public StoreController(
      GetHomePageUsecase getHomePageUsecase,
      GetCategoryPageUsecase getCategoryPageUsecase,
      FindProductUsecase findProductUsecase,
      FindReveiwByProductUsecase findReveiwByProductUsecase,
      FindAddressesByUserIdUsecase findAddressesByUserIdUsecase,
      PurchaseProductUsecase purchaseProductUsecase) {
    this.getHomePageUsecase = getHomePageUsecase;
    this.getCategoryPageUsecase = getCategoryPageUsecase;
    this.findProductUsecase = findProductUsecase;
    this.findReveiwByProductUsecase = findReveiwByProductUsecase;
    this.findAddressesByUserIdUsecase = findAddressesByUserIdUsecase;
    this.purchaseProductUsecase = purchaseProductUsecase;
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
    if (!allowedSortFields.contains(sortBy)) sortBy = "productName";

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

  @GetMapping("/products/{productId}")
  public String product(
      @PathVariable UUID productId,
      @AuthenticationPrincipal UserPrincipalDTO user,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "rating") String sortBy,
      @RequestParam(defaultValue = "false") boolean asc,
      Model model) {

    model.addAttribute("principal", user);
    model.addAttribute("product", findProductUsecase.execute(productId));

    PageQuery query = new PageQuery(page, size, sortBy, asc);
    model.addAttribute("reviews", findReveiwByProductUsecase.execute(productId, query));

    if (user != null) {
      AddressSortField sortField = AddressSortField.fromString("isDefault");
      PageQuery addressQuery = new PageQuery(page, 20, sortField.getFieldName(), asc);
      model.addAttribute(
          "addresses", findAddressesByUserIdUsecase.execute(user.id(), addressQuery).content());
    }

    return "products/productId";
  }

  @PostMapping("/products/{productId}/purchase")
  public String purchase(
      @PathVariable UUID productId,
      @RequestParam("addressId") UUID addressId,
      @AuthenticationPrincipal UserPrincipalDTO user) {
    purchaseProductUsecase.execute(user.id(), productId, addressId);
    return "redirect:/products/" + productId + "?success=true";
  }
}
