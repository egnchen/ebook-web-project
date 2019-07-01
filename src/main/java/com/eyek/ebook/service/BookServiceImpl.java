package com.eyek.ebook.service;

import com.eyek.ebook.dao.BookDao;
import com.eyek.ebook.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    @Value("${ebook.paging.pageSize}")
    private Integer defaultPageSize;

    private BookDao bookDao;

    @Autowired
    private PictureService pictureService;

    @Autowired
    BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public Book getBook(Integer id) {
        return bookDao.getOne(id);
    }

    @Override
    public Book getBook(String bookTitle) {
        return bookDao.getOne(bookTitle);
    }

    @Override
    public Integer addBook(Book book) {
        if(book.getPicture() != null) {
            if(book.getPicture().getId() > 0)
                book.setPicture(pictureService.getEntryById(book.getPicture().getId()));
        }
        return bookDao.addOne(book);
    }

    @Override
    public Boolean modifyBook(Integer id, Book book) {
        return bookDao.modifyOne(id, book);
    }

    @Override
    public Boolean deleteBook(Integer id) {
        return bookDao.delOne(id);
    }

    @Override
    public Page<Book> getAll(Integer pageNumber, Integer pageSize) {
        return bookDao.getAll(pageNumber, pageSize);
    }

    @Override
    public Page<Book> getAll(Integer pageNumber) {
        return bookDao.getAll(pageNumber, defaultPageSize);
    }

    @Override
    public Page<Book> search(String query, Integer pageNumber) {
        return bookDao.search(query, pageNumber, defaultPageSize);
    }
}
