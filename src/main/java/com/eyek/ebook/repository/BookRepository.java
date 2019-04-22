package com.eyek.ebook.repository;

import com.eyek.ebook.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {

    Page<Book> findBooksByTitleContaining(String title, Pageable pageable);
    Book findBookByTitle(String title);
}
