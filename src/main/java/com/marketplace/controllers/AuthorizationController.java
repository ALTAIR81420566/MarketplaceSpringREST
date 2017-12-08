package com.marketplace.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@SessionAttributes( {"login", "role"})
public class AuthorizationController {
    private final String LOGIN = "login";
    private final String ROLE = "role";
    private final String GUEST = "guest";

    @RequestMapping(value = "/authorization", method = GET)
    private ModelAndView showPage(){
        Map<String, String> model = new HashMap<>();
        model.put(LOGIN, GUEST);
        model.put(ROLE, GUEST);
        return new ModelAndView("Authorization", model);
    }

    @RequestMapping(value = "/authorization", method = POST)
    private ModelAndView authorization(@RequestParam(value = "login") String login,
    @RequestParam(value = "password") String password){

        Map<String, String> model = new HashMap<>();
        model.put(LOGIN, login);

       // model.put(ROLE, GUEST);
        return new ModelAndView("General", model);
    }

}
