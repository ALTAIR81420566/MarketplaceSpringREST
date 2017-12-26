package com.marketplace.controllers;

import com.marketplace.model.AdvancedSearchParams;
import com.marketplace.model.Product;
import com.marketplace.model.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class AdvancedSearchController {
    @RequestMapping(value = "/advanced", method = GET)
    private ModelAndView getAdvancedSearch() {
        AdvancedSearchParams params = new AdvancedSearchParams();
        return new ModelAndView("AdvancedSearch","params", params);
    }



}
