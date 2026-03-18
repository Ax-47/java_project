package com.example.restservice.Frontend.Controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.restservice.Address.models.AddressSortField;
import com.example.restservice.Address.usecases.FindAddressesByUserIdUsecase;
import com.example.restservice.Address.usecases.FindAddressesUsecase;
import com.example.restservice.Auth.dto.UserPrincipalDTO;
import com.example.restservice.Categories.usecases.FindCategoriesUsecase;
import com.example.restservice.Frontend.dto.CategorySectionDTO;
import com.example.restservice.Frontend.usecases.GetCategoryPageUsecase;
import com.example.restservice.Frontend.usecases.GetHomePageUsecase;
import com.example.restservice.Images.domain.ImageResourceType;
import com.example.restservice.Images.usecases.UploadImageUsecase;
import com.example.restservice.Orders.usecases.GetUserOrdersUsecase;
import com.example.restservice.ProductCategories.usecases.FindProductsByCategoryIdUsecase;
import com.example.restservice.Products.usecases.FindProductUsecase;
import com.example.restservice.Products.usecases.PurchaseProductUsecase;
import com.example.restservice.Reviews.dto.CreateReviewRequestDTO;
import com.example.restservice.Reviews.dto.ReviewRequestDTO;
import com.example.restservice.Reviews.usecases.CreateReviewUsecase;
import com.example.restservice.Reviews.usecases.DeleteReviewUsecase;
import com.example.restservice.Reviews.usecases.FindReveiwByProductUsecase;
import com.example.restservice.Reviews.usecases.UpdateReviewUsecase;
import com.example.restservice.TransactionStatements.dto.GetTransactionStatementsByUserRequestDTO;
import com.example.restservice.TransactionStatements.usecases.GetTransactionStatementsByUserUsecase;
import com.example.restservice.Users.usecases.FindUserByIdUsecase;
import com.example.restservice.Users.usecases.FindUserProfileUsecase;
import com.example.restservice.common.PageQuery;

/** Controller for the home page. */
@Controller
public class FrontendController {
  private final FindProductsByCategoryIdUsecase findProductsByCategoryIdUsecase;
  private final FindCategoriesUsecase findCategoriesUsecase;
  private final FindAddressesByUserIdUsecase findAddressesByUserIdUsecase;
  private final FindAddressesUsecase findAddressesUsecase;
  private final FindReveiwByProductUsecase findReveiwByProductUsecase;
  private final FindUserByIdUsecase findUserByIdUsecase;
  private final FindUserProfileUsecase findUserProfileUsecase;
  private final FindProductUsecase findProductUsecase;
  private final GetHomePageUsecase getHomePageUsecase;
  private final GetCategoryPageUsecase getCategoryPageUsecase;
  private final PurchaseProductUsecase purchaseProductUsecase;
  private final CreateReviewUsecase createReviewUsecase;
  private final UpdateReviewUsecase updateReviewUsecase;
  private final GetTransactionStatementsByUserUsecase getTransactionStatementsByUserUsecase;
  private final UploadImageUsecase uploadImageUsecase;
  private final DeleteReviewUsecase deleteReviewUsecase;
  private final GetUserOrdersUsecase getUserOrdersUsecase;

