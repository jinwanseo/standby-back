package com.standbytogetherbackend.customer.controller;


import com.standbytogetherbackend.customer.dto.CreateCustomerInput;
import com.standbytogetherbackend.customer.dto.DeleteCustomerInput;
import com.standbytogetherbackend.customer.dto.GetCustomerWaitingNumberInput;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface CustomerController {

    ResponseEntity<?> createCustomer(CreateCustomerInput createCustomerInput,
        BindingResult bindingResult);


    ResponseEntity<?> getCustomerById(Long customerId);


    ResponseEntity<?> deleteCustomer(DeleteCustomerInput deleteCustomerInput);

    ResponseEntity<?> getCustomerList(Long storeId);

    ResponseEntity<?> getWaitingNumber(GetCustomerWaitingNumberInput getCustomerWaitingNumberInput);

}
