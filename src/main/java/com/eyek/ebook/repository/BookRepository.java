package com.eyek.ebook.repository;

import com.eyek.ebook.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Integer> {

    Page<Book> findBooksByTitleContaining(String title, Pageable pageable);
    Book findBookByTitle(String title);

    // here to avoid N+1 problem
    @Query(value = "FROM Book as b LEFT JOIN FETCH b.picture",
    countQuery = "SELECT count(b) FROM Book b")
    Page<Book> findBooksWithPic(Pageable pageable);

    @Query(value = "FROM Book as b LEFT JOIN FETCH b.picture WHERE b.title LIKE %?1%",
    countQuery = "SELECT count(b) FROM Book b WHERE b.title LIKE %?1%")
    Page<Book> findBooksWithPicByTitleContaining(String title, Pageable pageable);
}
