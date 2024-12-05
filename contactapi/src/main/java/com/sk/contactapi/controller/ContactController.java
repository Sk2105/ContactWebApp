package com.sk.contactapi.controller;

import com.sk.contactapi.entities.Contact;
import com.sk.contactapi.models.ContactDTO;
import com.sk.contactapi.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/contact")
@CrossOrigin("http://localhost:5173/")
public class ContactController {

    @Autowired
    private ContactService contactService;

    // GET all contacts
    @GetMapping
    public ResponseEntity<?> getAllContacts() {
        List<Contact> contacts = contactService.getAllContacts();
        if (contacts.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(contacts);
        }
    }


    // POST a new contact
    @PostMapping
    public ResponseEntity<Contact> addContact(@RequestBody ContactDTO contact) {
        Contact savedContact = contactService.saveContact(contact);
        return new ResponseEntity<>(savedContact, HttpStatus.CREATED);
    }


    // Upload a contact image
    @PostMapping("/{id}/profile")
    @CrossOrigin("http://localhost:5173/")
    public ResponseEntity<String> uploadImage(@PathVariable String id, @RequestParam("image") MultipartFile image) {
        contactService.saveContactImage(id, image);
        return new ResponseEntity<>("Image uploaded successfully", HttpStatus.OK);
    }


    // update contact
    @PutMapping("/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable String id, @RequestBody ContactDTO contact) {
        Contact updatedContact = contactService.updateContact(id, contact);
        return new ResponseEntity<>(updatedContact, HttpStatus.OK);
    }


    // delete contact
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteContact(@PathVariable String id) {
        try {
            contactService.deleteContact(id);
            return new ResponseEntity<>("Contact deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Contact not found", HttpStatus.NOT_FOUND);
        }
    }


    // get contact by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getContactById(@PathVariable("id") String id) {
        Contact contact = contactService.getContactById(id);
        if (contact == null) {
            return ResponseEntity.badRequest().body(
                    Map.of("message", "Contact id not found")
            );
        } else {
            return ResponseEntity.ok(contact);
        }
    }


}
