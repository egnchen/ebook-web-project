package com.eyek.ebook.controller;

import com.eyek.ebook.repository.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class PictureController {

    @Autowired
    PictureRepository pictureRepository;

    // This is for database seeding purposes
//    @GetMapping("/picture/fill")
//    public ResponseEntity<String> fillDatabase() throws Exception{
//        MessageDigest md = MessageDigest.getInstance("SHA-1");
//        for (Book book: bookRepository.findAll()){
//            Picture picture = new Picture();
//            picture.setName(book.getTitle());
//            String sha1digest = new String(
//                    Hex.encode(md.digest(book.getTitle().getBytes(Charset.forName("UTF-8")))));
//            picture.setPath("images/" + sha1digest + ".jpeg");
//            picture.setSize(new PictureSize(100, 180).toString());
//            pictureRepository.save(picture);
//            book.setPicture(picture);
//            bookRepository.save(book);
//        }
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
