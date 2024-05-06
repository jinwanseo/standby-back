package com.standbytogetherbackend.customer.dto;

import lombok.Data;

@Data
public class GetCustomerWaitingNumberInput {

    private Long marketId;
    private Long customerId;
}
