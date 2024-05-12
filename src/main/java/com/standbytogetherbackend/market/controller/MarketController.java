package com.standbytogetherbackend.market.controller;

import com.standbytogetherbackend.market.dto.CreateMarketInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

@Tag(name = "스토어 관리", description = "매장 관리자 기능")
public interface MarketController {

    @Operation(summary = "등록", description = "스토어를 등록합니다.")
    ResponseEntity<?> createMarket(CreateMarketInput createMarketInput,
        BindingResult bindingResult);

    @Operation(summary = "상세 조회", description = "스토어 상세 정보를 조회합니다.")
    ResponseEntity<?> getMarketById(Long id);

    @Operation(summary = "목록 조회", description = "스토어 목록을 조회합니다.")
    ResponseEntity<?> getMarketList();

    @Operation(summary = "대기열 목록 조회", description = "대기 고객 리스트를 조회합니다.")
    ResponseEntity<?> getCustomerList(Long id);
}
