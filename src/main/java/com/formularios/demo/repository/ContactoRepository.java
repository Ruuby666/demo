package com.formularios.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.formularios.demo.models.Contact;

public interface ContactoRepository extends JpaRepository<Contact, Long> {
}

