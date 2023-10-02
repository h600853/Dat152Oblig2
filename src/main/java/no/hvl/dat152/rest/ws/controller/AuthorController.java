/**
 * 
 */
package no.hvl.dat152.rest.ws.controller;


import no.hvl.dat152.rest.ws.model.Author;
import no.hvl.dat152.rest.ws.model.Book;
import no.hvl.dat152.rest.ws.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;


/**
 * 
 */
@RestController
@RequestMapping("/elibrary/api/v1")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping("/authors")
    public ResponseEntity<List<Author>> getAuthors()
    {

        List<Author> authors = authorService.findAll();
        if(authors.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(authors, HttpStatus.OK);

    }
    @GetMapping("/authors/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable String id) {
        Author author = authorService.findByAuthorId(Long.parseLong(id));
        if(author == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }
    @GetMapping("/authors/{id}/books")
    public ResponseEntity<Object> getAuthorBooks(@PathVariable String id) {
        Author author;
        Set<Book> books;
        try {
            author = authorService.findByAuthorId(Long.parseLong(id));
            books = author.getBooks();
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(books, HttpStatus.OK);
    }
    @PostMapping("/authors")
    public ResponseEntity<Author> createAuthor(@RequestBody Author author)
    {
        Author nauthor = authorService.saveAuthor(author);
        return new ResponseEntity<>(nauthor, HttpStatus.CREATED);
    }
    @PutMapping("/authors/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable("id") String id, @RequestBody Author author) {
        try {
            authorService.findByAuthorId(Integer.parseInt(id));
            author.setAuthorId(Integer.parseInt(id));
            authorService.saveAuthor(author);
            return new ResponseEntity<>(author, HttpStatus.OK);
        } catch (NoSuchElementException e) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}

