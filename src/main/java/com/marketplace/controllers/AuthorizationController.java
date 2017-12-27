package com.marketplace.controllers;

import com.marketplace.model.User;
import com.marketplace.repositories.UserRepo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@SessionAttributes("user")
public class AuthorizationController {
    private final String GUEST = "guest";

    private String error = "";
    private final String ERROR_ATT = "error";
    private final String USER = "user";
    private final String REDIR_GEN = "redirect:/general";
    private final String REDIR = "redirect:/";
    private final String AUTH_VIEW = "Authorization";
    private final UserRepo repo;

    public AuthorizationController(UserRepo repo) {
        this.repo = repo;
    }

    @RequestMapping(value = "/", method = GET)
    private ModelAndView showPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(USER, new User());
        modelAndView.addObject(ERROR_ATT, error);
        modelAndView.setViewName(AUTH_VIEW);
        return modelAndView;
    }

    @RequestMapping(value = "/authorization", method = POST)
    private ModelAndView authorization(@ModelAttribute(value = USER) User user) {
        ModelAndView modelAndView = new ModelAndView();
        user.setRole("USER");
        modelAndView.addObject(USER, user);
        modelAndView.setViewName(REDIR_GEN);
        return modelAndView;
    }

    @RequestMapping(value = "/authError", method = POST)
    private ModelAndView authError() {
        this.error = ERROR_ATT;
        return new ModelAndView("/");
    }

    @RequestMapping(value = "/authorization/guest", method = POST)
    private ModelAndView authorizationGuest(@ModelAttribute(value = USER) User user) {
        ModelAndView modelAndView = new ModelAndView();

        user.setLogin(GUEST);
        user.setRole(GUEST);
        modelAndView.addObject(USER, user);
        modelAndView.setViewName(REDIR_GEN);

        return modelAndView;
    }

}
