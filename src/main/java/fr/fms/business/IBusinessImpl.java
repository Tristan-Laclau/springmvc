package fr.fms.business;

import java.util.List;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import fr.fms.dao.ArticleRepository;
import fr.fms.dao.CategoryRepository;
import fr.fms.entities.Article;
import fr.fms.entities.Category;


@Service
public class IBusinessImpl implements IBusiness{
	
	@Autowired
	public ArticleRepository articleRepository;
	
	@Autowired
	public CategoryRepository categoryRepository;
	
	@Override
	@PostConstruct
	public List<Article> getAllArticles() {
		return articleRepository.findAll();
	}
	
	@Override
	public void createArticle(String description, String brand, double price) {
		articleRepository.save(new Article(description,brand,price));
	}
	
	@Override
	public void deleteArticleById(Long id) {
		articleRepository.deleteById(id);
	}
	
	@Override
	public void updateArticle(Long id, String description, String brand, double price, String catName) {
		articleRepository.save(new Article(id,description,brand,price,categoryRepository.findByName(catName)));
	}
	
	@Override
	public List<Category> getAllCategories(){
		return categoryRepository.findAll();
	}
	
	@Override
	public void createCategory(String name) {
		categoryRepository.save(new Category(name));
	}
	
	@Override
	public void deleteCategoryById(Long id) {
		categoryRepository.deleteById(id);
	}
	
	@Override
	public void updateCategory(Long id, String name) {
		categoryRepository.save(new Category(id,name));
	}
	
	@Override
	public Page<Article> getArticlesPages(Pageable pageable) throws Exception {
		return articleRepository.findAll(pageable);
	}
	

}
