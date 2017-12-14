package com.marketplace.repositories;

import com.marketplace.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends CrudRepository<Product, Long> {

    @Query(value ="SELECT * FROM PRODUCTS INNER JOIN USERS on " +
            "PRODUCTS.SELLER_ID = USERS.USER_ID WHERE USERS.LOGIN = ?1 ", nativeQuery = true)
    Iterable<Product> findByLogin(String login);

    Product findByuID(long productId);

    Iterable<Product> findByTitleContaining(String title);

    Iterable<Product> findByDescriptionContaining(String description);
}
