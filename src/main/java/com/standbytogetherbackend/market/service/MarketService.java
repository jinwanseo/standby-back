package com.standbytogetherbackend.market.service;

import com.standbytogetherbackend.market.dto.CreateMarketInput;
import com.standbytogetherbackend.market.entity.Market;
import com.standbytogetherbackend.market.repository.MarketRepository;
import com.standbytogetherbackend.member.dto.MemberDetails;
import com.standbytogetherbackend.member.entity.Member;
import com.standbytogetherbackend.member.repository.MemberRepository;
import java.util.List;
import java.util.Optional;
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


    public Market createMarket(CreateMarketInput createMarketInput) {
        MemberDetails memberDetails = (MemberDetails) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
        memberDetails.getId();
        Optional<Member> byId = this.memberRepository.findById(memberDetails.getId());
        if (byId.isEmpty()) {
            throw new IllegalArgumentException("회원 정보 재확인 요망");
        }
        if (byId.get().getMarket() != null) {
            throw new IllegalArgumentException("이미 마켓을 등록하였습니다. 기존 마켓을 삭제 후 시도해주세요.");
        }

        Market market = createMarketInput.toEntity();
        market.setMember(byId.get());
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
}
