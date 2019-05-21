package com.eyek.ebook.dao;

import com.eyek.ebook.model.Book;
import com.eyek.ebook.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
public abstract class BookDaoImpl implements BookDao {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book getOne(Integer id) {
        return bookRepository.findById(id).get();
    }

    @Override
    public Book getOne(String title) {
        return bookRepository.findBookByTitle(title);
    }

    @Override
    public Integer addOne(Book book) {
        return bookRepository.save(book).getId();
    }

    @Override
    public Boolean modifyOne(Integer id, Book book) {
        if(bookRepository.existsById(id)) {
            bookRepository.save(book);
            return true;
        }
        return false;
    }

    @Override
    public Boolean delOne(Integer id) {
        if(bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Page<Book> getAll(Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return bookRepository.findAll(pageRequest);
    }

    @Override
    public Page<Book> search(String title, Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return bookRepository.findBooksByTitleContaining(title, pageRequest);
    }

    @Override
    public Book getOneWithDetail(Integer id) {
        return bookRepository.getOne(id);
    }

    @Override
    public Book getOneWithDetail(String title) {
        return bookRepository.findBookByTitle(title);
    }

    @Override
    public Page<Book> searchWithDetail(String title, Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return bookRepository.findBooksWithPicByTitleContaining(title, pageRequest);
    }
}
