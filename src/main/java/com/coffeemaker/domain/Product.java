package com.coffeemaker.domain;

public class Product implements Selleable{
	private String name;
	private ProductFamily productFamily;
	private int stock;
	private int rawMatPerUnit;
	private float price;	
	private int waterNeeded = 0;
	
	Product(){
	}
	
	public enum ProductFamily {
		COFFEE,
		MILK,
		CHOCOLATE,
		COCOA,
		RON
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public int getRawMatPerUnit() {
		return rawMatPerUnit;
	}
	public void setRawMatPerUnit(int rawMatPerUnit) {
		this.rawMatPerUnit = rawMatPerUnit;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	
	public boolean isAvailable(int availableWater) {
		return rawMatPerUnit <= stock && availableWater >= waterNeeded;
	}
	
	public ProductFamily getProductFamily() {
		return productFamily;
	}
	public void setProductFamily(ProductFamily productFamily) {
		this.productFamily = productFamily;
	}
	public int getWaterNeeded() {
		return waterNeeded;
	}
	public void setWaterNeeded(int waterNeeded) {
		this.waterNeeded = waterNeeded;
	}
	@Override
	public void buy() {
		stock -= rawMatPerUnit;
	}
	

}
