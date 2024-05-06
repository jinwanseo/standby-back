package com.standbytogetherbackend.customer.dto;

import com.standbytogetherbackend.customer.entity.Customer;
import com.standbytogetherbackend.market.entity.Market;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CreateCustomerInput {

    @NotBlank(message = "대기하실 분의 닉네임을 입력해주세요.")
    @Length(min = 2, max = 10, message = "닉네임은 최소 2자 이상 최대 20자 이하로 입력해주세요.")
    private String name;
    @NotBlank(message = "대기하실 분의 연락처를 입력해주세요.")
    @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", message = "연락처 형식이 올바르지 않습니다.")
    private String phone;
    @NotNull(message = "대기하실 매장 정보가 없습니다.")
    private Long marketId;

    public Customer toEntity(Market market) {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setPhone(phone);
        customer.setMarket(market);
        return customer;
    }
}
