package com.standbytogetherbackend.market.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.standbytogetherbackend.customer.entity.Customer;
import com.standbytogetherbackend.customer.entity.CustomerStatus;
import com.standbytogetherbackend.customer.repository.CustomerRepository;
import com.standbytogetherbackend.market.dto.CallCustomerInput;
import com.standbytogetherbackend.market.dto.CreateMarketInput;
import com.standbytogetherbackend.market.entity.Market;
import com.standbytogetherbackend.market.repository.MarketRepository;
import com.standbytogetherbackend.member.dto.MemberDetails;
import com.standbytogetherbackend.member.entity.Member;
import com.standbytogetherbackend.member.repository.MemberRepository;
import com.standbytogetherbackend.sse.service.SseService;
import java.io.IOException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MarketService {

    private final MarketRepository marketRepository;
    private final MemberRepository memberRepository;
    private final CustomerRepository customerRepository;
    private final SseService sseService;
    private final ObjectMapper objectMapper;


    public Market createMarket(CreateMarketInput createMarketInput) {
        MemberDetails memberDetails = (MemberDetails) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();

        Long memberId = memberDetails.getId();
        Optional<Member> byId = this.memberRepository.findById(memberId);
        if (byId.isEmpty()) {
            throw new IllegalArgumentException("회원 정보 재확인 요망");
        }

        Member member = byId.get();
        if (member.getMarket() != null) {
            throw new IllegalArgumentException("이미 마켓을 등록하였습니다. 기존 마켓을 삭제 후 시도해주세요.");
        }

        Market market = createMarketInput.toEntity();
        market.setMember(member);
        return this.marketRepository.save(market);
    }

    public Market findMarketById(Long id) {
        Optional<Market> market = this.marketRepository.findById(id);
        if (market.isEmpty()) {
            throw new IllegalArgumentException("해당 ID의 마켓이 존재하지 않습니다.");
        }
        return market.get();
    }

    public List<Market> findMarketList() {
        return this.marketRepository.findAll();
    }


    public void callCustomer(CallCustomerInput callCustomerInput) throws IOException {
        // 로그인한 사용자 정보
        MemberDetails memberInfo = (MemberDetails) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
        // 마켓 정보
        Optional<Market> byMarketId = this.marketRepository.findById(
            callCustomerInput.getMarketId());

        // 마켓 정보가 없을 경우
        if (byMarketId.isEmpty()) {
            throw new IllegalArgumentException("해당 ID의 마켓이 존재하지 않습니다.");
        }

        // 마켓 소유자 여부 확인
        Market market = byMarketId.get();
        if (market.getMember().getId() != memberInfo.getId()) {
            throw new IllegalArgumentException("해당 마켓의 소유자가 아닙니다.");
        }

        // 마켓에 속한 고객 여부 확인
        Optional<Customer> byCustomerId = this.customerRepository.findById(
            callCustomerInput.getCustomerId());

        // 고객 정보가 없을 경우
        if (byCustomerId.isEmpty()) {
            throw new IllegalArgumentException("호출 대상이 되는 고객이 존재하지 않습니다.");
        }

        Customer customer = byCustomerId.get();
        // 마켓에 속한 고객인지 확인
        if (customer.getMarket().getId() != market.getId()) {
            throw new IllegalArgumentException("해당 마켓에 속한 고객이 아닙니다.");
        }

        // 고객 호출
        customer.setStatus(CustomerStatus.CALLED);
        // 영속성 반영
        market.getCustomers().stream().filter(c -> c == customer)
            .findFirst().ifPresentOrElse(c -> c.setStatus(CustomerStatus.CALLED),
                () -> {
                    throw new IllegalArgumentException("해당 마켓에 속한 고객이 아닙니다.");
                });

        // DB 반영
        this.customerRepository.save(customer);

        // SSE 이벤트 발생 (고객 호출용)
        this.sseService.sendEvent("client", "CAlled~!@~!@", customer.getId(),
            customer.getMarket().getId());

        List<Map<String, Object>> cusList = new ArrayList<>();
        for (Customer cus : customer.getMarket().getCustomers()) {
            cusList.add(
                Map.of("id", cus.getId(), "createdAt",
                    cus.getCreatedAt().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
            );
        }
        String result = this.objectMapper.writeValueAsString(cusList);

        // SSE 이벤트 발생 (마켓 호출용)
        this.sseService.sendEvent("store", result, customer.getId(),
            customer.getMarket().getId());
    }
}
