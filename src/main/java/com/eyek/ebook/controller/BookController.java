package com.eyek.ebook.controller;

import com.eyek.ebook.model.Book;
import com.eyek.ebook.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api")
@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/book/{id}")
    public Book getBook(@PathVariable("id") int id) {
        return bookService.getBook(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/book/{id}")
    public ResponseEntity<String> addBook(@PathVariable("id") Integer id, @RequestBody @Valid Book book) {
        if(bookService.addBook(book) > 0)
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/book/{id}")
    public ResponseEntity<String> modifyBook(@PathVariable("id") Integer id, @RequestBody @Valid Book book) {
        if(bookService.modifyBook(id, book)) {
            // success
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            // not found
            return new ResponseEntity<>("Book not found, check bookId.", HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/book/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable("id") Integer id) {
        if(bookService.deleteBook(id)) {
            // success
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Book not found, check id.", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/books")
    public Page<Book> getBooks(
            @RequestParam(required = false) String bookTitle,
            @RequestParam(defaultValue = "1") int pageNumber) {
        pageNumber -= 1;
        if(bookTitle == null) {
            return bookService.getAll(pageNumber);
        } else {
            return bookService.search(bookTitle, pageNumber);
        }
    }
}
