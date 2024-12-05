package com.sk.contactapi.controller;


import com.sk.contactapi.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.InputStream;

@RestController
@RequestMapping("/images")
@CrossOrigin("http://localhost:5173/")
public class ImageController {

    @Autowired
    private ContactService contactService;


    @GetMapping("/{imageName}")
    public ResponseEntity<?> getImage(@PathVariable String imageName) {
        InputStream resource = null;
        try {
            resource = contactService.getImageByName(imageName);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(new InputStreamResource(resource));
        } catch (FileNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
