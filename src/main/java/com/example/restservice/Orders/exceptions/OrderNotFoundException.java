package com.example.restservice.Orders.exceptions;

public class OrderNotFoundException extends RuntimeException{

  public OrderNotFoundException(String massage){
    super(massage);
  }
  
}
