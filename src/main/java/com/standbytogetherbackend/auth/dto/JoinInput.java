package com.standbytogetherbackend.auth.dto;

import com.standbytogetherbackend.member.entity.Member;

import com.standbytogetherbackend.member.entity.MemberRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class JoinInput {

    @NotBlank(message = "아이디를 입력해주세요.")
    @Length(min = 3, max = 20, message = "아이디는 3자 이상 20자 이하로 입력해주세요.")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Length(min = 4, max = 20, message = "비밀번호는 4자 이상 20자 이하로 입력해주세요.")
    private String password;

    @NotBlank(message = "연락처를 입력해주세요.")
    @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", message = "연락처 형식이 올바르지 않습니다.")
    private String phone;

    @NotBlank(message = "권한 정보를 입력해주세요.")
    @Pattern(regexp = "^(ROLE_USER|ROLE_ADMIN)$", message = "ROLE_USER 또는 ROLE_ADMIN 중 하나를 입력해주세요.")
    private String role;

    public Member toEntity() {
        return new Member(this.username, this.password, this.phone, MemberRole.valueOf(this.role));
    }
}
