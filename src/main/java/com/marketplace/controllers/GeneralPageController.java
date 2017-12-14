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
public class GeneralPageController {

    private final ProductRepo prodRepo;
    private final BidRepo bidRepo;

    public GeneralPageController(ProductRepo prodRepo, BidRepo bidRepo) {
        this.prodRepo = prodRepo;
        this.bidRepo = bidRepo;
    }

    @RequestMapping(value = "/general", method = GET)
    private ModelAndView general(@ModelAttribute("user") User user,
                                 @RequestParam(value = "findBy", required = false) String searchBy,
                                 @RequestParam(value = "searchText", required = false) String text) {
        Iterable<Product> dBProducts = null;
        HashMap<Product, Bid> products = new HashMap<>();
        if (searchBy == null) {
            dBProducts = prodRepo.findAll();
            products = makeProducts(dBProducts);
        } else if (searchBy.equals("Title")) {
            dBProducts = prodRepo.findByTitleContaining(text);
            products = makeProducts(dBProducts);
        } else if (searchBy.equals("Description")) {
            dBProducts = prodRepo.findByDescriptionContaining(text);
            products = makeProducts(dBProducts);
        } else if (searchBy.equals("uId")) {
            Product product = prodRepo.findByuID(Long.parseLong(text));
            Bid bid = bidRepo.getBestBid(product.getuID());
            products.put(product, bid);
        }
        return new ModelAndView("General", "products", products);
    }

    private HashMap<Product, Bid> makeProducts(Iterable<Product> dBProducts) {
        HashMap<Product, Bid> products = new HashMap<>();
        dBProducts.forEach(product -> {
            Bid bid = bidRepo.getBestBid(product.getuID());
            products.put(product, bid);
        });
        return products;
    }

    @RequestMapping(value = "/general", method = POST)
    private ModelAndView addBid(@ModelAttribute("user") User user,
                                @RequestParam("productId") long productId,
                                @RequestParam("count") int count) {
        Product product = prodRepo.findByuID(productId);
        Bid bestBid = bidRepo.getBestBid(productId);
        if (product.getStep() <= count) {
            if (bestBid != null) {
                if (count > bestBid.getCount()) {
                    saveBid(user, productId, count);
                }
            } else {
                saveBid(user, productId, count);
            }
        }
        return new ModelAndView("redirect:/general");
    }

    private void saveBid(User user, long productId, int count) {
        Bid bid = new Bid();
        bid.setCount(count);
        bid.setProductId(productId);
        bid.setUserId(user.getId());
        bidRepo.save(bid);
    }

    @RequestMapping(value = "/buy", method = POST)
    private ModelAndView buyBid(@ModelAttribute("user") User user, @RequestParam("productId") long productId) {
        Product product = prodRepo.findByuID(productId);
        product.setSold(1);
        prodRepo.save(product);
        return new ModelAndView("redirect:/general");
    }

}
