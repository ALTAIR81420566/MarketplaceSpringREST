package com.marketplace.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@SessionAttributes( {"login", "role"})
public class GeneralPageController {

    @RequestMapping(value = "/general", method = GET)
    private ModelAndView doGet(@ModelAttribute("login") String login, @ModelAttribute("role") String role) {
        System.out.println(login);
        return new ModelAndView("General");
    }

}
