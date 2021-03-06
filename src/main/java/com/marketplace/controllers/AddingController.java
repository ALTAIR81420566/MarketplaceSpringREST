package com.marketplace.controllers;

import com.marketplace.model.Product;
import com.marketplace.model.User;
import com.marketplace.repositories.ProductRepo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@SessionAttributes("user")
public class AddingController {


    private final ProductRepo prodRepo;
    private Long productId;
    private static final String ADD_VIEW = "Adding";
    private static final String ADD_PATH = "/add";

    public AddingController(ProductRepo prodRepo) {
        this.prodRepo = prodRepo;
    }

    @RequestMapping(value = ADD_PATH, method = GET)
    private ModelAndView add(@RequestParam(value = "productId", required = false) Long productId) {
        this.productId = productId;
        return new ModelAndView(ADD_VIEW);
    }

    @RequestMapping(value = ADD_PATH, method = POST)
    private void addProduct(@ModelAttribute(value = "user") User user,
                            @RequestParam("title") String title,
                            @RequestParam("startPrice") double startPrice,
                            @RequestParam("timeLeft") int timeLeft,
                            @RequestParam("description") String description,
                            @RequestParam("step") double step,
                            @RequestParam("buyItNow") String buyItNow) {
        Product product = new Product();
        if (productId != null) {
            product.setuID(productId);
            Product dbProd = prodRepo.findByuID(productId);
            product.setStartBiddingDate(dbProd.getStartBiddingDate());
        } else {
            product.setStartBiddingDate(new Date().getTime());
        }
        product.setTitle(title);
        product.setStartPrice(startPrice);
        product.setTime(timeLeft * 60 * 60 * 1000);
        product.setEndBiddingDate(product.getTime() + product.getStartBiddingDate());
        product.setDescription(description);
        product.setStep(step);
        if (buyItNow.equals("true")) {
            product.setIsBuyNow(1);
        } else {
            product.setIsBuyNow(0);
        }
        product.setSellerID(user.getId());
        prodRepo.save(product);
    }

}
