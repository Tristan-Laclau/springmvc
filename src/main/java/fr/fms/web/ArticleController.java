package fr.fms.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.fms.dao.ArticleRepository;
import fr.fms.dao.CategoryRepository;
import fr.fms.entities.Article;
import fr.fms.entities.Category;

@Controller
public class ArticleController {
	@Autowired
	ArticleRepository articleRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	//@RequestMapping(value="/index" , method=RequestMethod.GET)
	@GetMapping("/index")
	public String index(Model model, @RequestParam(name="page",defaultValue = "0") int page,
			@RequestParam(name="keyword" , defaultValue = "") String kw, @RequestParam(name="category", defaultValue="-1") Long catId) {
		
		Page<Article> articles;
		
		if(catId!=-1) {
			articles = articleRepository.findByDescriptionContainsAndCategoryId(kw, catId, PageRequest.of(page, 5));
		} else {
			articles = articleRepository.findByDescriptionContains(kw, PageRequest.of(page, 5));
		}
		
		model.addAttribute("listArticle",articles.getContent()); //Insertion de tous les articles dans le modèle
																//Accessible via l'attribut "listArticle"
		
		List<Category> categories = categoryRepository.findAll();
		
		model.addAttribute("listCategory", categories);
		
		model.addAttribute("keyword",kw);
		
		model.addAttribute("category",catId);
		
		model.addAttribute("pages",new int [articles.getTotalPages()]);
		
		model.addAttribute("currentPage",page);
		
		
		return "articles"; //Cette méthode retourne au dispacherServlet une vue
	}
	
	@GetMapping("/delete")
	public String delete(Long id, int page, String keyword) {
		
		articleRepository.deleteById(id);
		
		return"redirect:/index?page="+page+"&keyword="+keyword;
	}
	
	@GetMapping("/article")
	public String article(Model model) {
		model.addAttribute("article", new Article());
		return "article";
	}
	
	@GetMapping("/update")
	public String update(Long id, Model model) {
		
		model.addAttribute("article");
		return "article";
	}
	
	//@RequestMapping(value="/article" , method=RequestMethod.POST)
	@PostMapping("/save")
	public String save(@Valid Article article, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) return "article";
		articleRepository.save(article);
		
		return"redirect:/index";
	}
}
