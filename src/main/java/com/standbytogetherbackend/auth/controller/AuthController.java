package com.standbytogetherbackend.auth.controller;

import com.standbytogetherbackend.auth.dto.JoinInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Tag(name = "인증", description = "회원가입, 로그인, 로그아웃")
public interface AuthController {


    @PostMapping("/join")
    @Operation(summary = "회원가입", description = "회원가입을 진행합니다.")
    ResponseEntity<?> joinProcess(@Valid @ModelAttribute JoinInput joinInput,
        BindingResult bindingResult);

}
