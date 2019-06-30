package com.eyek.ebook.service;

import com.eyek.ebook.model.Book;
import com.eyek.ebook.model.DBPicture;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PictureService {
    String save(MultipartFile file) throws IOException;
    DBPicture get(String filename);
    DBPicture getById(int id);
    DBPicture getByBook(Book book);
    void remove(int id);
}
