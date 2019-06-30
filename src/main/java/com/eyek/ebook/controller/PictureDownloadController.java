package com.eyek.ebook.controller;

import com.eyek.ebook.model.DBPicture;
import com.eyek.ebook.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/api")
public class PictureDownloadController {

    @Autowired
    private PictureService pictureService;

    @GetMapping("/picture")
    public ResponseEntity<byte[]> downloadFile(@RequestParam int id, HttpServletResponse response) {
        DBPicture dbPicture = pictureService.getById(id);
        if (dbPicture == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", dbPicture.getFilename());
        headers.setContentType(MediaType.parseMediaType(dbPicture.getContentType()));
        return new ResponseEntity<>(dbPicture.getContent(), headers, HttpStatus.OK);
    }
}
