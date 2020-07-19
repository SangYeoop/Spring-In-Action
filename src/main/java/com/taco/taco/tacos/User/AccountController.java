package com.taco.taco.tacos.User;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class AccountController {
    private final AccountService accountService;

    @InitBinder
    public void RegistrationFormInitBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(new RegistrationFormValidator());
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute(new RegistrationForm());
        return "registration";
    }

    @PostMapping("/register")
    public String processRegistration(@Valid RegistrationForm form, Errors errors) {
        if(errors.hasErrors()) {
            return "registration";
        }
        accountService.save(form);
        return "redirect:/design";
    }
}
