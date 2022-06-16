package fr.fms.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.fms.dao.ArticleRepository;
import fr.fms.entities.Article;

@Controller
public class ArticleController {
	@Autowired
	ArticleRepository articleRepository;
	
	//@RequestMapping(value="/index" , method=RequestMethod.GET)
	@GetMapping("/index")
	public String index(Model model, @RequestParam(name="page",defaultValue = "0") int page,
			@RequestParam(name="keyword" , defaultValue = "") String kw) {
		Page<Article> articles = articleRepository.findByDescriptionContains(kw, PageRequest.of(page, 5));
		
		model.addAttribute("listArticle",articles.getContent()); //Insertion de tous les articles dans le modèle
													//Accessible via l'attribut "listArticle"
		model.addAttribute("keyword",kw);
		
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
	public String article() {
		return "article";
	}
	
	@PostMapping("/save")
	public String save(Model model,@ModelAttribute("Article") @Valid Article article, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) return "article";
		articleRepository.save(article);
		
		return"redirect:/index";
	}
}
