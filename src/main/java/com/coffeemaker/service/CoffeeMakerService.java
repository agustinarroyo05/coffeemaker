package com.coffeemaker.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coffeemaker.domain.Machine;
import com.coffeemaker.domain.Product;
import com.coffeemaker.domain.exceptions.ProductStockException;

@Service
public class CoffeeMakerService {
	
	@Autowired
	private Machine machine;
	
	public List<Product> getAvailableProducts(){
		return machine.getAvailableProducts();
	}
	
	public void buyProducts(Collection<String> products)throws ProductStockException{
		machine.buyProducts(products);
	}

}
