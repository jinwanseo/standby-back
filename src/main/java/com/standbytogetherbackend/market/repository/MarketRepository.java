package com.standbytogetherbackend.market.repository;


import com.standbytogetherbackend.market.entity.Market;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketRepository extends JpaRepository<Market, Long> {

}
