package com.standbytogetherbackend.customer.controller;

import com.standbytogetherbackend.customer.dto.CreateCustomerInput;
import com.standbytogetherbackend.customer.dto.DeleteCustomerInput;
import com.standbytogetherbackend.customer.dto.GetCustomerWaitingNumberInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

@Tag(name = "고객 관리", description = "대기 등록 고객 관리")
public interface CustomerController {

    @Operation(summary = "대기열 등록", description = "대기열에 추가합니다")
    ResponseEntity<?> createCustomer(CreateCustomerInput createCustomerInput);

    @Operation(summary = "대기 정보 조회", description = "대기 상태를 조회 합니다.")
    ResponseEntity<?> getCustomerById(Long customerId);

    @Operation(summary = "대기열 삭제", description = "대기열에서 삭제 합니다.")
    ResponseEntity<?> deleteCustomer(DeleteCustomerInput deleteCustomerInput) throws IOException;

    @Operation(summary = "대기 리스트 조회", description = "매장 대기열 리스트를 조회 합니다.")
    ResponseEntity<?> getCustomerList(Long storeId);

    @Operation(summary = "줄서기 순서 조회", description = "자신의 순서를 조회 합니다.")
    ResponseEntity<?> getWaitingNumber(GetCustomerWaitingNumberInput getCustomerWaitingNumberInput);


    @Operation(summary = "미루기", description = "내 순서를 한단계 뒤로 미룹니다.")
    ResponseEntity<?> postponeWaiting(Long customerId);
}
