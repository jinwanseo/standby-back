package com.standbytogetherbackend.market.dto;

import lombok.Data;

@Data
public class CallCustomerInput {

    private Long marketId;
    private Long customerId;
}
