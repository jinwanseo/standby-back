package com.standbytogetherbackend.member.dto;

import com.standbytogetherbackend.market.dto.MarketOutput;
import com.standbytogetherbackend.member.entity.MemberRole;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class MemberOutput {

    private Long id;
    private String username;
    private String phone;
    private MemberRole role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
