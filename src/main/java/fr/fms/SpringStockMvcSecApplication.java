package fr.fms;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import fr.fms.business.IBusinessImpl;
import fr.fms.entities.Article;
import fr.fms.entities.Category;

@SpringBootApplication
public class SpringStockMvcSecApplication implements CommandLineRunner {

	@Autowired
	private IBusinessImpl job;


	public Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		SpringApplication.run(SpringStockMvcSecApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//mainMenu();
		
		generateValues();
	}
	
	public void generateValues() {
		Category smartphone = job.categoryRepository.save(new Category("Smartphone"));
		Category pc = job.categoryRepository.save(new Category("Ordinateur"));
		Category tablet = job.categoryRepository.save(new Category("Tablette"));
		Category printer = job.categoryRepository.save(new Category("Imprimante"));
		Category camera = job.categoryRepository.save(new Category("Camera"));
		Category tv = job.categoryRepository.save(new Category("TV"));
		
		job.articleRepository.save(new Article(null,"S8","Samsung",250,smartphone));
		job.articleRepository.save(new Article(null,"S9","Samsung", 300,smartphone));
		job.articleRepository.save(new Article(null,"iPhone 10","Apple",500,smartphone));		
		job.articleRepository.save(new Article(null,"MI11","Xiaomi",100,smartphone));
		job.articleRepository.save(new Article(null,"9 Pro","OnePlus",200,smartphone));
		job.articleRepository.save(new Article(null,"Pixel 5","Google",350,smartphone));
		job.articleRepository.save(new Article(null,"F3","Poco",150,smartphone));
		
		job.articleRepository.save(new Article(null,"810","Dell",550,pc));
		job.articleRepository.save(new Article(null,"F756","Asus",600,pc));
		job.articleRepository.save(new Article(null,"E80","Asus",700,pc));
		job.articleRepository.save(new Article(null,"Pro","MacBook",1000,pc));
		job.articleRepository.save(new Article(null,"Air","MacBook",1200,pc));
		
		job.articleRepository.save(new Article(null,"XL 5","IPad",300,tablet));
		job.articleRepository.save(new Article(null,"XL 7","IPad",500,tablet));
		
		
		job.articleRepository.save(new Article(null,"MG30","Canon",50,printer));
		job.articleRepository.save(new Article(null,"MG50","Canon",60,printer));
		job.articleRepository.save(new Article(null,"800","HP",50,printer));
		job.articleRepository.save(new Article(null,"3T","Epson",100,printer));
		
		job.articleRepository.save(new Article(null,"7","GoPro",150,camera));
		job.articleRepository.save(new Article(null,"10","GoPro",200,camera));
		
		job.articleRepository.save(new Article(null,"HT","Panasonic",1500,tv));
		job.articleRepository.save(new Article(null,"L43","Philips",450,tv));	
	}

	public void displayAllArticles() {
		System.out.println("------------------------------------------------------");
		for(Article article : job.getAllArticles()) {
			System.out.println(article);
			System.out.println("------------------------------------------------------");
		}
	}

	private void displayArticlesByPage() {
		int currentPage = 0;
		int nbPage = 5;
		boolean flag = true;
		
		menuPaginate();
		
		while(flag) {
			try {
				Page<Article> articles = job.getArticlesPages(PageRequest.of(currentPage, nbPage));						
				if(articles.getTotalPages() != 0) {
					displayTitles();
					
					articles.stream().forEach(a -> System.out.println(a));
					System.out.print("\n PREV [");
					for(int i=0 ; i<articles.getTotalPages() ; i++) {
						if(i == currentPage)	System.out.print(" {" + i + "} ");
						else System.out.print(" "+i+" ");
					}
					System.out.println("] NEXT");
					System.out.println("Tapez 0 pour Afficher le menu de pagination \n");
					
					String action = scan.next();			
					if(action.equalsIgnoreCase("PREV")) {
						if(currentPage > 0) currentPage--; 
					}
					else if	(action.equalsIgnoreCase("NEXT")) {
						if(currentPage < articles.getTotalPages()-1)	currentPage++;		
					}
					else if(action.equalsIgnoreCase("EXIT")) {
						flag = false;
					}
					else if(action.equalsIgnoreCase("PAGE")) {
						System.out.println("saisissez le nombre d'articles par page :");
						nbPage = scan.nextInt();
						currentPage = 0;
					}
					else if(action.equalsIgnoreCase("0")) {
						menuPaginate();
					}
				}
				else {
					System.out.println("TABLE VIDE EN BDD !");
					flag = false;
				}
			}
			catch(Exception e) {
				System.out.println("Erreur de communication avec la base : " + e.getMessage());
				flag = false;
			}
		}
	}

	private void menuPaginate() {
		System.out.println("EXIT    pour sortir de la pagination");
		System.out.println("PREV    pour afficher la page précédente");
		System.out.println("NEXT    pour afficher la page suivante");
		System.out.println("PAGE 7  pour afficher 7 articles par page (par défaut c'est 5)");
	}
	
	private void displayTitles() {
		String titles = "\n" + Article.centerString("IDENTIFIANT") + Article.centerString("DESCRIPTION") + Article.centerString("MARQUE") + Article.centerString("PRIX") + Article.centerString("CATEGORIE");
		System.out.println(titles);
	}
	
