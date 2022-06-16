package fr.fms.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.fms.entities.Article;
import fr.fms.entities.Category;

public interface ArticleRepository extends JpaRepository<Article,Long>{
	public List<Article> findByBrand(String brand);
	public List<Article> findByBrandContains(String brand);
	public List<Article> findByBrandAndPrice(String brand, double price);
	public List<Article> findByBrandAndPriceGreaterThan(String brand, double price);
	public List<Article> findByCategoryId(Long categoryId);
	public List<Article> findByBrandAndDescription(String brand, String Description);
	public void deleteById(Long articleId);
	public Page<Article> findAll(Pageable pageable);
	public Page<Article> findByDescriptionContains(String description, Pageable pageable);
	public Page<Article> findByDescriptionContainsAndCategoryId(String description, Long categoryId, Pageable pageable);
	
}
