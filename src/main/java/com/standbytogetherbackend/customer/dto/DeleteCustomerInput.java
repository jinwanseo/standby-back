package com.standbytogetherbackend.customer.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DeleteCustomerInput {

    @NotBlank(message = "삭제할 고객 ID는 필수 입력 값입니다.")
    private Long customerId;
}
