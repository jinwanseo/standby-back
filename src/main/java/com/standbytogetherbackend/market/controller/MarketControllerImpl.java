package com.standbytogetherbackend.market.controller;

import com.standbytogetherbackend.common.error.CustomErrorMessage;
import com.standbytogetherbackend.customer.dto.CustomerOutput;
import com.standbytogetherbackend.customer.entity.Customer;
import com.standbytogetherbackend.market.dto.CreateMarketInput;
import com.standbytogetherbackend.market.dto.MarketOutput;
import com.standbytogetherbackend.market.entity.Market;
import com.standbytogetherbackend.market.service.MarketService;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/market")
public class MarketControllerImpl implements MarketController {

    private final MarketService marketService;
    private final CustomErrorMessage errorMessage;

    @Override
    @PostMapping("/create")
    public ResponseEntity<?> createMarket(
        @Valid @ModelAttribute CreateMarketInput createMarketInput, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<Map<String, String>> errorMessages = this.errorMessage.getErrorMessages(
                bindingResult);
            return new ResponseEntity<>(Map.of("ok", false, "message", errorMessages),
                HttpStatus.BAD_REQUEST);
        }
        Market market = this.marketService.createMarket(createMarketInput);
        return new ResponseEntity<>(
            Map.of("ok", true, "result", market.toOutput()), HttpStatus.CREATED
        );
    }

    @GetMapping("/detail/{marketId}")
    @Override
    public ResponseEntity<?> getMarketById(@PathVariable Long marketId) {
        Market market = this.marketService.findMarketById(marketId);
        return new ResponseEntity<>(Map.of("ok", true, "result", market.toOutput()), HttpStatus.OK);
    }

    @GetMapping("/list")
    @Override
    public ResponseEntity<?> getMarketList() {
        List<Market> marketList = this.marketService.findMarketList();

        List<MarketOutput> result = new ArrayList<>();
        for (Market market : marketList) {
            result.add(market.toOutput());
        }
        return new ResponseEntity<>(
            Map.of("ok", true, "total", marketList.size(), "result", result), HttpStatus.OK);
    }

    @GetMapping("/customer/list/{marketId}")
    @Override
    public ResponseEntity<?> getCustomerList(@PathVariable Long marketId) {
        Market marketById = this.marketService.findMarketById(marketId);
        List<Customer> customers = marketById.getCustomers();
        List<CustomerOutput> result = new ArrayList<>();
        for (Customer customer : customers) {
            result.add(customer.toOutput());
        }
        return new ResponseEntity<>(
            Map.of("ok", true, "total", customers.size(), "result", result), HttpStatus.OK);
    }
}
