package com.eyek.ebook.service;

import com.eyek.ebook.model.Book;
import com.eyek.ebook.model.DBPicture;
import com.eyek.ebook.model.Picture;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PictureService {
    // return picture id
    Picture save(MultipartFile file) throws IOException;
    DBPicture get(String filename);
    Picture getEntryById(int id);
    DBPicture getById(int id);
    DBPicture getByBook(Book book);
    void remove(int id);
}