  public FrontendController(
      FindProductsByCategoryIdUsecase findProductsByCategoryIdUsecase,
      FindProductUsecase findProductUsecase,
      GetHomePageUsecase getHomePageUsecase,
      FindAddressesUsecase findAddressesUsecase,
      FindReveiwByProductUsecase findReveiwByProductUsecase,
      FindUserByIdUsecase findUserByIdUsecase,
      FindUserProfileUsecase findUserProfileUsecase,
      FindAddressesByUserIdUsecase findAddressesByUserIdUsecase,
      GetCategoryPageUsecase getCategoryPageUsecase,
      PurchaseProductUsecase purchaseProductUsecase,
      CreateReviewUsecase createReviewUsecase,
      UploadImageUsecase uploadImageUsecase,
      UpdateReviewUsecase updateReviewUsecase,
      DeleteReviewUsecase deleteReviewUsecase,
      GetTransactionStatementsByUserUsecase getTransactionStatementsByUserUsecase,
      GetUserOrdersUsecase getUserOrdersUsecase,
      FindCategoriesUsecase findCategoriesUsecase) {
    this.findProductsByCategoryIdUsecase = findProductsByCategoryIdUsecase;
    this.findCategoriesUsecase = findCategoriesUsecase;
    this.getHomePageUsecase = getHomePageUsecase;
    this.getCategoryPageUsecase = getCategoryPageUsecase;
    this.findProductUsecase = findProductUsecase;
    this.findAddressesByUserIdUsecase = findAddressesByUserIdUsecase;
    this.findAddressesUsecase = findAddressesUsecase;
    this.findReveiwByProductUsecase = findReveiwByProductUsecase;
    this.findUserProfileUsecase = findUserProfileUsecase;
    this.findUserByIdUsecase = findUserByIdUsecase;
    this.purchaseProductUsecase = purchaseProductUsecase;
    this.createReviewUsecase = createReviewUsecase;
    this.uploadImageUsecase = uploadImageUsecase;
    this.updateReviewUsecase = updateReviewUsecase;
    this.deleteReviewUsecase = deleteReviewUsecase;
    this.getTransactionStatementsByUserUsecase = getTransactionStatementsByUserUsecase;
    this.getUserOrdersUsecase = getUserOrdersUsecase;
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
      @PathVariable UUID productId,
      @AuthenticationPrincipal UserPrincipalDTO user,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "rating") String sortBy,
      @RequestParam(defaultValue = "false") boolean asc,
      Model model) {
    model.addAttribute("principal", user);
    var product = findProductUsecase.execute(productId);
    model.addAttribute("product", product);
    PageQuery query = new PageQuery(page, size, sortBy, asc);
    var reviews = findReveiwByProductUsecase.execute(productId, query);
    model.addAttribute("reviews", reviews);

    AddressSortField sortField = AddressSortField.fromString("isDefault");
    PageQuery addressQuery = new PageQuery(page, 20, sortField.getFieldName(), asc);
    var addresses = findAddressesByUserIdUsecase.execute(user.id(), addressQuery);

    model.addAttribute("addresses", addresses.content());
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

  @PostMapping("/products/{productId}/purchase")
  public String purchase(
      @PathVariable UUID productId,
      @RequestParam("addressId") UUID addressId,
      @AuthenticationPrincipal UserPrincipalDTO user) {

    purchaseProductUsecase.execute(user.id(), productId, addressId);
    return "redirect:/products/" + productId + "?success=true";
  }

  @PostMapping("/review")
  public String createReview(
      @RequestParam("productId") UUID productId,
      @RequestParam("rating") int rating,
      @RequestParam("comment") String comment,
      @RequestParam(value = "files", required = false) MultipartFile[] files,
      @AuthenticationPrincipal UserPrincipalDTO user) {
    var res =
        createReviewUsecase.execute(
            new CreateReviewRequestDTO(productId, user.id(), rating, comment));
    UUID reviewId = res.id();
    try {
      if (files != null && files.length > 0) {
        int i = 1;
        for (MultipartFile file : files) {
          if (!file.isEmpty()) {
            uploadImageUsecase.execute(file, reviewId, ImageResourceType.REVIEW, i);
            i++;
          }
        }
      }
    } catch (Exception e) {
      // ถ้าอัปโหลดรูปพัง อาจจะเปลี่ยนเป็นส่ง warning หรือ error กลับไปให้หน้าเว็บรู้
      return "redirect:/products/" + productId + "?success=true&imageUploadFailed=true";
    }
    return "redirect:/products/" + productId + "?success=true";
  }

  @PutMapping("/review/{reviewId}")
  public String updateReview(
      @PathVariable UUID reviewId,
      @RequestParam("productId") UUID productId,
      @RequestParam("rating") int rating,
      @RequestParam("comment") String comment,
      @RequestParam(value = "files", required = false) MultipartFile[] files,
      @AuthenticationPrincipal UserPrincipalDTO user) {

    var res = updateReviewUsecase.execute(reviewId, new ReviewRequestDTO(rating, comment));
    try {
      if (files != null && files.length > 0) {
        int i = 1;
        for (MultipartFile file : files) {
          if (!file.isEmpty()) {
            uploadImageUsecase.execute(file, reviewId, ImageResourceType.REVIEW, i);
            i++;
          }
        }
      }
    } catch (Exception e) {
      return "redirect:/products/" + productId + "?success=true";
    }
    return "redirect:/products/" + productId + "?success=true";
  }

  @DeleteMapping("/review/{reviewId}")
  public String deleteReview(
      @PathVariable UUID reviewId,
      @RequestParam("productId") UUID productId,
      @AuthenticationPrincipal UserPrincipalDTO user) {

    deleteReviewUsecase.execute(reviewId);
    return "redirect:/products/" + productId + "?success=true";
  }

  @GetMapping("/sign_in")
  public String signin() {

    return "sign_in/index";
  }

  @GetMapping("/sign_up")
  public String signup() {
    return "sign_up/index";
  }

  @GetMapping("/profile/transactions")
  public String viewTransactions(
      @AuthenticationPrincipal UserPrincipalDTO user,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      Model model) {
    PageQuery query = new PageQuery(page, size, "createdAt", false);

    var transactions =
        getTransactionStatementsByUserUsecase.execute(
            new GetTransactionStatementsByUserRequestDTO(user.id(), query));

    model.addAttribute("transactions", transactions);

    return "profile/transactions/index"; // คืนค่าหน้า HTML
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

  @GetMapping("/profile/orders")
  public String viewOrders(
      @AuthenticationPrincipal UserPrincipalDTO user,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      Model model) {
    PageQuery query = new PageQuery(page, size, "createdAt", false);
    var orders = getUserOrdersUsecase.execute(user.id(), query);
    model.addAttribute("orders", orders);
    return "profile/orders/index";
  }

  @GetMapping("/order")
  public String order(@AuthenticationPrincipal UserPrincipalDTO user, Model model) {

    model.addAttribute("user", user);
    return "order";
  }

  @GetMapping("/totup")
  public String cash(@AuthenticationPrincipal UserPrincipalDTO user, Model model) {

    var userDetail = findUserByIdUsecase.execute(user.id());
    model.addAttribute("detail", userDetail);
    return "totup/index";
  }
}
