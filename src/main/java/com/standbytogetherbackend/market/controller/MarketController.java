package com.standbytogetherbackend.market.controller;

import com.standbytogetherbackend.member.dto.MemberDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/market")
public class MarketController {

    @GetMapping
    public String market() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MemberDetails memberDetails = (MemberDetails) authentication.getPrincipal();

        log.info("###### {} {} {} ",
            memberDetails.getId(),
            memberDetails.getUsername(),
            memberDetails.getAuthorities().iterator().next().getAuthority());
        return "dd";
    }
}