	public void articleMenu() {

		System.out.println("Quelle action voulez-vous effectuer?");
		System.out.println("1 : Ajouter un article");
		System.out.println("2 : Supprimer un article");
		System.out.println("3 : Mettre à jour un article");
		System.out.println("4 : Lire un article");
		System.out.println("5 : Retour au menu");


		int choice;

		while(!scan.hasNextInt()) scan.next();

		choice = scan.nextInt();

		switch (choice) {
		case 1:
			addArticle();
			break;
		case 2:
			removeArticle();
			break;
		case 3:
			updateArticle();
			break;
		case 4:
			readArticle();
			break;
		default:

			break;
		}
	}

	public void addArticle() {


		System.out.println("Entrez le nom de l'article");
		String description = scan.next();

		System.out.println("Entrez une marque");
		String brand = scan.next();

		System.out.println("Entrez un prix");
		double price = scan.nextDouble();

		job.createArticle(description,brand,price);

	}

	public void removeArticle() {

		System.out.println("Entrez l'id de l'article à supprimer");

		displayAllArticles();

		int idChoice = scan.nextInt();

		job.deleteArticleById(Long.valueOf(idChoice));
	}

	public void updateArticle() {

		System.out.println("Entrez l'id de l'article à modifier");

		displayAllArticles();

		int idArticle = scan.nextInt();

		System.out.println("Entrez le nouveau nom de l'article");
		String newDescription = scan.next();

		System.out.println("Entrez la nouvelle marque de l'article");
		String newBrand = scan.next();

		System.out.println("Entrez le nouveau prix de l'article");
		double newPrice = scan.nextDouble();

		displayAllCategories();
		System.out.println("Entrez la nouvelle catégorie de l'article");
		String newCategory = scan.next();

		job.updateArticle(Long.valueOf(idArticle), newDescription, newBrand, newPrice, newCategory);

	}

	public void readArticle() {

		System.out.println("Entrez l'ID de l'article que vous souhaitez consulter");
		int idArticle = scan.nextInt();

		for (Article article : job.getAllArticles()) {
			if(article.getId()==Long.valueOf(idArticle)) System.out.println(article);
		}
	}

	public void categoryMenu() {

		System.out.println("Quelle action voulez-vous effectuer?");
		System.out.println("1 : Ajouter une catégorie");
		System.out.println("2 : Supprimer une catégorie");
		System.out.println("3 : Mettre à jour une catégorie");
		System.out.println("4 : Lire une catégorie");
		System.out.println("5 : Retour au menu");


		int choice;

		while(!scan.hasNextInt()) scan.next();

		choice = scan.nextInt();

		switch (choice) {
		case 1:
			addCategory();
			break;
		case 2:
			removeCategory();
			break;
		case 3:
			updateCategory();
			break;
		case 4:
			readCategory();
			break;
		default:

			break;
		}
	}

	public void addCategory() {
		System.out.println("Entrez le nom de la catégorie");
		String name = scan.next();

		job.createCategory(name);
	}
	
	public void removeCategory() {

		System.out.println("Entrez l'id de la catégorie à supprimer");

		displayAllCategories();

		int idChoice = scan.nextInt();

		job.deleteCategoryById(Long.valueOf(idChoice));
	}

	public void updateCategory() {

		System.out.println("Entrez l'id de la catégorie à modifier");

		displayAllCategories();

		int idArticle = scan.nextInt();

		System.out.println("Entrez le nouveau nom de la catégorie");
		String newName = scan.next();

		job.updateCategory(Long.valueOf(idArticle), newName);

	}

	public void readCategory() {

		System.out.println("Entrez l'ID de la catégorie que vous souhaitez consulter");
		int idCategory = scan.nextInt();

		for (Category category : job.getAllCategories()) {
			if(category.getId()==Long.valueOf(idCategory)) System.out.println(category);
		}
	}


	public void displayAllCategories() {
		System.out.println("---------------------------------------------------------------------------------------------------------------");
		for(Category category : job.getAllCategories()) {
			System.out.println(category);
			System.out.println("---------------------------------------------------------------------------------------------------------------");
		}
	}

	public void displayArticlesByCategory() {
		
		System.out.println("Entrez l'ID de la catégorie que vous souhaitez consulter");
		displayAllCategories();
		int idCategory = scan.nextInt();

		for (Article article : job.getAllArticles()) {
			if(article.getCategory().getId() == Long.valueOf(idCategory)) System.out.println(article);
		}
	}

	public void mainMenu() {

		System.out.print("Bienvenue dans SpringShop! ");

		boolean leaving = false;

		while(!leaving) {

			System.out.println("Que voulez-vous faire?");
			System.out.println("1 : Afficher la liste des articles");
			System.out.println("2 : Afficher les articles par page");
			System.out.println("3 : Afficher la liste des catégories");
			System.out.println("4 : Accéder au menu des articles");
			System.out.println("5 : Accéder au menu des catégories");
			System.out.println("6 : Rechercher un article par catégorie");
			System.out.println("7 : Quitter l'application");


			int choice;

			while(!scan.hasNextInt()) scan.next();

			choice = scan.nextInt();

			switch (choice) {
			case 1:
				displayAllArticles();
				break;
			case 2:
				displayArticlesByPage();
				break;
			case 3:
				displayAllCategories();
				break;
			case 4:
				articleMenu();
				break;
			case 5:
				categoryMenu();
				break;
			case 6:
				displayArticlesByCategory();
				break;
			default:
				leaving=true;
				break;
			}
		}
		System.out.println("Ciao!");
	}





}
