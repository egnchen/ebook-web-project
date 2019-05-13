package com.eyek.ebook.controller;

import com.eyek.ebook.model.Book;
import com.eyek.ebook.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api")
@RestController
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/book")
    public Book getBook(@RequestParam int bookId) {
        Book book = bookRepository.findById(bookId).get();
        return book;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/book")
    public ResponseEntity addBook(@RequestBody @Valid Book book) {
        bookRepository.save(book);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/book")
    public ResponseEntity<String> modifyBook(@RequestBody @Valid Book book) {
        if(bookRepository.findById(book.getId()).isEmpty())
            return new ResponseEntity<>("Book not found, check bookId.", HttpStatus.NOT_FOUND);
        bookRepository.save(book);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/book")
    public ResponseEntity<String> deleteBook(@RequestParam int bookId) {
        Book book = bookRepository.getOne(bookId);
        if(book == null) return new ResponseEntity<>("Book not found, check bookId.", HttpStatus.NOT_FOUND);
        bookRepository.delete(book);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/books")
    public Page<Book> getBooks(
            @RequestParam(required = false) String bookTitle,
            @RequestParam(defaultValue = "0") int pageNumber) {
        if(pageNumber > 0)
            pageNumber -= 1;
        Pageable pageable = PageRequest.of(pageNumber, 10);
        if(bookTitle == null) {
            return bookRepository.findBooksWithPic(pageable);
        }
        else {
            return bookRepository.findBooksWithPicByTitleContaining(bookTitle, pageable);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/test")
    public ResponseEntity testRequest() {
        return new ResponseEntity(HttpStatus.OK);
    }
}
