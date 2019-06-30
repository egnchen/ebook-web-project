package com.eyek.ebook.dao;

import com.eyek.ebook.model.Book;
import org.springframework.data.domain.Page;

public interface BookDao {

    Book getOne(Integer id);
    Book getOne(String tile);

    Integer addOne(Book book);
    Boolean modifyOne(Integer id, Book book);
    Boolean delOne(Integer id);

    Page<Book> getAll(Integer pageNumber, Integer pageSize);
    Page<Book> search(String title, Integer pageNumber, Integer pageSize);

    Book getOneWithDetail(Integer id);
    Book getOneWithDetail(String title);

    Page<Book> searchWithDetail(String title, Integer pageNumber, Integer pageSize);
}
