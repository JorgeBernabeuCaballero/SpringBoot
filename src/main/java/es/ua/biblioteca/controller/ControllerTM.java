package es.ua.biblioteca.controller;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.ua.biblioteca.service.WikidataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.ua.biblioteca.model.Book;
import es.ua.biblioteca.service.IBookService;

@Controller
public class ControllerTM {
	
	@Autowired
    private IBookService bookService;

	@Autowired
	private WikidataService wikidataService;
	
	@RequestMapping("/books")
	public String libros(Model modelo) {
		
		List<Book> books = bookService.findAll();

		modelo.addAttribute("books", books);
		return "biblioteca";
	}
	
	@RequestMapping("/")
	public String hola(Model modelo) {

		modelo.addAttribute("mensaje", "Ejemplo biblioteca en Spring Boot");
		return "index";
	}
	
	@RequestMapping("/createBook")
	public String createBook(Model model) {
		model.addAttribute("book", new Book());
	    return "form";
	}
	
	@RequestMapping("/searchBook")
	public String searchBook(@RequestParam(value = "texto", required = false) String texto, Model model) {

		// añadir servicio de busqueda llamada y logica para mostrar los resultados en el formulario
		model.addAttribute("libros", bookService.search(texto));

		return "searchForm";
	}
	@RequestMapping("/searchAuthor")
	public String searchAuthor(@RequestParam(value = "texto", required = false) String texto, Model model) {

		// añadir servicio de busqueda llamada y logica para mostrar los resultados en el formulario
		model.addAttribute("libros", bookService.searchAuthor(texto));

		return "searchAuthor";
	}
	@PostMapping("/createBook")
	public String createBook(@ModelAttribute Book book, Model model) {
		String result = bookService.create(book);
	    model.addAttribute("book", book);
	    model.addAttribute("result", result);
	    return "result";
	}

	@RequestMapping("/wikidatabvmc")
	public String searchAuthor(Model model) {
		model.addAttribute("book", new Book());
		return "wikidataSearch";
	}

	@PostMapping ("/wikidatabvmc")
	public String searchAuthorPost(@RequestParam(value = "author", required = false) String author, Model model) {
		String booksJson = wikidataService.getBooks(author);

		ObjectMapper objectMapper = new ObjectMapper();
		Object libros = null;

		try {
			System.out.println("Pasa por el POST de busqueda del libro");
			libros = objectMapper.readValue(booksJson, Object.class);

			System.out.println(libros);
		} catch (Exception e) {
			e.printStackTrace();
			// Manejar el error, por ejemplo, mostrando un mensaje de error en la vista
			model.addAttribute("error", "Error al procesar los datos de Wikidata.");
		}

		model.addAttribute("libros", libros);
		return "wikidataSearch";
	}
}
