package com.sk.contactapi.services;


import com.sk.contactapi.entities.Contact;
import com.sk.contactapi.models.ContactDTO;
import com.sk.contactapi.repo.ContactRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class ContactService {

    @Value(value = "${spring.servlet.multipart.location}")
    private String imagePath;

    @Autowired
    private ContactRepo contactRepo;


    public List<Contact> getAllContacts() {
        return contactRepo.findAll();
    }

    public Contact saveContact(ContactDTO contactDTO) {
        Contact contact = new Contact();
        contact.setName(contactDTO.name());
        contact.setEmail(contactDTO.email());
        contact.setPhone(contactDTO.phone());
        contact.setAddress(contactDTO.address());
        contact.setAge(contactDTO.age());
        contact.setActive(contactDTO.active());
        return contactRepo.save(contact);
    }

    // upload a account image
    public void saveContact(String id, MultipartFile image) {
        try {
            Contact contact = contactRepo.findById(id).orElseThrow(() -> new RuntimeException("Contact id not found"));
            String fileName = UUID.randomUUID() + Objects.requireNonNull(image.getOriginalFilename()).substring(image.getOriginalFilename().lastIndexOf("."));
            File file = new File(imagePath + File.separator + fileName);
            // Create parent directories if they don't exist
            if (!file.exists()) {
                file.mkdir();
            }

            // Copy the image file
            try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, Paths.get(file.toURI()), StandardCopyOption.REPLACE_EXISTING);
            }

            contact.setImageUrl("http://localhost:8080/image/" + fileName);

            contactRepo.save(contact);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save contact image", e);
        }
    }


    // update contact
    public Contact updateContact(String id, ContactDTO contactDTO) {
        Contact contact = contactRepo.findById(id).get();
        contact.setName(contactDTO.name());
        contact.setEmail(contactDTO.email());
        contact.setPhone(contactDTO.phone());
        contact.setAddress(contactDTO.address());
        contact.setAge(contactDTO.age());
        contact.setActive(contactDTO.active());
        return contactRepo.save(contact);
    }


    // delete contact
    public void deleteContact(String id) throws Exception {
        try{
            Contact contact = contactRepo.findById(id).get();
            File file = new File(imagePath + File.separator + contact.getImageUrl().substring(contact.getImageUrl().lastIndexOf("/")));
            if (file.exists()) {
                file.delete();
            }
            contactRepo.deleteById(id);
        }catch (Exception e){
            throw new Exception("Contact id not found");
        }

    }

    // find contact by id
    public Contact getContactById(String id) {
        return contactRepo.findById(id).orElseThrow(() -> new RuntimeException("Contact id not found"));
    }

    // get image by contact id
    public File getImage(String id) {
        Contact contact = contactRepo.findById(id).orElseThrow(() -> new RuntimeException("Contact id not found"));
        File file = new File(URI.create(contact.getImageUrl()));
        if (file.exists()) {
            System.out.println(file.toURI());
            return file;
        } else {
            throw new RuntimeException("Image not found");
        }
    }

    // get image by imageName
    public InputStream getImageByName(String imageName) throws FileNotFoundException {
        File file = new File(imagePath + File.separator + imageName);
        if (file.exists()) {
            return new FileInputStream(file);
        } else {
            throw new FileNotFoundException("Image not found");
        }
    }

}
