package com.eyek.ebook.service;

import com.eyek.ebook.model.Book;
import com.eyek.ebook.model.DBPicture;
import com.eyek.ebook.model.Picture;
import com.eyek.ebook.repository.DBPictureRepository;
import com.eyek.ebook.repository.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PictureServiceImpl implements PictureService {

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private DBPictureRepository dbPictureRepository;

    public String save(MultipartFile file) throws IOException {
        // save in RDBMS first
        Picture picture = new Picture();
        picture.setName(file.getOriginalFilename());
        picture.setPath(null);  // indicate that it's saved in MongoDB
        picture.setThumbPath(null);
        pictureRepository.save(picture);
        // save in MongoDB next
        DBPicture dbPicture = new DBPicture();
        dbPicture.setFilename(file.getOriginalFilename());
        dbPicture.setRid(picture.getId());
        dbPicture.setContentType(file.getContentType());
        dbPicture.setContent(file.getInputStream().readAllBytes());
        dbPicture.setSize(file.getSize());
        dbPictureRepository.save(dbPicture);
        return dbPicture.getId();
    }

    public DBPicture get(String filename) {
        return dbPictureRepository.findByFilename(filename);
    }

    @Override
    public DBPicture getById(int id) {
        return dbPictureRepository.findByRid(id);
    }

    public DBPicture getByBook(Book book) {
        return get(book.getPicture().getName());
    }

    public void remove(int id) {
        pictureRepository.deleteById(id);
        dbPictureRepository.delete(dbPictureRepository.findByRid(id));
    }

}
