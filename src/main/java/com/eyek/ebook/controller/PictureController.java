package com.eyek.ebook.controller;

import com.eyek.ebook.model.Picture;
import com.eyek.ebook.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("/api")
@RestController
public class PictureController {

    @Autowired
    private PictureService pictureService;

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

    @PostMapping("/picture")
    public ResponseEntity<Picture> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            return new ResponseEntity<>(pictureService.save(file), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/picture")
    public ResponseEntity<String> deleteFile(@RequestParam int id) {
        pictureService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
