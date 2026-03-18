package com.example.restservice.Frontend.Controllers.Admin;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.restservice.Categories.domain.DatabaseCategoryRepository;
import com.example.restservice.Categories.dto.CategoryRequestDTO;
import com.example.restservice.Categories.usecases.*;
import com.example.restservice.Frontend.dto.CategoryFormDTO;
import com.example.restservice.Products.dto.UpdateProductRequestDTO;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {

  private final DatabaseCategoryRepository databaseCategoryRepository;
  private final CreateCategoryUsecase createCategoryUsecase;
  private final UpdateCategoryUsecase updateCategoryUsecase;
  private final DeleteCategoryUsecase deleteCategoryUsecase;

  public AdminCategoryController(
      DatabaseCategoryRepository databaseCategoryRepository,
      CreateCategoryUsecase createCategoryUsecase,
      UpdateCategoryUsecase updateCategoryUsecase,
      DeleteCategoryUsecase deleteCategoryUsecase) {
    this.databaseCategoryRepository = databaseCategoryRepository;
    this.createCategoryUsecase = createCategoryUsecase;
    this.updateCategoryUsecase = updateCategoryUsecase;
    this.deleteCategoryUsecase = deleteCategoryUsecase;
  }

  private String redirectToAdmin(int orderPage, int productPage, int categoryPage, String tab) {
    return String.format(
        "redirect:/admin?tab=%s&orderPage=%d&productPage=%d&categoryPage=%d",
        tab, orderPage, productPage, categoryPage);
  }

  @GetMapping("/create")
  public String createCategoryView(Model model) {
    model.addAttribute("createCategoryDTO", new CategoryFormDTO());
    return "admin/categories/create/index";
  }

  @PostMapping("/create")
  public String createCategory(
      @ModelAttribute CategoryFormDTO request,
      @RequestParam(defaultValue = "0") int orderPage,
      @RequestParam(defaultValue = "0") int productPage,
      @RequestParam(defaultValue = "0") int categoryPage,
      @RequestParam(defaultValue = "categories") String tab) {

    createCategoryUsecase.execute(new CategoryRequestDTO(request.getName()));
    return redirectToAdmin(orderPage, productPage, categoryPage, tab);
  }

  @GetMapping("/{categoryId}/edit")
  public String editCategoryView(@PathVariable UUID categoryId, Model model) {
    var category = databaseCategoryRepository.findById(categoryId).orElseThrow();
    model.addAttribute("category", category);
    return "admin/categories/edit/index";
  }

  @PatchMapping("/{categoryId}/edit")
  public String updateCategory(
      @PathVariable UUID categoryId,
      @ModelAttribute UpdateProductRequestDTO request,
      @RequestParam(defaultValue = "0") int orderPage,
      @RequestParam(defaultValue = "0") int productPage,
      @RequestParam(defaultValue = "0") int categoryPage,
      @RequestParam(defaultValue = "categories") String tab) {

    updateCategoryUsecase.execute(categoryId, new CategoryRequestDTO(request.name()));
    return redirectToAdmin(orderPage, productPage, categoryPage, tab);
  }

  @DeleteMapping("/{categoryId}/delete")
  public String deleteCategory(
      @PathVariable UUID categoryId,
      @RequestParam(defaultValue = "0") int orderPage,
      @RequestParam(defaultValue = "0") int productPage,
      @RequestParam(defaultValue = "0") int categoryPage,
      @RequestParam(defaultValue = "categories") String tab) {

    deleteCategoryUsecase.execute(categoryId);
    return redirectToAdmin(orderPage, productPage, categoryPage, tab);
  }
}
