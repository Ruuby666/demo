package com.formularios.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.formularios.demo.models.Signup;
import com.formularios.demo.repository.SignupRepository;

@Controller
public class SignupController {
    private final SignupRepository signUpRepository;

    public SignupController(SignupRepository signUpRepository) {
        this.signUpRepository = signUpRepository;
    }

    @PostMapping("/guardar")
    public String guardarSignup(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam(value = "agree", required = false) boolean agree,
            @RequestParam(value = "newsletter", required = false) boolean newsletter) {
                
        Signup sign = new Signup();
        sign.setEmail(email);
        sign.setPassword(password);
        signUpRepository.save(sign);
        sign.setAgree(agree);
        sign.setNewsletter(newsletter);

        signUpRepository.save(sign);
        return "redirect:/signs";
    }

    @GetMapping("/usuarios")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", signUpRepository.findAll());
        return "usuarios";
    }
}
