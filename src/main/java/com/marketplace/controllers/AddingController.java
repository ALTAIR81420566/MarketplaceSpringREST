package com.marketplace.controllers;

import com.marketplace.model.Product;
import com.marketplace.model.User;
import com.marketplace.repositories.ProductRepo;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@SessionAttributes("user")
public class AddingController {

    private final ProductRepo prodRepo;

    public AddingController(ProductRepo prodRepo) {
        this.prodRepo = prodRepo;
    }

    @RequestMapping(value = "/add", method = GET)
    private ModelAndView add() {
        return new ModelAndView("Adding");
    }

    @RequestMapping(value = "/add", method = POST)
    private void addProduct(@ModelAttribute(value = "user") User user, @RequestParam("title") String title, @RequestParam("startPrice") double startPrice,
                              @RequestParam("timeLeft") int timeLeft, @RequestParam("description") String description,
                              @RequestParam("step") double step, @RequestParam("buyItNow") String buyItNow) {
        Product product =  new Product();
        product.setTitle(title);
        product.setStartPrice(startPrice);
        product.setTime(timeLeft * 60 * 60 * 1000);
        product.setDescription(description);
        product.setStep(step);
        if(buyItNow.equals("true")) {
            product.setIsBuyNow(1);
        }else{
            product.setIsBuyNow(0);
        }
        product.setStartBiddingDate(new Date().getTime());
        product.setSellerID(user.getId());
        prodRepo.save(product);
    }
}
