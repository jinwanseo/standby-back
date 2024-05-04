package com.standbytogetherbackend.member.service;

import com.standbytogetherbackend.member.dto.MemberDetails;
import com.standbytogetherbackend.member.entity.Member;
import com.standbytogetherbackend.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = this.memberRepository.findByUsername(username);
        if (member == null) {
            throw new UsernameNotFoundException("Username not found");
        }
        return new MemberDetails(member);
    }
}
