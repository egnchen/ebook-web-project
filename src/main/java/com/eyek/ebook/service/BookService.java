package com.eyek.ebook.service;

import com.eyek.ebook.model.Book;
import org.springframework.data.domain.Page;

public interface BookService {

    Book getBook(Integer id);
    Book getBook(String bookTitle);

    Integer addBook(Book book);
    Boolean modifyBook(Integer id, Book book);
    Boolean deleteBook(Integer id);

    Page<Book> getAll(Integer pageNumber, Integer pageSize);
    Page<Book> getAll(Integer pageNumber);

    Page<Book> search(String query, Integer pageNumber);
}
