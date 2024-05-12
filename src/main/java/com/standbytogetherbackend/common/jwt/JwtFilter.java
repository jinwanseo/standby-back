package com.standbytogetherbackend.common.jwt;

import com.standbytogetherbackend.member.dto.MemberDetails;
import com.standbytogetherbackend.member.entity.Member;
import com.standbytogetherbackend.member.entity.MemberRole;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            log.info("###### No token");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.substring("Bearer ".length());
        if (jwtUtil.isExpired(token)) {
            log.info("###### Token expired");
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰에서 정보 추출 후 세션에 저장
        Long id = jwtUtil.getId(token);
        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

        Member member = new Member();
        member.setId(id);
        member.setUsername(username);
        member.setRole(MemberRole.valueOf(role));

        MemberDetails memberDetails = new MemberDetails(member);
        Authentication authToken = new UsernamePasswordAuthenticationToken(memberDetails, null,
            memberDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }
}
