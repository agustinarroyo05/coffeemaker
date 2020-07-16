package com.coffeemaker.domain;

import java.util.Collection;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coffeemaker.domain.exceptions.ProductStockException;

@Component
public class Machine {
	
	private static int INITIAL_WATER_VALUE = 1000; 
	
	private int water = INITIAL_WATER_VALUE;
	
	@Autowired
	Map<String, Product> products; 
	
	public List<Product> getAvailableProducts(){
		return products.values().stream().filter(p->p.isAvailable(water)).collect(Collectors.toList());	
	}
	
	public void buyProducts(Collection<String> nameProductsToBuy) throws ProductStockException{
		
		IntSummaryStatistics sum;
		Collection<Product> productsToBuy = getProducts(nameProductsToBuy);
		
		try {
			productsToBuy.forEach(p-> {
				if (!p.isAvailable(water))
					throw new RuntimeException("Not enough " + p.getName() + " to make the coffee");
			});
		}
		catch(RuntimeException re) {
			throw new ProductStockException(re.getMessage());
		}
		Stream<Product> prodToCollect = productsToBuy.stream(); 
		productsToBuy.stream().forEach(p-> {
			p.buy();
		});
		sum = prodToCollect.collect(Collectors.summarizingInt(p-> p.getWaterNeeded()));
		water -= sum.getSum();		
	}
	
	private Collection<Product> getProducts(Collection<String> productNames){
		return products.values().stream()
				.filter(p -> productNames.contains(p.getName()))
				.collect(Collectors.toList());
	}

}
