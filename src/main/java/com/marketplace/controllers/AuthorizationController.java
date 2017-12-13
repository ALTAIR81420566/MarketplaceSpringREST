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
public class AuthorizationController {
    private final String GUEST = "guest";

    private String error = "";
    private final UserRepo repo;

    public AuthorizationController(UserRepo repo) {
        this.repo = repo;
    }

    @RequestMapping(value = "/", method = GET)
    private ModelAndView showPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", new User());
        modelAndView.addObject("error", error);
        modelAndView.setViewName("Authorization");
        return modelAndView;
    }

    @RequestMapping(value = "/authorization", method = POST)
    private ModelAndView authorization(@ModelAttribute(value = "user") User user) {
        ModelAndView modelAndView = new ModelAndView();
        User respUser = repo.findByLogin(user.getLogin());
        if (respUser != null && respUser.getPassword().equals(user.getPassword())) {
            modelAndView.addObject("user", respUser);
            modelAndView.setViewName("redirect:/general");
        } else {
            this.error = "error";
            modelAndView.setViewName("redirect:/");
        }

        return modelAndView;
    }

    @RequestMapping(value = "/authorization/guest", method = POST)
    private ModelAndView authorizationGuest(@ModelAttribute(value = "user") User user) {
        ModelAndView modelAndView = new ModelAndView();

        user.setLogin(GUEST);
        user.setRole(GUEST);
        modelAndView.addObject("user", user);
        modelAndView.setViewName("redirect:/general");

        return modelAndView;
    }

}
