package com.formularios.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.formularios.demo.models.Signup;
import com.formularios.demo.repository.SignupRepository;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class SignupController {

    private static final Logger logger = LoggerFactory.getLogger(SignupController.class);
    private static final Logger failedLoginLogger = LoggerFactory.getLogger("FILE_FAILED_LOGIN");

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
        logger.trace("Entering signup method with email: {}, agree: {}, newsletter: {}", email, agree, newsletter);

        if (signUpRepository.existsByEmail(email)) {
            logger.error("Email {} already exists in the database", email);
            return "redirect:/signup.html";
        }
        Signup sign = new Signup();
        sign.setEmail(email);
        sign.setPassword(password);
        signUpRepository.save(sign);
        sign.setAgree(agree);
        sign.setNewsletter(newsletter);

        signUpRepository.save(sign);
        logger.info("Email " + email + " saved successfully");
        return "redirect:/signup.html";
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request) {

        String ip = getClientIp(request);
        String userAgent = request.getHeader("User-Agent");
        failedLoginLogger.warn("Test log entry for login - IP: {}, User-Agent: {}", ip, userAgent);
        return "redirect:/signup.html";

    }

    private String getClientIp(HttpServletRequest request) {
        String remoteAddr = "";

        if (request != null) {
            remoteAddr = request.getHeader("X-Forwarded-For");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }

        return remoteAddr;
    }

    @GetMapping("/usuarios")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", signUpRepository.findAll());
        return "usuarios";
    }
}
