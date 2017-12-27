package com.marketplace.controllers;

import com.marketplace.model.*;
import com.marketplace.repositories.BidRepo;
import com.marketplace.repositories.ProductRepo;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@SessionAttributes("user")
public class GeneralPageController {
    private final ProductRepo prodRepo;
    private final BidRepo bidRepo;
    private final String REDIR_GEN = "redirect:/general";
    private final String TITLE_PARAM = "Title";
    private final String DESCRIPTION_PARAM = "Description";
    private final String UID_PARAM = "uId";
    private final String USER = "user";
    private final String GENERAL_VIEW = "General";
    private final String GENERAL_PATH = "/general";
    private final String EQUALS_OPERATION = ":";
    private final String GREATER_OPERATION = ">";
    private final String LESS_OPERATION = "<";

    private HashMap<Product, Bid> products;

    private static Logger log = Logger.getLogger(GeneralPageController.class.getName());

    public GeneralPageController(ProductRepo prodRepo, BidRepo bidRepo) {
        this.prodRepo = prodRepo;
        this.bidRepo = bidRepo;
    }

    @RequestMapping(value = GENERAL_PATH, method = GET)
    private ModelAndView general(@ModelAttribute(USER) User user,
                                 @RequestParam(value = "findBy", required = false) String searchBy,
                                 @RequestParam(value = "searchText", required = false) String text) {
        HashMap<Product, Bid> result = null;
        if (products == null) {
            result = new HashMap<>();
            Iterable<Product> dBProducts = null;
            result = new HashMap<Product, Bid>();
            if (searchBy == null) {
                dBProducts = prodRepo.findAll();
                result = makeProducts(dBProducts, null);
            } else if (searchBy.equals(TITLE_PARAM)) {
                dBProducts = prodRepo.findByTitleContaining(text);
                result = makeProducts(dBProducts, null);
            } else if (searchBy.equals(DESCRIPTION_PARAM)) {
                dBProducts = prodRepo.findByDescriptionContaining(text);
                result = makeProducts(dBProducts, null);
            } else if (searchBy.equals(UID_PARAM)) {
                Product product = prodRepo.findByuID(Long.parseLong(text));
                Bid bid = bidRepo.getBestBid(product.getuID());
                result.put(product, bid);
            }
        } else {
            result = products;
            products = null;
        }

        return new ModelAndView(GENERAL_VIEW, "products", result);
    }

    private HashMap<Product, Bid> makeProducts(Iterable<Product> dBProducts, Integer findBid) {
        HashMap<Product, Bid> products = new HashMap<>();
        dBProducts.forEach(product -> {
            Bid bid = bidRepo.getBestBid(product.getuID());
            if (findBid != null) {
                if (bid != null && bid.getCount() == findBid) {
                    products.put(product, bid);
                }
            } else {
                products.put(product, bid);
            }
        });
        return products;
    }

    @RequestMapping(value = GENERAL_PATH, method = POST)
    private ModelAndView addBid(@ModelAttribute(USER) User user,
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
        return new ModelAndView(REDIR_GEN);
    }

    private void saveBid(User user, long productId, int count) {
        Bid bid = new Bid();
        bid.setCount(count);
        bid.setProductId(productId);
        bid.setUserId(user.getId());
        bidRepo.save(bid);
    }

    @RequestMapping(value = "/buy", method = POST)
    private ModelAndView buyBid(@ModelAttribute(USER) User user, @RequestParam("productId") long productId) {
        Product product = prodRepo.findByuID(productId);
        product.setSold(1);
        prodRepo.save(product);
        return new ModelAndView(REDIR_GEN);
    }

    @RequestMapping(value = "/advanced", method = GET)
    private ModelAndView getAdvancedSearch(HttpServletRequest request) {
        AdvancedSearchParams searchParams = new AdvancedSearchParams();
        return new ModelAndView("AdvancedSearch", "params", searchParams);
    }

    @RequestMapping(value = "/advanced", method = POST)
    private ModelAndView postAdvancedSearch(@Valid @ModelAttribute("params") AdvancedSearchParams params, BindingResult bindingResult,
                                            HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        ;
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("AdvancedSearch");
            modelAndView.addObject("params", params);
        } else {
            modelAndView.setViewName(REDIR_GEN);
            products = new HashMap<>();
            Specifications<Product> specifications = makeSpecification(params);
            List<Product> prodList = prodRepo.findAll(Specifications.where(specifications));

            Integer findBid = null;
            if (params.getBidCount() != null) {
                findBid = params.getBidCount();
            }
            products = makeProducts(prodList, findBid);
        }

