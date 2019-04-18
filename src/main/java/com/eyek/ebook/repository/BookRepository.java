package com.eyek.ebook.repository;

import com.eyek.ebook.model.Book;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// automatically implemented by spring
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findBooksByTitleContaining(String title, PageRequest pageRequest);
    Book findBookByTitle(String title);

}
