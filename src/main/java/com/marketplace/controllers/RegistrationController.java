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
    private static final String REG_VIEW = "Registration";
    private static final String REG_PATH = "/registration";

    public RegistrationController(UserRepo repo) {
        this.repo = repo;
    }

    @RequestMapping(value = REG_PATH, method = GET)
    private ModelAndView registrationGet() {
        return new ModelAndView(REG_VIEW);
    }

    @RequestMapping(value = REG_PATH, method = POST)
    private User registrationPost(@RequestParam("fullName") String name,
                                  @RequestParam("login") String login,
                                  @RequestParam("password") String password,
                                  @RequestParam("address") String address, Model model) {
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
