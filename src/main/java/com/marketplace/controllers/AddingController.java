package com.marketplace.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@SessionAttributes("user")
public class AddingController {

    @RequestMapping(value = "/add", method = GET)
    private ModelAndView doGet(Model model){
        return new ModelAndView("General");
    }
}
