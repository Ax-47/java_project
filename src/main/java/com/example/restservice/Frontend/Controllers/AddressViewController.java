package com.example.restservice.Frontend.Controllers;

import java.util.UUID;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.restservice.Address.dto.*;
import com.example.restservice.Address.models.AddressSortField;
import com.example.restservice.Address.usecases.*;
import com.example.restservice.Auth.dto.UserPrincipalDTO;
import com.example.restservice.Frontend.dto.AddressWebFormDTO;
import com.example.restservice.common.PageQuery;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/addresses")
public class AddressViewController {

  private final CreateAddressUsecase createAddressUsecase;
  private final DeleteAddressUsecase deleteAddressUsecase;
  private final UpdateAddressUsecase updateAddressUsecase;
  private final FindAddressUsecase findAddressUsecase;
  private final FindAddressesByUserIdUsecase findAddressesByUserIdUsecase;

  public AddressViewController(
      CreateAddressUsecase createAddressUsecase,
      FindAddressUsecase findAddressUsecase,
      DeleteAddressUsecase deleteAddressUsecase,
      UpdateAddressUsecase updateAddressUsecase,
      FindAddressesByUserIdUsecase findAddressesByUserIdUsecase) {
    this.createAddressUsecase = createAddressUsecase;
    this.findAddressesByUserIdUsecase = findAddressesByUserIdUsecase;
    this.findAddressUsecase = findAddressUsecase;
    this.updateAddressUsecase = updateAddressUsecase;
    this.deleteAddressUsecase = deleteAddressUsecase;
  }

  @GetMapping
  public String addresses(
      @AuthenticationPrincipal UserPrincipalDTO user,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "createdAt") String sortBy,
      @RequestParam(defaultValue = "true") boolean asc,
      Model model) {

    model.addAttribute("user", user);
    int pageSize = 20;
    AddressSortField sortField = AddressSortField.fromString(sortBy);
    PageQuery query = new PageQuery(page, pageSize, sortField.getFieldName(), asc);

    var addresses = findAddressesByUserIdUsecase.execute(user.id(), query);
    model.addAttribute("addresses", addresses.content());

    return "addresses/index";
  }

  @GetMapping("/create")
  public String showAddAddressForm(Model model) {
    model.addAttribute("addressDTO", new AddressWebFormDTO());
    model.addAttribute("isEditMode", false);
    return "addresses/form/index";
  }

  @GetMapping("/{id}/edit")
  public String showEditAddressForm(
      @AuthenticationPrincipal UserPrincipalDTO user, @PathVariable("id") UUID id, Model model) {

    FindAddressResponseDTO address = findAddressUsecase.execute(new FindAddressRequestDTO(id));
    if (!address.userId().equals(user.id())) {
      return "redirect:/addresses";
    }

    model.addAttribute("addressDTO", mapToFormDTO(address, id));
    model.addAttribute("isEditMode", true);

    return "addresses/form/index";
  }

  @PostMapping("/create")
  public String createAddress(
      @AuthenticationPrincipal UserPrincipalDTO user,
      @Valid @ModelAttribute("addressDTO") AddressWebFormDTO form,
      BindingResult bindingResult,
      Model model) {

    if (bindingResult.hasErrors()) {
      model.addAttribute("error", true);
      model.addAttribute("isEditMode", false);
      return "addresses/form/index";
    }

    try {
      createAddressUsecase.execute(user.id(), mapToCreateRequest(form));
      return "redirect:/addresses?success=true";
    } catch (IllegalArgumentException e) {
      handleValidationError(e, bindingResult, model);
      model.addAttribute("isEditMode", false);
      return "addresses/form/index";
    }
  }

  @PostMapping("/{id}/edit")
  public String updateAddress(
      @PathVariable("id") UUID id,
      @AuthenticationPrincipal UserPrincipalDTO user,
      @Valid @ModelAttribute("addressDTO") AddressWebFormDTO form,
      BindingResult bindingResult,
      Model model) {

    if (bindingResult.hasErrors()) {
      model.addAttribute("error", true);
      model.addAttribute("isEditMode", true);
      return "addresses/form/index";
    }

    try {
      form.setId(id);
      updateAddressUsecase.execute(mapToUpdateRequest(form));
      return "redirect:/addresses?update_success=true";
    } catch (IllegalArgumentException e) {
      handleValidationError(e, bindingResult, model);
      model.addAttribute("isEditMode", true);
      return "addresses/form/index";
    }
  }

  @DeleteMapping("/{id}/delete")
  public String deleteAddress(
      @PathVariable("id") UUID id, @AuthenticationPrincipal UserPrincipalDTO user) {
    try {
      deleteAddressUsecase.execute(new DeleteAddressRequestDTO(id, user.id()));
      return "redirect:/addresses?delete_success=true";
    } catch (Exception e) {
      return "redirect:/addresses?error_delete=true";
    }
  }

  private AddressWebFormDTO mapToFormDTO(FindAddressResponseDTO address, UUID id) {
    return new AddressWebFormDTO()
        .setId(id)
        .setFullName(address.fullName())
        .setPhoneNumber(address.phoneNumber())
        .setAddressLine1(address.addressLine1())
        .setAddressLine2(address.addressLine2())
        .setSubDistrict(address.subDistrict())
        .setDistrict(address.district())
        .setProvince(address.province())
        .setPostalCode(address.postalCode())
        .setCountry(address.country())
        .setLabel(address.label())
        .setIsDefault(address.isDefault());
  }

  private CreateAddressRequestDTO mapToCreateRequest(AddressWebFormDTO form) {
    return new CreateAddressRequestDTO(
        form.getFullName(),
        form.getPhoneNumber(),
        form.getAddressLine1(),
        form.getAddressLine2(),
        form.getSubDistrict(),
        form.getDistrict(),
        form.getProvince(),
        form.getPostalCode(),
        form.getLabel(),
        form.getCountry(),
        form.getIsDefault());
  }

  private UpdateAddressRequestDTO mapToUpdateRequest(AddressWebFormDTO form) {
    return new UpdateAddressRequestDTO(
        form.getId(),
        form.getFullName(),
        form.getPhoneNumber(),
        form.getAddressLine1(),
        form.getAddressLine2(),
        form.getSubDistrict(),
        form.getDistrict(),
        form.getProvince(),
        form.getPostalCode(),
        form.getLabel(),
        form.getCountry(),
        form.getIsDefault());
  }

  private void handleValidationError(
      IllegalArgumentException e, BindingResult bindingResult, Model model) {
    model.addAttribute("error", true);
    if (e.getMessage() != null
        && (e.getMessage().contains("phone") || e.getMessage().contains("เบอร์"))) {
      bindingResult.rejectValue("phoneNumber", "invalid.phone", e.getMessage());
    } else {
      model.addAttribute("globalErrorMessage", e.getMessage());
    }
  }
}
