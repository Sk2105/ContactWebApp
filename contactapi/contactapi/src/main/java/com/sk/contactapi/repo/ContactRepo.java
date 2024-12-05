package com.sk.contactapi.repo;

import com.sk.contactapi.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepo extends JpaRepository<Contact, String> {
}
