package pl.rengreen.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.rengreen.taskmanager.model.User;
import pl.rengreen.taskmanager.service.UserService;

import javax.validation.Valid;
import java.sql.SQLOutput;

@Controller
public class RegisterController {
    private UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "forms/register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> {
                System.out.println(error.getDefaultMessage());
            });
            return "forms/register";
        }

        if (userService.isUserEmailPresent(user.getEmail())) {
            System.out.println("email exist");
            model.addAttribute("exist", true);
            return "register";
        }

        userService.createUser(user);
        System.out.println("Success");
        return "views/success";
    }

}
