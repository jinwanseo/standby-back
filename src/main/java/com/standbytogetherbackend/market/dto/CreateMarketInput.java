package com.standbytogetherbackend.market.dto;

import com.standbytogetherbackend.market.entity.Market;
import com.standbytogetherbackend.member.entity.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CreateMarketInput {

    @NotBlank(message = "마켓 이름을 입력해주세요.")
    @Length(min = 2, max = 20, message = "마켓 이름은 최소 2자 이상 최대 20자 이하로 입력해주세요.")
    private String name;

    @NotBlank(message = "마켓 주소를 입력해주세요.")
    private String address;

    @NotBlank(message = "마켓 전화번호를 입력해주세요.")
    @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", message = "연락처 형식이 올바르지 않습니다.")
    private String phone;

    @NotBlank(message = "마켓 설명을 입력해주세요.")
    @Length(min = 10, max = 100, message = "마켓 설명은 최소 10자 이상 최대 100자 이하로 입력해주세요.")
    private String description;

    public Market toEntity() {
        return new Market(this.name, this.address, this.phone, this.description);
    }
}
