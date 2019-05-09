package com.eyek.ebook.controller;

import com.eyek.ebook.model.Book;
import com.eyek.ebook.repository.BookRepository;
import com.eyek.ebook.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
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
    public Message addBook(@RequestBody @Valid Book book) {
        bookRepository.save(book);
        return new Message("OK", null);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/book")
    public Message modifyBook(@RequestBody @Valid Book book) {
        if(bookRepository.findById(book.getId()).isEmpty())
            return new Message("NOT FOUND", "Check book id.");
        bookRepository.save(book);
        return new Message("OK", null);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/book")
    public Message deleteBook(@RequestParam int bookId) {
        Book book = bookRepository.getOne(bookId);
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
        if(bookTitle == null) {
            return bookRepository.findBooksWithPic(pageable);
        }
        else {
            return bookRepository.findBooksWithPicByTitleContaining(bookTitle, pageable);
        }
    }
}
