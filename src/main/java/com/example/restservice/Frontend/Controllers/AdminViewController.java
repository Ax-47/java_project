package com.example.restservice.Frontend.Controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.restservice.Auth.dto.UserPrincipalDTO;
import com.example.restservice.Categories.domain.Category;
import com.example.restservice.Categories.domain.DatabaseCategoryRepository;
import com.example.restservice.Categories.dto.CategoryRequestDTO;
import com.example.restservice.Categories.usecases.CreateCategoryUsecase;
import com.example.restservice.Categories.usecases.DeleteCategoryUsecase;
import com.example.restservice.Categories.usecases.FindCategoriesUsecase;
import com.example.restservice.Categories.usecases.UpdateCategoryUsecase;
import com.example.restservice.Frontend.dto.CategoryFormDTO;
import com.example.restservice.Frontend.dto.CreateProductFormDTO;
import com.example.restservice.Images.domain.DatabaseImageRepository;
import com.example.restservice.Images.domain.ImageResourceType;
import com.example.restservice.Images.dto.UploadImageResponseDTO;
import com.example.restservice.Images.usecases.FindImageUsecase;
import com.example.restservice.Orders.domain.DatabaseOrderRepository;
import com.example.restservice.Orders.usecases.CancelOrderUsecase;
import com.example.restservice.Orders.usecases.CompleteOrderUsecase;
import com.example.restservice.Orders.usecases.GetOrdersUsecase;
import com.example.restservice.Orders.usecases.GetUserOrderUsecase;
import com.example.restservice.Orders.usecases.ShipOrderUsecase;
import com.example.restservice.ProductCategories.domain.DatabaseProductCategoryRepository;
import com.example.restservice.Products.domain.DatabaseProductRepository;
import com.example.restservice.Products.domain.Product;
import com.example.restservice.Products.dto.DeleteProductRequestDTO;
import com.example.restservice.Products.dto.UpdateProductRequestDTO;
import com.example.restservice.Products.usecases.CreateProductUsecase;
import com.example.restservice.Products.usecases.DeleteProductUsecase;
import com.example.restservice.Products.usecases.UpdateProductUsecase;
import com.example.restservice.Users.domain.DatabaseUserRepository;
import com.example.restservice.common.PageQuery;

@Controller
@RequestMapping("/admin")
public class AdminViewController {
  private final DatabaseUserRepository databaseUserRepository;
  private final DatabaseOrderRepository databaseOrderRepository;
  private final DatabaseProductCategoryRepository databaseProductCategoryRepository;
  private final DatabaseImageRepository databaseImageRepository;
  private final UpdateProductUsecase updateProductUsecase;
  private final FindImageUsecase findImageUsecase;
  private final DatabaseProductRepository databaseProductRepository;
  private final DatabaseCategoryRepository databaseCategoryRepository;
  private final DeleteProductUsecase deleteProductUsecase;
  private final CreateProductUsecase createProductUsecase;
  private final CreateCategoryUsecase createCategoryUsecase;
  private final UpdateCategoryUsecase updateCategoryUsecase;
  private final FindCategoriesUsecase findCategoriesUsecase;
  private final DeleteCategoryUsecase deleteCategoryUsecase;
  private final GetOrdersUsecase getOrdersUsecase;
  private final GetUserOrderUsecase getUserOrderUsecase;
  private final CompleteOrderUsecase completeOrderUsecase;
  private final ShipOrderUsecase shipOrderUsecase;
  private final CancelOrderUsecase cancelOrderUsecase;

  public AdminViewController(
      DatabaseUserRepository databaseUserRepository,
      DatabaseOrderRepository databaseOrderRepository,
      DatabaseProductCategoryRepository databaseProductCategoryRepository,
      DatabaseImageRepository databaseImageRepository,
      UpdateProductUsecase updateProductUsecase,
      FindImageUsecase findImageUsecase,
      DatabaseProductRepository databaseProductRepository,
      DatabaseCategoryRepository databaseCategoryRepository,
      DeleteProductUsecase deleteProductUsecase,
      CreateProductUsecase createProductUsecase,
      CreateCategoryUsecase createCategoryUsecase,
      UpdateCategoryUsecase updateCategoryUsecase,
      FindCategoriesUsecase findCategoriesUsecase,
      DeleteCategoryUsecase deleteCategoryUsecase,
      GetOrdersUsecase getOrdersUsecase,
      GetUserOrderUsecase getUserOrderUsecase,
      CompleteOrderUsecase completeOrderUsecase,
      ShipOrderUsecase shipOrderUsecase,
      CancelOrderUsecase cancelOrderUsecase) {
    this.databaseUserRepository = databaseUserRepository;
    this.databaseOrderRepository = databaseOrderRepository;
    this.databaseProductCategoryRepository = databaseProductCategoryRepository;
    this.databaseImageRepository = databaseImageRepository;
    this.updateProductUsecase = updateProductUsecase;
    this.findImageUsecase = findImageUsecase;
    this.databaseProductRepository = databaseProductRepository;
    this.databaseCategoryRepository = databaseCategoryRepository;
    this.deleteProductUsecase = deleteProductUsecase;
    this.createProductUsecase = createProductUsecase;
    this.createCategoryUsecase = createCategoryUsecase;
    this.updateCategoryUsecase = updateCategoryUsecase;
    this.findCategoriesUsecase = findCategoriesUsecase;
    this.deleteCategoryUsecase = deleteCategoryUsecase;
    this.getOrdersUsecase = getOrdersUsecase;
    this.getUserOrderUsecase = getUserOrderUsecase;
    this.completeOrderUsecase = completeOrderUsecase;
    this.shipOrderUsecase = shipOrderUsecase;
    this.cancelOrderUsecase = cancelOrderUsecase;
  }

