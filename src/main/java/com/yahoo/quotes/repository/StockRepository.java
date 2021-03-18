package com.yahoo.quotes.repository;

import com.yahoo.quotes.domain.Stock;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Stock entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    @Query("select stock from Stock stock where stock.user.login = ?#{principal.username}")
    List<Stock> findByUserIsCurrentUser();
}
