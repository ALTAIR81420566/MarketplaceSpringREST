package com.marketplace.controllers;

import com.marketplace.model.Bid;
import com.marketplace.model.Product;
import com.marketplace.model.User;
import com.marketplace.repositories.BidRepo;
import com.marketplace.repositories.ProductRepo;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@SessionAttributes("user")
public class GeneralPageController {


    private final ProductRepo prodRepo;
    private final BidRepo bidRepo;

    public GeneralPageController( ProductRepo prodRepo, BidRepo bidRepo) {
        this.prodRepo = prodRepo;
        this.bidRepo = bidRepo;
    }

    @RequestMapping(value = "/general", method = GET)
    private ModelAndView general(@ModelAttribute("user") User user) {
        Iterable<Product> dBProducts = prodRepo.findAll();
        HashMap<Product, Bid> products = new HashMap<>();
        dBProducts.forEach( product -> {
           Bid bid = bidRepo.getBestBid(product.getuID());
           products.put(product,bid);
        });

        return new ModelAndView("General","products", products);
    }

}
