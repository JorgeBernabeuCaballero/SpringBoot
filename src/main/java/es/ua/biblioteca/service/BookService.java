package es.ua.biblioteca.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.ua.biblioteca.model.Book;
import es.ua.biblioteca.repository.BookRepository;

@Service
public class BookService implements IBookService {

    @Autowired
    private BookRepository repository;

    @Override
    public List<Book> findAll() {
        return (List<Book>) repository.findAll();
    }

	@Override
	public Optional<Book> findById(long id) {
		return repository.findById(id);
	}
	
	@Override
	public String create(Book book) {
		Book b = repository.save(book);
		return "book created:" + b.toString();
	}
	
	@Override
	public String delete(long id) {
		Optional<Book> book = repository.findById(id);
		
		if(book.isPresent())
			repository.delete(book.get());
		return "book deleted";
	}
	
	@Override
	public String update(Book book) {
		if (book.getId() != 0) {
			Book b = repository.save(book);
			return "Book updated:" + b.toString();
		}else
			return "No id provided for the book"; 
	}
	
	@Override
	public List<Book> search(String title) {
		return repository.searchTitle(title);
	}

	@Override
	public List<Book> searchAuthor(String Author) {
		return repository.searchAuthor(Author);
	}
}
