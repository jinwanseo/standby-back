package com.standbytogetherbackend.auth.validator;

import com.standbytogetherbackend.auth.dto.JoinInput;
import com.standbytogetherbackend.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class JoinValidator implements Validator {

    private final MemberRepository memberRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(JoinInput.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        JoinInput joinInput = (JoinInput) target;
        BindingResult bindingResult = (BindingResult) errors;

        Boolean existsByUsername = this.memberRepository.existsByUsername(joinInput.getUsername());
        if (existsByUsername) {
            bindingResult.rejectValue("username", "invalid.username", "이미 사용중인 아이디입니다.");
        }

    }
}
