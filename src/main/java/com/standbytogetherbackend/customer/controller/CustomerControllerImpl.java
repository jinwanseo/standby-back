package com.standbytogetherbackend.customer.controller;

import com.standbytogetherbackend.customer.dto.CreateCustomerInput;
import com.standbytogetherbackend.customer.dto.CustomerOutput;
import com.standbytogetherbackend.customer.dto.DeleteCustomerInput;
import com.standbytogetherbackend.customer.dto.GetCustomerWaitingNumberInput;
import com.standbytogetherbackend.customer.entity.Customer;
import com.standbytogetherbackend.customer.service.CustomerService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerControllerImpl implements CustomerController {

    private final CustomerService customerService;

    @Override
    @PostMapping("/create")
    public ResponseEntity<?> createCustomer(
        @RequestBody CreateCustomerInput createCustomerInput
    ) {

        // 대기열 등록
        Customer customer = customerService.createCustomer(createCustomerInput);
        // 대기 번호 조회
        Map<String, Integer> numbers = customerService.getWaitingNumber(
            createCustomerInput.getMarketId(), customer.getId());

        return new ResponseEntity<>(
            Map.of("ok", true, "result", customer.toOutput(), "waiting", numbers.get("waiting"),
                "total", numbers.get("total")),
            HttpStatus.CREATED);
    }

    @Override
    @GetMapping("/detail/{customerId}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long customerId) {
        Customer customer = this.customerService.getCustomerById(customerId);
        return new ResponseEntity<>(Map.of("ok", true, "result", customer.toOutput()),
            HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCustomer(DeleteCustomerInput deleteCustomerInput)
        throws IOException {
        Customer customer = this.customerService.deleteCustomer(
            deleteCustomerInput.getCustomerId());
        return new ResponseEntity<>(Map.of("ok", true, "result", customer.toOutput()),
            HttpStatus.OK);
    }

    @Override
    @GetMapping("/list/{marketId}")
    public ResponseEntity<?> getCustomerList(@PathVariable Long marketId) {
        List<Customer> customers = this.customerService.getCustomerList(marketId);
        List<CustomerOutput> result = new ArrayList<>();
        for (Customer customer : customers) {
            result.add(customer.toOutput());
        }
        return new ResponseEntity<>(Map.of("ok", true, "result", result), HttpStatus.OK);
    }

    @Override
    @PostMapping("/waiting")
    public ResponseEntity<?> getWaitingNumber(
        @ModelAttribute GetCustomerWaitingNumberInput getCustomerWaitingNumberInput) {
        Map<String, Integer> result = this.customerService.getWaitingNumber(
            getCustomerWaitingNumberInput.getMarketId(),
            getCustomerWaitingNumberInput.getCustomerId());
        return new ResponseEntity<>(
            Map.of("ok", true, "waiting", result.get("waiting"), "total", result.get("total")),
            HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> postponeWaiting(Long customerId) {
        return null;
    }
}
