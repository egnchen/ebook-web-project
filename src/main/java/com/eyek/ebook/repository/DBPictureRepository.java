package com.eyek.ebook.repository;

import com.eyek.ebook.model.DBPicture;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DBPictureRepository extends MongoRepository<DBPicture, String> {

    DBPicture findByFilename(String filename);
    DBPicture findByRid(int rid);
}