  @GetMapping
  public String adminDashboard(
      @RequestParam(defaultValue = "0") int orderPage,
      @RequestParam(defaultValue = "0") int productPage,
      @RequestParam(defaultValue = "0") int categoryPage,
      @RequestParam(defaultValue = "50") int size,
      @RequestParam(defaultValue = "orders") String tab,
      @RequestParam(defaultValue = "createdAt") String sortBy,
      @RequestParam(defaultValue = "desc") String sortDir,
      Model model) {
    boolean sortDirection = sortDir.equalsIgnoreCase("asc") ? true : false;
    PageQuery orderQuery = new PageQuery(orderPage, size, sortBy, sortDirection);
    PageQuery productQuery = new PageQuery(productPage, size, sortBy, sortDirection);
    PageQuery categoryQuery = new PageQuery(categoryPage, size, sortBy, sortDirection);

    var orders = getOrdersUsecase.execute(orderQuery);
    var products = databaseProductRepository.findAllProducts(productQuery);
    var categories = databaseCategoryRepository.findAllCategories(categoryQuery);

    model.addAttribute("orders", orders.content());
    model.addAttribute("products", products.content());
    model.addAttribute("categories", categories.content());

    model.addAttribute("orderPage", orderPage);
    model.addAttribute("productPage", productPage);
    model.addAttribute("categoryPage", categoryPage);

    model.addAttribute("orderTotal", orders.totalPages());
    model.addAttribute("productTotal", products.totalPages());
    model.addAttribute("categoryTotal", categories.totalPages());

    model.addAttribute("activeTab", tab);
    model.addAttribute("sortBy", sortBy);
    model.addAttribute("sortDir", sortDir);

    return "admin/index";
  }

  @GetMapping("/products/edit/{productId}")
  public String adminEditProduct(@PathVariable UUID productId, Model model) {
    Product product = databaseProductRepository.findById(productId).orElseThrow();
    List<Category> allCategories = databaseCategoryRepository.findAll();

    List<Category> productCategories =
        databaseProductCategoryRepository.findCategoriesByProductId(productId);
    List<UploadImageResponseDTO> images =
        findImageUsecase.execute(productId, ImageResourceType.PRODUCT);
    model.addAttribute("product", product);
    model.addAttribute("productCategories", productCategories);
    model.addAttribute("images", images);
    model.addAttribute("allCategories", allCategories);

    return "admin/products/edit/index";
  }

  @GetMapping("/products/{productId}/images")
  public String adminImageMenage(@PathVariable UUID productId, Model model) {
    Product product = databaseProductRepository.findById(productId).orElseThrow();
    List<UploadImageResponseDTO> images =
        findImageUsecase.execute(productId, ImageResourceType.PRODUCT);
    model.addAttribute("product", product);
    model.addAttribute("images", images);

    return "admin/products/images/index";
  }

  // /admin/categories/create

  @GetMapping("/categories/create")
  public String createCategoryView(
      @AuthenticationPrincipal UserPrincipalDTO user,
      @RequestParam(defaultValue = "0") int orderPage,
      @RequestParam(defaultValue = "0") int productPage,
      @RequestParam(defaultValue = "0") int categoryPage,
      @RequestParam(defaultValue = "50") int size,
      @RequestParam(defaultValue = "categories") String tab,
      Model model) {
    model.addAttribute("createCategoryDTO", new CategoryFormDTO());
    return "admin/categories/create/index";
  }

  @PostMapping("/categories/create")
  public String updateCategoryView(
      @AuthenticationPrincipal UserPrincipalDTO user,
      @RequestParam(defaultValue = "0") int orderPage,
      @RequestParam(defaultValue = "0") int productPage,
      @RequestParam(defaultValue = "0") int categoryPage,
      @RequestParam(defaultValue = "50") int size,
      @RequestParam(defaultValue = "categories") String tab,
      @ModelAttribute CategoryFormDTO request,
      Model model) {
    createCategoryUsecase.execute(new CategoryRequestDTO(request.getName()));
    return "redirect:/admin?tab=products";
  }

