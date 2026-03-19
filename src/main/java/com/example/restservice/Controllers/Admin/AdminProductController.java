package com.example.restservice.Controllers.Admin;

import java.util.UUID;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.restservice.Auth.dto.UserPrincipalDTO;
import com.example.restservice.Frontend.dto.CreateProductFormDTO;
import com.example.restservice.Products.dto.DeleteProductRequestDTO;
import com.example.restservice.Products.dto.UpdateProductRequestDTO;
import com.example.restservice.Products.usecases.*;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {

  private final FindProductForEditUsecase findProductForEditUsecase;
  private final CreateProductUsecase createProductUsecase;
  private final UpdateProductUsecase updateProductUsecase;
  private final DeleteProductUsecase deleteProductUsecase;
  private final FindProductForImagesUsecase findProductForImagesUsecase;

  public AdminProductController(
      FindProductForEditUsecase findProductForEditUsecase,
      FindProductForImagesUsecase findProductForImagesUsecase,
      CreateProductUsecase createProductUsecase,
      UpdateProductUsecase updateProductUsecase,
      DeleteProductUsecase deleteProductUsecase) {
    this.createProductUsecase = createProductUsecase;
    this.updateProductUsecase = updateProductUsecase;
    this.deleteProductUsecase = deleteProductUsecase;
    this.findProductForEditUsecase = findProductForEditUsecase;
    this.findProductForImagesUsecase = findProductForImagesUsecase;
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
    var response = findProductForEditUsecase.execute(productId);
    model.addAttribute("product", response.product());
    model.addAttribute("productCategories", response.productCategories());
    model.addAttribute("images", response.images());
    model.addAttribute("allCategories", response.allCategories());
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
    var response = findProductForImagesUsecase.execute(productId);
    model.addAttribute("product", response.product());
    model.addAttribute("images", response.images());
    return "admin/products/images/index";
  }
}