        addCookies(response, params);
        return modelAndView;
    }

    private void addCookies(HttpServletResponse response, AdvancedSearchParams params) {
        if(params.getuId() != null) {
            Cookie uId = new Cookie("uIdCookie", params.getuId().toString());
            uId.setMaxAge(3600);
            response.addCookie(uId);
        }

        if(params.getDescription() != null) {
            Cookie description = new Cookie("descriptionCookie", params.getDescription());
            description.setMaxAge(3600);
            response.addCookie(description);
        }

        if(params.getTitle() != null) {
            Cookie title = new Cookie("titleCookie", params.getTitle());
            title.setMaxAge(3600);
            response.addCookie(title);
        }

        if(params.getExpireDate() != null) {
            Cookie expireDate = new Cookie("expireDateCookie", params.getExpireDate());
            expireDate.setMaxAge(3600);
            response.addCookie(expireDate);
        }

        if(params.getStartDate() != null) {
            Cookie startDate = new Cookie("startDateCookie", params.getStartDate());
            startDate.setMaxAge(3600);
            response.addCookie(startDate);
        }

        if(params.getBidCount() != null) {
            Cookie bid = new Cookie("bidCookie", params.getBidCount().toString());
            bid.setMaxAge(3600);
            response.addCookie(bid);
        }

        if(params.getBuyNow() != null) {
            Cookie buyNow = new Cookie("buyNowCookie", params.getBuyNow().toString());
            buyNow.setMaxAge(3600);
            response.addCookie(buyNow);
        }

        if(params.getMaxPrice() != null) {
            Cookie maxPrice = new Cookie("maxPriceCookie", params.getMaxPrice().toString());
            maxPrice.setMaxAge(3600);
            response.addCookie(maxPrice);
        }

        if(params.getMinPrice() != null) {
            Cookie minPrice = new Cookie("minPriceCookie", params.getMinPrice().toString());
            minPrice.setMaxAge(3600);
            response.addCookie(minPrice);
        }


    }

    private Specifications<Product> buildSpecification(Specifications<Product> specifications, ProductSpecification sp) {
        Specifications<Product> result = null;
        if (specifications == null) {
            result = Specifications.where(sp);
        } else {
            result = specifications.and(sp);
        }
        return result;
    }

    private Specifications<Product> makeSpecification(AdvancedSearchParams params) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Long startDateMillis = null;
        Long expireDateMillis = null;
        try {
            if (params.getStartDate() != null && !params.getStartDate().equals("")) {
                Date startDate = formatter.parse(params.getStartDate());
                startDateMillis = startDate.getTime();
            }
            if (params.getExpireDate() != null && !params.getExpireDate().equals("")) {
                Date expireDate = formatter.parse(params.getExpireDate());
                expireDateMillis = expireDate.getTime();
            }
        } catch (ParseException e) {
            log.log(Level.SEVERE, "Incorrect format of date", e);
        }

        Specifications<Product> specifications = null;
        if (params.getTitle() != null && !params.getTitle().equals("")) {
            ProductSpecification sp = new ProductSpecification(new SearchCriteria("title", EQUALS_OPERATION, params.getTitle()));
            specifications = buildSpecification(specifications, sp);
        }
        if (expireDateMillis != null) {
            ProductSpecification sp = new ProductSpecification(new SearchCriteria("endBiddingDate", LESS_OPERATION, expireDateMillis));
            specifications = buildSpecification(specifications, sp);
        }
        if (startDateMillis != null) {
            ProductSpecification sp = new ProductSpecification(new SearchCriteria("startBiddingDate", GREATER_OPERATION, startDateMillis));
            specifications = buildSpecification(specifications, sp);
        }
        if (params.getDescription() != null && !params.getDescription().equals("")) {
            ProductSpecification sp = new ProductSpecification(new SearchCriteria("description", EQUALS_OPERATION, params.getDescription()));
            specifications = buildSpecification(specifications, sp);
        }
        if (params.getMaxPrice() != null) {
            ProductSpecification sp = new ProductSpecification(new SearchCriteria("startPrice", LESS_OPERATION, params.getMaxPrice()));
            specifications = buildSpecification(specifications, sp);
        }
        if (params.getMinPrice() != null) {
            ProductSpecification sp = new ProductSpecification(new SearchCriteria("startPrice", GREATER_OPERATION, params.getMinPrice()));
            specifications = buildSpecification(specifications, sp);
        }
        if (params.getuId() != null) {
            ProductSpecification sp = new ProductSpecification(new SearchCriteria("uID", EQUALS_OPERATION, params.getuId()));
            specifications = buildSpecification(specifications, sp);
        }
        if (params.getBuyNow() != null && params.getBuyNow() == true) {
            ProductSpecification sp = new ProductSpecification(new SearchCriteria("buyNow", EQUALS_OPERATION, 1));
            specifications = buildSpecification(specifications, sp);
        }

        return specifications;
    }


}