  @GetMapping("/categories/{categoryId}/edit")
  public String updateCategory(
      @PathVariable UUID categoryId,
      @RequestParam(defaultValue = "0") int orderPage,
      @RequestParam(defaultValue = "0") int productPage,
      @RequestParam(defaultValue = "0") int categoryPage,
      @RequestParam(defaultValue = "50") int size,
      @RequestParam(defaultValue = "categories") String tab,
      Model model) {
    Category category = databaseCategoryRepository.findById(categoryId).orElseThrow();
    model.addAttribute("category", category);
    return "admin/categories/edit/index";
  }

  @PostMapping("/categories/{categoryId}/delete")
  public String deleteCategory(
      @RequestParam(defaultValue = "0") int orderPage,
      @RequestParam(defaultValue = "0") int productPage,
      @RequestParam(defaultValue = "0") int categoryPage,
      @RequestParam(defaultValue = "50") int size,
      @RequestParam(defaultValue = "categories") String tab,
      @PathVariable UUID categoryId,
      @AuthenticationPrincipal UserPrincipalDTO user,
      Model model) {
    deleteCategoryUsecase.execute(categoryId);
    return "redirect:/admin?tab=products";
  }

  @PostMapping("/categories/{categoryId}/edit")
  public String updateCategory(
      @PathVariable UUID categoryId,
      @RequestParam(defaultValue = "0") int orderPage,
      @RequestParam(defaultValue = "0") int productPage,
      @RequestParam(defaultValue = "0") int categoryPage,
      @RequestParam(defaultValue = "50") int size,
      @RequestParam(defaultValue = "categories") String tab,
      Model model,
      @ModelAttribute UpdateProductRequestDTO request) {
    updateCategoryUsecase.execute(categoryId, new CategoryRequestDTO(request.name()));
    return "redirect:/admin?tab=products";
  }

  @GetMapping("/products/create")
  public String createProductView(
      @AuthenticationPrincipal UserPrincipalDTO user,
      @RequestParam(defaultValue = "0") int orderPage,
      @RequestParam(defaultValue = "0") int productPage,
      @RequestParam(defaultValue = "0") int categoryPage,
      @RequestParam(defaultValue = "50") int size,
      @RequestParam(defaultValue = "products") String tab,
      Model model) {
    model.addAttribute("addressDTO", new CreateProductFormDTO());
    return "redirect:/admin?tab=products";
  }

  @PostMapping("/products/create")
  public String createProduct(
      @AuthenticationPrincipal UserPrincipalDTO user,
      @RequestParam(defaultValue = "0") int orderPage,
      @RequestParam(defaultValue = "0") int productPage,
      @RequestParam(defaultValue = "0") int categoryPage,
      @RequestParam(defaultValue = "50") int size,
      @RequestParam(defaultValue = "products") String tab,
      @ModelAttribute CreateProductFormDTO request,
      Model model) {
    createProductUsecase.execute(request.toRequest(user.id()));
    return "redirect:/admin?tab=products";
  }

  @PostMapping("/products/{productId}/edit")
  public String updateProduct(
      @PathVariable UUID productId,
      @RequestParam(defaultValue = "0") int orderPage,
      @RequestParam(defaultValue = "0") int productPage,
      @RequestParam(defaultValue = "0") int categoryPage,
      @RequestParam(defaultValue = "50") int size,
      @RequestParam(defaultValue = "products") String tab,
      Model model,
      @ModelAttribute UpdateProductRequestDTO request) {
    updateProductUsecase.execute(productId, request);
    return "redirect:/admin?tab=products";
  }

  @PostMapping("/products/{productId}/delete")
  public String showAddAddressForm(
      @RequestParam(defaultValue = "0") int orderPage,
      @RequestParam(defaultValue = "0") int productPage,
      @RequestParam(defaultValue = "0") int categoryPage,
      @RequestParam(defaultValue = "50") int size,
      @RequestParam(defaultValue = "products") String tab,
      @PathVariable UUID productId,
      @AuthenticationPrincipal UserPrincipalDTO user,
      Model model) {
    deleteProductUsecase.execute(new DeleteProductRequestDTO(productId, user.id()));
    return "redirect:/admin?tab=products";
  }

  @GetMapping("/orders/{orderId}/view")
  public String viewOrderDetail(@PathVariable UUID orderId, Model model) {
    var order = getUserOrderUsecase.execute(orderId);
    if (order == null) {
      return "redirect:/admin?tab=orders";
    }

    model.addAttribute("order", order);
    return "admin/orders/view/index";
  }

  @PatchMapping("/orders/{orderId}/ship")
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

  @PatchMapping("/orders/{orderId}/cancel")
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

  @PatchMapping("/orders/{orderId}/complete")
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
