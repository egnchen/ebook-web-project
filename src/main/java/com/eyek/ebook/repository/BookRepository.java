package com.eyek.ebook.repository;

import com.eyek.ebook.model.Book;
import org.springframework.data.repository.CrudRepository;

// automatically implemented by spring
public interface BookRepository extends CrudRepository<Book, Integer> {

}
