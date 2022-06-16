package fr.fms.business;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import fr.fms.entities.Article;
import fr.fms.entities.Category;

public interface IBusiness {

	public List<Article> getAllArticles();
	public void createArticle(String description, String brand, double price);
	public void deleteArticleById(Long id);
	public void updateArticle(Long id, String description, String brand, double price, String catName);
	Page<Article> getArticlesPages(Pageable pageable) throws Exception;
	
	public List<Category> getAllCategories();
	public void createCategory(String name);
	public void deleteCategoryById(Long id);
	public void updateCategory(Long id, String name);
}
