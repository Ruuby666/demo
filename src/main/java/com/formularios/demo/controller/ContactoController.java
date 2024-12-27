package com.formularios.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.formularios.demo.models.Contact;
import com.formularios.demo.repository.ContactoRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class ContactoController {

    @Autowired
    private ContactoRepository contactoRepository;

    @PostMapping("/guardarContacto")
    public String guardarContacto(
            @RequestParam("contactEmail") String contactEmail,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("job") String job,
            @RequestParam("location") String location) {

        // Crear objeto y guardar en la base de datos
        Contact contacto = new Contact();
        contacto.setEmail(contactEmail);
        contacto.setFirstName(firstName);
        contacto.setLastName(lastName);
        contacto.setJob(job);
        contacto.setLocation(location);

        contactoRepository.save(contacto);

        // Redireccionar a una página o vista
        return "redirect:/contactos";
    }

    // Mostrar todos los contactos guardados
    @GetMapping("/contactos")
    public String verContactos(Model model) {
        model.addAttribute("contactos", contactoRepository.findAll());
        return "contactos";
    }
}

