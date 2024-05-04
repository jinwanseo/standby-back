package com.standbytogetherbackend.auth.service;

import com.standbytogetherbackend.auth.dto.JoinInput;
import com.standbytogetherbackend.member.entity.Member;
import com.standbytogetherbackend.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    public Member joinProcess(JoinInput joinInput) {

        joinInput.setPassword(this.passwordEncoder.encode(joinInput.getPassword()));
        return this.memberRepository.save(joinInput.toEntity());
    }

}
