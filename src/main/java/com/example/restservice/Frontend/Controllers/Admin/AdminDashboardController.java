package com.example.restservice.Frontend.Controllers.Admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.restservice.Categories.domain.CategorySortField;
import com.example.restservice.Categories.domain.DatabaseCategoryRepository;
import com.example.restservice.Orders.domain.OrderSortField;
import com.example.restservice.Orders.usecases.GetOrdersUsecase;
import com.example.restservice.Products.domain.DatabaseProductRepository;
import com.example.restservice.Products.domain.ProductSortField;
import com.example.restservice.common.PageQuery;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

  private final DatabaseProductRepository databaseProductRepository;
  private final DatabaseCategoryRepository databaseCategoryRepository;
  private final GetOrdersUsecase getOrdersUsecase;

  public AdminDashboardController(
      DatabaseProductRepository databaseProductRepository,
      DatabaseCategoryRepository databaseCategoryRepository,
      GetOrdersUsecase getOrdersUsecase) {
    this.databaseProductRepository = databaseProductRepository;
    this.databaseCategoryRepository = databaseCategoryRepository;
    this.getOrdersUsecase = getOrdersUsecase;
  }

  @GetMapping
  public String dashboard(
      @RequestParam(defaultValue = "0") int orderPage,
      @RequestParam(defaultValue = "0") int productPage,
      @RequestParam(defaultValue = "0") int categoryPage,
      @RequestParam(defaultValue = "50") int size,
      @RequestParam(defaultValue = "orders") String tab,
      @RequestParam(defaultValue = "status") String sortBy,
      @RequestParam(defaultValue = "desc") String sortDir,
      Model model) {
    AdminTab currentTab = AdminTab.fromString(tab);
    boolean sortDirection = sortDir.equalsIgnoreCase("asc");
    List<?> orderContent = List.of();
    List<?> productContent = List.of();
    List<?> categoryContent = List.of();
    int orderTotalPages = 0;
    int productTotalPages = 0;
    int categoryTotalPages = 0;
    if (currentTab == AdminTab.PRODUCTS) {
      String productSortColumn = validateProductSort(sortBy);
      PageQuery productQuery = new PageQuery(productPage, size, productSortColumn, sortDirection);
      var products = databaseProductRepository.findAllProducts(productQuery);

      productContent = products.content();
      productTotalPages = products.totalPages();

    } else if (currentTab == AdminTab.CATEGORIES) {
      String categorySortColumn = validateCategorySort(sortBy);
      PageQuery categoryQuery =
          new PageQuery(categoryPage, size, categorySortColumn, sortDirection);
      var categories = databaseCategoryRepository.findAllCategories(categoryQuery);

      categoryContent = categories.content();
      categoryTotalPages = categories.totalPages();

    } else {
      String orderSortColumn = validateOrderSort(sortBy);
      PageQuery orderQuery = new PageQuery(orderPage, size, orderSortColumn, sortDirection);
      var orders = getOrdersUsecase.execute(orderQuery);

      orderContent = orders.content();
      orderTotalPages = orders.totalPages();
    }
    model.addAttribute("orders", orderContent);
    model.addAttribute("products", productContent);
    model.addAttribute("categories", categoryContent);

    model.addAttribute("orderPage", orderPage);
    model.addAttribute("productPage", productPage);
    model.addAttribute("categoryPage", categoryPage);

    model.addAttribute("orderTotal", orderTotalPages);
    model.addAttribute("productTotal", productTotalPages);
    model.addAttribute("categoryTotal", categoryTotalPages);

    model.addAttribute("activeTab", tab);
    model.addAttribute("sortBy", sortBy);
    model.addAttribute("sortDir", sortDir);

    return "admin/index";
  }

  private String validateProductSort(String sortBy) {
    try {
      return ProductSortField.valueOf(sortBy).name();
    } catch (IllegalArgumentException e) {
      return ProductSortField.createdAt.name();
    }
  }

  private String validateCategorySort(String sortBy) {
    try {
      return CategorySortField.valueOf(sortBy).name();
    } catch (IllegalArgumentException e) {
      return CategorySortField.createdAt.name();
    }
  }

  private String validateOrderSort(String sortBy) {
    try {
      return OrderSortField.valueOf(sortBy).name();
    } catch (IllegalArgumentException e) {
      return OrderSortField.createdAt.name();
    }
  }
}
