package com.coffeemaker.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.coffeemaker.domain.Product;
import com.coffeemaker.domain.exceptions.ProductStockException;
import com.coffeemaker.service.CoffeeMakerService;

@RestController
@RequestMapping("coffeemaker")
public class CoffeeMakerController {
	
	@Autowired
	CoffeeMakerService coffeeMakerService;

	@RequestMapping(value="/products", method = RequestMethod.GET)
	public List<Product> getAvailableProducts(){
		return coffeeMakerService.getAvailableProducts();
	}
	
	@RequestMapping(value="/products", method = RequestMethod.POST)
	public void buyProducts(@RequestBody Collection<String> products) throws ProductStockException{
		coffeeMakerService.buyProducts(products);
	}
	
	@ExceptionHandler(ProductStockException.class)
    public ResponseEntity<Object> handleCreditException() {
    	return new ResponseEntity<>("Insuficient product", HttpStatus.NOT_MODIFIED);
    }
}
