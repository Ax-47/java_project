package com.example.restservice.Controllers.Admin;

import java.util.UUID;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.restservice.Auth.dto.UserPrincipalDTO;
import com.example.restservice.Categories.domain.DatabaseCategoryRepository;
import com.example.restservice.Frontend.dto.CreateProductFormDTO;
import com.example.restservice.Images.domain.ImageResourceType;
import com.example.restservice.Images.usecases.FindImageUsecase;
import com.example.restservice.ProductCategories.domain.DatabaseProductCategoryRepository;
import com.example.restservice.Products.domain.DatabaseProductRepository;
import com.example.restservice.Products.dto.DeleteProductRequestDTO;
import com.example.restservice.Products.dto.UpdateProductRequestDTO;
import com.example.restservice.Products.usecases.*;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {

  private final DatabaseProductRepository databaseProductRepository;
  private final DatabaseCategoryRepository databaseCategoryRepository;
  private final DatabaseProductCategoryRepository databaseProductCategoryRepository;
  private final FindImageUsecase findImageUsecase;
  private final CreateProductUsecase createProductUsecase;
  private final UpdateProductUsecase updateProductUsecase;
  private final DeleteProductUsecase deleteProductUsecase;

  public AdminProductController(
      DatabaseProductRepository databaseProductRepository,
      DatabaseCategoryRepository databaseCategoryRepository,
      DatabaseProductCategoryRepository databaseProductCategoryRepository,
      FindImageUsecase findImageUsecase,
      CreateProductUsecase createProductUsecase,
      UpdateProductUsecase updateProductUsecase,
      DeleteProductUsecase deleteProductUsecase) {
    this.databaseProductRepository = databaseProductRepository;
    this.databaseCategoryRepository = databaseCategoryRepository;
    this.databaseProductCategoryRepository = databaseProductCategoryRepository;
    this.findImageUsecase = findImageUsecase;
    this.createProductUsecase = createProductUsecase;
    this.updateProductUsecase = updateProductUsecase;
    this.deleteProductUsecase = deleteProductUsecase;
  }

  private String redirectToAdmin(int orderPage, int productPage, int categoryPage, String tab) {
    return String.format(
        "redirect:/admin?tab=%s&orderPage=%d&productPage=%d&categoryPage=%d",
        tab, orderPage, productPage, categoryPage);
  }

  @GetMapping("/create")
  public String createProductView(Model model) {
    model.addAttribute("productForm", new CreateProductFormDTO());
    return "admin/products/create/index";
  }

  @PostMapping("/create")
  public String createProduct(
      @AuthenticationPrincipal UserPrincipalDTO user,
      @ModelAttribute CreateProductFormDTO request,
      @RequestParam(defaultValue = "0") int orderPage,
      @RequestParam(defaultValue = "0") int productPage,
      @RequestParam(defaultValue = "0") int categoryPage,
      @RequestParam(defaultValue = "products") String tab) {

    createProductUsecase.execute(request.toRequest(user.id()));
    return redirectToAdmin(orderPage, productPage, categoryPage, tab);
  }

  @GetMapping("/{productId}/edit")
  public String editProductView(@PathVariable UUID productId, Model model) {
    var product = databaseProductRepository.findById(productId).orElseThrow();
    var allCategories = databaseCategoryRepository.findAll();
    var productCategories = databaseProductCategoryRepository.findCategoriesByProductId(productId);
    var images = findImageUsecase.execute(productId, ImageResourceType.PRODUCT);

    model.addAttribute("product", product);
    model.addAttribute("productCategories", productCategories);
    model.addAttribute("images", images);
    model.addAttribute("allCategories", allCategories);

    return "admin/products/edit/index";
  }

  @PutMapping("/{productId}/edit")
  public String updateProduct(
      @PathVariable UUID productId,
      @ModelAttribute UpdateProductRequestDTO request,
      @RequestParam(defaultValue = "0") int orderPage,
      @RequestParam(defaultValue = "0") int productPage,
      @RequestParam(defaultValue = "0") int categoryPage,
      @RequestParam(defaultValue = "products") String tab) {

    updateProductUsecase.execute(productId, request);
    return redirectToAdmin(orderPage, productPage, categoryPage, tab);
  }

  @DeleteMapping("/{productId}/delete")
  public String deleteProduct(
      @PathVariable UUID productId,
      @AuthenticationPrincipal UserPrincipalDTO user,
      @RequestParam(defaultValue = "0") int orderPage,
      @RequestParam(defaultValue = "0") int productPage,
      @RequestParam(defaultValue = "0") int categoryPage,
      @RequestParam(defaultValue = "products") String tab) {

    deleteProductUsecase.execute(new DeleteProductRequestDTO(productId, user.id()));
    return redirectToAdmin(orderPage, productPage, categoryPage, tab);
  }

  @GetMapping("/{productId}/images")
  public String manageImagesView(@PathVariable UUID productId, Model model) {
    var product = databaseProductRepository.findById(productId).orElseThrow();
    var images = findImageUsecase.execute(productId, ImageResourceType.PRODUCT);

    model.addAttribute("product", product);
    model.addAttribute("images", images);

    return "admin/products/images/index";
  }
}
