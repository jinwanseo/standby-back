package com.standbytogetherbackend.auth.controller;

import com.standbytogetherbackend.auth.dto.JoinInput;
import com.standbytogetherbackend.auth.service.AuthService;
import com.standbytogetherbackend.auth.validator.JoinValidator;
import com.standbytogetherbackend.common.error.CustomErrorMessage;
import com.standbytogetherbackend.member.entity.Member;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AuthController {

    private final JoinValidator joinValidator;
    private final AuthService authService;
    private final CustomErrorMessage errorMessage;

    @InitBinder("joinInput")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(joinValidator);
    }

    @PostMapping("/join")
    public ResponseEntity<?> joinProcess(@Valid @ModelAttribute JoinInput joinInput,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<Map<String, String>> errorMessages = this.errorMessage.getErrorMessages(
                bindingResult);
            return new ResponseEntity<>(Map.of("ok", false, "error", errorMessages),
                HttpStatus.BAD_REQUEST);
        }

        Member member = this.authService.joinProcess(joinInput);
        return new ResponseEntity<>(Map.of("ok", true, "result", member), HttpStatus.OK);
    }


}
