package com.eyek.ebook.dao;

import com.eyek.ebook.model.Book;
import org.springframework.data.domain.Page;

public interface BookDao {

    Book getOne(Integer id);
    Book getOne(String tile);

    Page<Book> search(String title, Integer pageNumber);
    Page<Book> search(String title, Integer pageNumber, Integer pageSize);

    Book getOneWithDetail(Integer id);
    Book getOneWithDetail(String title);

    Page<Book> searchWithDetail(String title, Integer pageNumber);
    Page<Book> searchWithDetail(String title, Integer pageNumber, Integer pageSize);

}
