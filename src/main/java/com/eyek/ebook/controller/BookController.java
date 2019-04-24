package com.eyek.ebook.controller;

import com.eyek.ebook.model.Book;
import com.eyek.ebook.repository.BookRepository;
import com.eyek.ebook.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api")
@RestController
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/book")
    public Book getBook(@RequestParam int id) {
        Book book = bookRepository.findById(id).get();
        System.out.println(book.getTitle());
        System.out.println(book.getAuthor());
        return book;
    }

    @PostMapping("/book")
    public Message addBook(@RequestBody @Valid Book book) {
        System.out.println(book.getTitle());
        bookRepository.save(book);
        return new Message("OK", null);
    }

    @PutMapping("/book")
    public Message modifyBook(@RequestBody @Valid Book book) {
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
    public Page<Book> getBooks(
            @RequestParam(required = false) String bookTitle,
            @RequestParam(defaultValue = "0") int pageNumber) {
        if(pageNumber > 0)
            pageNumber -= 1;
        Pageable pageable = PageRequest.of(pageNumber, 10);
        if(bookTitle == null)
            return bookRepository.findAll(pageable);
        else {
            return bookRepository.findBooksByTitleContaining(bookTitle, pageable);
        }
    }
}
