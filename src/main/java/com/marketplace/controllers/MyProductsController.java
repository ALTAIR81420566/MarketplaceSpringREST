package com.marketplace.controllers;

import com.marketplace.model.Bid;
import com.marketplace.model.Product;
import com.marketplace.model.User;
import com.marketplace.repositories.BidRepo;
import com.marketplace.repositories.ProductRepo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@SessionAttributes("user")
public class MyProductsController {

    private final ProductRepo prodRepo;
    private final BidRepo bidRepo;

    public MyProductsController(ProductRepo prodRepo, BidRepo bidRepo) {
        this.prodRepo = prodRepo;
        this.bidRepo = bidRepo;
    }

    @RequestMapping(value = "/my_product", method = GET)
    private ModelAndView getMyProduct(@ModelAttribute("user") User user) {
        Iterable<Product> dBProducts = prodRepo.findByLogin(user.getLogin());
        HashMap<Product, Bid> products = new HashMap<>();
        dBProducts.forEach( product -> {
            Bid bid = bidRepo.getBestBid(product.getuID());
            products.put(product,bid);
        });

        return new ModelAndView("MyProducts","products", products);
    }

    @RequestMapping(value = "/delete", method = POST)
    private ModelAndView delete(@RequestParam("productId") long productId) {
        prodRepo.delete(productId);
        return new ModelAndView("redirect:/my_product");
    }

}
