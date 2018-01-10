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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
    private static final String REDIR_GEN = "redirect:/general";
    private static final String TITLE_PARAM = "Title";
    private static final String PARAMS_PARAM = "params";
    private static final String DESCRIPTION_PARAM = "Description";
    private static final String UID_PARAM = "uId";
    private static final String USER = "user";
    private static final String GENERAL_VIEW = "General";
    private static final String GENERAL_PATH = "/general";
    private static final String ADVANCED_SEARCH_VIEW = "/AdvancedSearch";
    private static final String EQUALS_OPERATION = ":";
    private static final String GREATER_OPERATION = ">";
    private static final String LESS_OPERATION = "<";
    private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm";
    private static final int COOCKIES_MAX_AGE = 3600;

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
            Iterable<Product> dBProducts = null;
            result = new HashMap<Product, Bid>();
            if (searchBy == null) {
                dBProducts = prodRepo.findAll();
                result = makeProducts(dBProducts, null);
            } else {
                switch (searchBy) {
                    case TITLE_PARAM:
                        dBProducts = prodRepo.findByTitleContaining(text);
                        result = makeProducts(dBProducts, null);
                        break;
                    case DESCRIPTION_PARAM:
                        dBProducts = prodRepo.findByDescriptionContaining(text);
                        result = makeProducts(dBProducts, null);
                        break;
                    case UID_PARAM:
                        Product product = prodRepo.findByuID(Long.parseLong(text));
                        Bid bid = bidRepo.getBestBid(product.getuID());
                        result.put(product, bid);
                        break;
                }
            }
        } else {
            result = products;
            products = null;
        }

        return new ModelAndView(GENERAL_VIEW, "products", result);
    }

    private HashMap<Product, Bid> makeProducts(Iterable<Product> dBProducts, Integer findBid) {
        HashMap<Product, Bid> result = new HashMap<>();
        dBProducts.forEach(product -> {
            Bid bid = bidRepo.getBestBid(product.getuID());
            if (findBid != null) {
                if (bid != null && bid.getCount() == findBid) {
                    result.put(product, bid);
                }
            } else {
                result.put(product, bid);
            }
        });
        return result;
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
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(ADVANCED_SEARCH_VIEW);
        modelAndView.addObject(PARAMS_PARAM, searchParams);
        return modelAndView;
    }

    @RequestMapping(value = "/clear", method = GET)
    private ModelAndView clearSearch(HttpServletRequest request, HttpServletResponse response) {

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (!cookie.getName().equals("JSESSIONID")) {
                cookie.setMaxAge(0);
                cookie.setValue(null);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }

        return new ModelAndView("redirect:/advanced");
    }

    @RequestMapping(value = "/advanced", method = POST)
    private ModelAndView postAdvancedSearch(@Valid @ModelAttribute(PARAMS_PARAM) AdvancedSearchParams params, BindingResult bindingResult,
                                            HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        ;
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName(ADVANCED_SEARCH_VIEW);
            modelAndView.addObject(PARAMS_PARAM, params);
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

        try {
            addCookies(response, params);
        } catch (UnsupportedEncodingException e) {
            log.log(Level.SEVERE, "Encode error", e);
        }
        return modelAndView;
    }

    private void addCookies(HttpServletResponse response, AdvancedSearchParams params) throws UnsupportedEncodingException {
        if (params.getuId() != null) {
            Cookie uId = new Cookie("uIdCookie", params.getuId().toString());
            uId.setMaxAge(COOCKIES_MAX_AGE);
            response.addCookie(uId);
        }

        if (params.getDescription() != null) {
            Cookie description = new Cookie("descriptionCookie",  URLEncoder.encode(params.getDescription(), "UTF-8"));
            description.setMaxAge(COOCKIES_MAX_AGE);
            response.addCookie(description);
        }

        if (params.getTitle() != null) {
            Cookie title = new Cookie("titleCookie",  URLEncoder.encode(params.getTitle(), "UTF-8"));
            title.setMaxAge(COOCKIES_MAX_AGE);
            response.addCookie(title);
        }

        if (params.getExpireDate() != null) {
            Cookie expireDate = new Cookie("expireDateCookie", params.getExpireDate());
            expireDate.setMaxAge(COOCKIES_MAX_AGE);
            response.addCookie(expireDate);
        }

        if (params.getStartDate() != null) {
            Cookie startDate = new Cookie("startDateCookie", params.getStartDate());
            startDate.setMaxAge(COOCKIES_MAX_AGE);
            response.addCookie(startDate);
        }

        if (params.getBidCount() != null) {
            Cookie bid = new Cookie("bidCookie", params.getBidCount().toString());
            bid.setMaxAge(COOCKIES_MAX_AGE);
            response.addCookie(bid);
        }

        if (params.getBuyNow() != null) {
            Cookie buyNow = new Cookie("buyNowCookie", params.getBuyNow().toString());
            buyNow.setMaxAge(COOCKIES_MAX_AGE);
            response.addCookie(buyNow);
        }

        if (params.getMaxPrice() != null) {
            Cookie maxPrice = new Cookie("maxPriceCookie", params.getMaxPrice().toString());
            maxPrice.setMaxAge(COOCKIES_MAX_AGE);
            response.addCookie(maxPrice);
        }

        if (params.getMinPrice() != null) {
            Cookie minPrice = new Cookie("minPriceCookie", params.getMinPrice().toString());
            minPrice.setMaxAge(COOCKIES_MAX_AGE);
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

        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
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

