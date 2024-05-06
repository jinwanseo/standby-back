package com.standbytogetherbackend.customer.dto;

import com.standbytogetherbackend.customer.entity.CustomerStatus;
import com.standbytogetherbackend.market.dto.MarketOutput;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CustomerOutput {

    private Long id;
    private String name;
    private String phone;
    private MarketOutput market;
    private CustomerStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
