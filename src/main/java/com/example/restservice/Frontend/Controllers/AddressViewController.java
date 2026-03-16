package com.example.restservice.Frontend.Controllers;

import java.util.UUID;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.restservice.Address.dto.CreateAddressRequestDTO;
import com.example.restservice.Address.dto.DeleteAddressRequestDTO;
import com.example.restservice.Address.dto.FindAddressRequestDTO;
import com.example.restservice.Address.dto.FindAddressResponseDTO;
import com.example.restservice.Address.dto.UpdateAddressRequestDTO;
import com.example.restservice.Address.models.AddressSortField;
import com.example.restservice.Address.usecases.CreateAddressUsecase;
import com.example.restservice.Address.usecases.DeleteAddressUsecase;
import com.example.restservice.Address.usecases.FindAddressUsecase;
import com.example.restservice.Address.usecases.FindAddressesByUserIdUsecase;
import com.example.restservice.Address.usecases.FindAddressesUsecase;
import com.example.restservice.Address.usecases.UpdateAddressUsecase;
import com.example.restservice.Auth.dto.UserPrincipalDTO;
import com.example.restservice.Frontend.dto.AddressWebFormDTO;
import com.example.restservice.common.PageQuery;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/addresses")
public class AddressViewController {
  private final CreateAddressUsecase createAddressUsecase;
  private final FindAddressesUsecase findAddressesUsecase;
  private final DeleteAddressUsecase deleteAddressUsecase;
  private final UpdateAddressUsecase updateAddressUsecase;
  private final FindAddressUsecase findAddressUsecase;
  private final FindAddressesByUserIdUsecase findAddressesByUserIdUsecase;

  public AddressViewController(
      CreateAddressUsecase createAddressUsecase,
      FindAddressesUsecase findAddressesUsecase,
      FindAddressUsecase findAddressUsecase,
      DeleteAddressUsecase deleteAddressUsecase,
      UpdateAddressUsecase updateAddressUsecase,
      FindAddressesByUserIdUsecase findAddressesByUserIdUsecase) {
    this.createAddressUsecase = createAddressUsecase;
    this.findAddressesByUserIdUsecase = findAddressesByUserIdUsecase;
    this.findAddressUsecase = findAddressUsecase;
    this.findAddressesUsecase = findAddressesUsecase;
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

  @GetMapping("/new")
  public String showAddAddressForm(Model model) {
    model.addAttribute("addressDTO", new AddressWebFormDTO());
    return "addresses/new";
  }

  @GetMapping("/edit/{id}")
  public String showEditAddressForm(@PathVariable("id") UUID id, Model model) {
    FindAddressResponseDTO address = findAddressUsecase.execute(new FindAddressRequestDTO(id));
    AddressWebFormDTO form = new AddressWebFormDTO();
    form.setId(id)
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
    model.addAttribute("addressDTO", form);
    return "addresses/new";
  }

  @PostMapping
  public String createAddress(
      @AuthenticationPrincipal UserPrincipalDTO user,
      @Valid @ModelAttribute("addressDTO") AddressWebFormDTO form,
      BindingResult bindingResult,
      Model model) {

    if (bindingResult.hasErrors()) {
      model.addAttribute("error", bindingResult.hasErrors());
      return "addresses/new";
    }
    try {
      if (form.getId() == null) {
        CreateAddressRequestDTO requestModel =
            new CreateAddressRequestDTO(
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

        createAddressUsecase.execute(user.id(), requestModel);
      } else {
        var requestModel =
            new UpdateAddressRequestDTO(
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
        updateAddressUsecase.execute(requestModel);
      }

      return "redirect:/addresses";

    } catch (IllegalArgumentException e) {
      model.addAttribute("error", true);
      if (e.getMessage().contains("phone") || e.getMessage().contains("เบอร์")) {
        bindingResult.rejectValue("phoneNumber", "invalid.phone", e.getMessage());
      } else {
        model.addAttribute("globalErrorMessage", e.getMessage());
      }

      return "addresses/form";
    }
  }

  @PostMapping("/delete/{id}")
  public String deleteAddress(
      @PathVariable("id") UUID id, @AuthenticationPrincipal UserPrincipalDTO user) {
    try {
      deleteAddressUsecase.execute(new DeleteAddressRequestDTO(id, user.id()));
      return "redirect:/addresses?delete_success";
    } catch (Exception e) {
      return "redirect:/addresses?error_delete";
    }
  }
}
