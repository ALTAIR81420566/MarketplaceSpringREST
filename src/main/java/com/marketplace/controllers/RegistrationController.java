package com.marketplace.controllers;

import com.marketplace.model.User;
import com.marketplace.repositories.UserRepo;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@SessionAttributes("user")
public class RegistrationController {

    private final UserRepo repo;

    public RegistrationController(UserRepo repo) {
        this.repo = repo;
    }

    @RequestMapping(value = "/registration", method = GET)
    private ModelAndView registrationGet() {
        return new ModelAndView("Registration");
    }

    @RequestMapping(value = "/registration", method = POST)
    private User registrationPost(@RequestParam("fullName") String name, @RequestParam("login") String login,
                                  @RequestParam("password") String password, @RequestParam("address") String address, Model model) {
        User user = new User();
        if (repo.findByLogin(login) == null) {
            user.setLogin(login);
            user.setBillingAddress(address);
            user.setPassword(password);
            user.setName(name);
            user.setRole("USER");
            repo.save(user);
        }
        return user;
    }
}
