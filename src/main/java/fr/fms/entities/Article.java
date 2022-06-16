package fr.fms.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Article implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Size(min=1,max=50)
	private String description;
	
	private String brand;
	
	@DecimalMin("40")
	private double price;
	
	@ManyToOne
	private Category category;
	
	public Article(String description, String brand, double price) {
		this.description = description;
		this.setBrand(brand);
		this.setPrice(price);
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id=id;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	public Category getCategory(){
		return this.category;
	}

	@Override
	public String toString() {
		return centerString(String.valueOf(id)) + centerString(description) + centerString(brand) + centerString(String.valueOf(price)) + centerString(category.getName());
	}
	
	public static String centerString(String str) {
		if(str.length() >= 20) return str;
		String dest = "                    ";
		int deb = (20 - str.length())/2 ;
		String data = new StringBuilder(dest).replace( deb, deb + str.length(), str ).toString();
		return data;
	}
	
	
	
}
