package com.eyek.ebook.controller;

import com.eyek.ebook.model.Book;
import com.eyek.ebook.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @RequestMapping(value = "/addBook", method = RequestMethod.POST)
    public String addBook(
            @RequestParam String title,
            @RequestParam String author,
            @RequestParam String publisher
    ) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setISBN(new BigDecimal(1234567890));
        book.setDescription("Book description empty.");
        bookRepository.save(book);
        return "saved";
    }

    @RequestMapping(value = "/bookList", method = RequestMethod.GET)
    public Iterable<Book> getBook() {
        return bookRepository.findAll();
    }

}
