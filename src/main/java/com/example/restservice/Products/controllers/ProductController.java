package com.example.restservice.Products.controllers;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.restservice.Auth.dto.UserPrincipalDTO;
import com.example.restservice.Categories.dto.CategoryResponseDTO;
import com.example.restservice.Images.domain.ImageResourceType;
import com.example.restservice.Images.dto.*;
import com.example.restservice.Images.usecases.*;
import com.example.restservice.ProductCategories.usecases.*;
import com.example.restservice.Products.dto.*;
import com.example.restservice.Products.models.ProductSortField;
import com.example.restservice.Products.usecases.*;
import com.example.restservice.common.PageQuery;
import com.example.restservice.common.PageResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/products")
public class ProductController {
  private final CreateProductUsecase createProductUsecase;
  private final DeleteProductUsecase deleteProductUsecase;
  private final UploadImageUsecase uploadImageUsecase;
  private final FindImageUsecase findImageUsecase;
  private final ReorderImageUsecase reorderImageUsecase;
  private final DeleteImageUsecase deleteImageUsecase;
  private final FindProductsUsecase findProductsUsecase;
  private final FindProductUsecase findProductUsecase;
  private final UpdateProductUsecase updateProductUsecase;
  private final AddProductCategoriesUsecase addProductCategoriesUsecase;
  private final RemoveProductCategoryUsecase removeProductCategoryUsecase;
  private final FindProductCategoriesUsecase findProductCategoriesUsecase;
  private final PurchaseProductUsecase purchaseProductUsecase;

  public ProductController(
      CreateProductUsecase createProductUsecase,
      DeleteProductUsecase deleteProductUsecase,
      UploadImageUsecase uploadImageUsecase,
      FindImageUsecase findImageUsecase,
      DeleteImageUsecase deleteImageUsecase,
      FindProductsUsecase findProductsUsecase,
      FindProductUsecase findProductUsecase,
      UpdateProductUsecase updateProductUsecase,
      AddProductCategoriesUsecase addProductCategoriesUsecase,
      RemoveProductCategoryUsecase removeProductCategoryUsecase,
      FindProductCategoriesUsecase findProductCategoriesUsecase,
      PurchaseProductUsecase purchaseProductUsecase,
      ReorderImageUsecase reorderImageUsecase) {

    this.createProductUsecase = createProductUsecase;
    this.deleteProductUsecase = deleteProductUsecase;
    this.deleteImageUsecase = deleteImageUsecase;
    this.uploadImageUsecase = uploadImageUsecase;
    this.findImageUsecase = findImageUsecase;
    this.findProductUsecase = findProductUsecase;
    this.reorderImageUsecase = reorderImageUsecase;
    this.updateProductUsecase = updateProductUsecase;
    this.findProductsUsecase = findProductsUsecase;
    this.addProductCategoriesUsecase = addProductCategoriesUsecase;
    this.removeProductCategoryUsecase = removeProductCategoryUsecase;
    this.findProductCategoriesUsecase = findProductCategoriesUsecase;
    this.purchaseProductUsecase = purchaseProductUsecase;
  }

  // HELLLOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO
  @PostMapping
  public ResponseEntity<CreateProductResponseDTO> create(
      @Valid @RequestBody CreateProductRequestDTO requestModel) {
    CreateProductResponseDTO response = createProductUsecase.execute(requestModel);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping
  public ResponseEntity<DeleteProductResponseDTO> delete(
      @Valid @RequestBody DeleteProductRequestDTO requestModel) {
    DeleteProductResponseDTO response = deleteProductUsecase.execute(requestModel);
    return ResponseEntity.ok(response);
  }

  @PostMapping(value = "/{productId}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public UploadImageResponseDTO uploadProductImage(
      @PathVariable UUID productId, @RequestParam MultipartFile file, @RequestParam int sortOrder)
      throws IOException {

    return uploadImageUsecase.execute(file, productId, ImageResourceType.PRODUCT, sortOrder);
  }

  @GetMapping("/{productId}/images")
  public List<UploadImageResponseDTO> findProductImages(@PathVariable UUID productId) {
    return findImageUsecase.execute(productId, ImageResourceType.PRODUCT);
  }

  @PatchMapping("/{productId}/images/reorder")
  public void reorderProductImages(
      @PathVariable UUID productId, @RequestBody List<ReorderImageRequestDTO> request) {

    reorderImageUsecase.execute(productId, request);
  }

  @DeleteMapping("/{productId}/images/{imageId}")
  public void deleteProductImage(@PathVariable UUID productId, @PathVariable UUID imageId) {

    deleteImageUsecase.execute(productId, imageId);
  }

  @GetMapping
  public ResponseEntity<PageResponse<ProductResponseDTO>> findAllProducts(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "productName") ProductSortField sortBy,
      @RequestParam(defaultValue = "true") boolean asc) {

    PageQuery query = new PageQuery(page, size, sortBy.name(), asc);
    return ResponseEntity.ok(PageResponse.from(findProductsUsecase.execute(query)));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductResponseDTO> findById(@PathVariable UUID id) {
    return ResponseEntity.ok(findProductUsecase.execute(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProductResponseDTO> putById(
      @PathVariable UUID id, @Valid @RequestBody UpdateProductRequestDTO request) {
    return ResponseEntity.ok(updateProductUsecase.execute(id, request));
  }

  @PostMapping("/{productId}/categories/{categoryId}")
  public void addCategory(@PathVariable UUID productId, @PathVariable UUID categoryId) {
    addProductCategoriesUsecase.execute(productId, categoryId);
  }

  @DeleteMapping("/{productId}/categories/{categoryId}")
  public ResponseEntity<Void> removeCategory(
      @PathVariable UUID productId, @PathVariable UUID categoryId) {
    removeProductCategoryUsecase.execute(productId, categoryId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{productId}/categories")
  public List<CategoryResponseDTO> getCategories(@PathVariable UUID productId) {

    return findProductCategoriesUsecase.execute(productId);
  }

  // POST /api/products/{productId}/purchase
  @PostMapping("/api/products/{productId}/purchase")
  public ResponseEntity<Void> purchase(
      @PathVariable UUID productId, @AuthenticationPrincipal UserPrincipalDTO user) {

    // purchaseProductUsecase.execute(user.id(), productId);

    return ResponseEntity.ok().build();
  }
}
