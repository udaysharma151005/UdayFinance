package com.uday.financeportal.controller;
import com.uday.financeportal.model.User;
import com.uday.financeportal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
@RequiredArgsConstructor
public class   AuthController {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        userRepository.save(user);
        model.addAttribute("message", "Registration successful! Please login.");
        return "login";
    }
}