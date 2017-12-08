package com.marketplace.controllers;

import com.marketplace.repositories.BidRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.concurrent.atomic.AtomicLong;


@Controller
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    final BidRepo bidRepo;

    public GreetingController(BidRepo bidRepo) {
        this.bidRepo = bidRepo;
    }

    @RequestMapping("/test")
    public String test() {
        return "General";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        return "index";
    }
}