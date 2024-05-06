package com.standbytogetherbackend.market.dto;

import com.standbytogetherbackend.member.dto.MemberOutput;
import lombok.Data;

@Data
public class MarketOutput {

    private Long id;
    private String name;
    private String address;
    private String phone;
    private String description;

    private MemberOutput member;
}
