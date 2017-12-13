package com.marketplace.repositories;


import com.marketplace.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidRepo extends JpaRepository<Bid, Long> {

    @Query(value ="SELECT b.BID_ID, b.USER_ID, b.PRODUCT_ID, b.BID  FROM BIDS b," +
            "(SELECT MAX(BID) AS max, PRODUCT_ID FROM BIDS WHERE PRODUCT_ID = ?1 GROUP BY PRODUCT_ID) " +
            "maxresults WHERE b.PRODUCT_ID = ?1 AND b.BID  = maxresults.max", nativeQuery = true)
    Bid getBestBid(long productId);
}
