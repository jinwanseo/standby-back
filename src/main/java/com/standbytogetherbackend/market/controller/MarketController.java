package com.standbytogetherbackend.market.controller;

import com.standbytogetherbackend.market.dto.CreateMarketInput;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;

public interface MarketController {

    ResponseEntity<?> createMarket(CreateMarketInput createMarketInput,
        BindingResult bindingResult);

    ResponseEntity<?> getMarketById(Long id);

    ResponseEntity<?> getMarketList();


    ResponseEntity<?> getCustomerList(Long id);
}
