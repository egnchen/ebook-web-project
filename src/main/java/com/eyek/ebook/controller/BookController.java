package com.eyek.ebook.controller;

import com.eyek.ebook.model.Book;
import com.eyek.ebook.repository.BookRepository;
import com.eyek.ebook.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:8081", maxAge = 3600)
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/book")
    public Book getBook(@RequestParam int id) {
        return bookRepository.findById(id).get();
    }

    @PostMapping("/book")
    public Message addBook(@RequestParam @Valid Book book) {
        bookRepository.save(book);
        return new Message("OK", null);
    }

    @PutMapping("/book")
    public Message modifyBook(@RequestParam Book book) {
        if(bookRepository.findById(book.getId()).isEmpty())
            return new Message("NOT FOUND", "Check book id.");
        bookRepository.save(book);
        return new Message("OK", null);
    }

    @DeleteMapping("/book")
    public Message deleteBook(@RequestParam int id) {
        Book book = bookRepository.findById(id).get();
        if(book == null) return new Message("NOT FOUND", "Check book id.");
        bookRepository.delete(book);
        return new Message("OK", null);
    }

    @GetMapping("/books")
    public Iterable<Book> getBooks(
            @RequestParam(required = false) String bookTitle,
            @RequestParam(defaultValue = "0") int pageNumber) {
        Pageable pageRequest = PageRequest.of(pageNumber, 10);
        if(bookTitle == null)
            return bookRepository.findAll(pageRequest).getContent();
        else {
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withMatcher("title", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
            Book bookExample = new Book();
            bookExample.setTitle(bookTitle);
            Example<Book> example = Example.of(bookExample, matcher);
            return bookRepository.findAll(example, pageRequest).getContent();
        }
    }
}
