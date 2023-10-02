/**
 * 
 */
package no.hvl.dat152.rest.ws.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import no.hvl.dat152.rest.ws.exceptions.AuthorNotFoundException;
import no.hvl.dat152.rest.ws.model.Author;
import no.hvl.dat152.rest.ws.model.Book;
import no.hvl.dat152.rest.ws.repository.AuthorRepository;

/**
 * @author tdoy
 */
@Service
public class AuthorService {

	@Autowired
	private AuthorRepository authorRepository;
	
	
	public Author saveAuthor(Author author) {
		
		return authorRepository.save(author);

	}
	
	public Author findById(long id) throws AuthorNotFoundException {
		
		Author author = authorRepository.findById(id)
				.orElseThrow(()-> new AuthorNotFoundException("Author with the id: "+id+ "not found!"));
		
		return author;
	}
	public List<Author> findAll() {

		return (List<Author>) authorRepository.findAll();
	}
	public Author findByAuthorId(long id) throws NoSuchElementException {
		Author author = null;
		try {
			author = authorRepository.findById(id).get();
		}catch(Exception e) {
			throw new NoSuchElementException("Author with id = "+id+" not found!");

		}
		return author;
	}

}
