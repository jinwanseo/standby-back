package com.standbytogetherbackend.sse.controller;


import io.swagger.v3.oas.annotations.Operation;
import java.io.IOException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface SseController {

    @Operation(summary = "SSE 연결 이벤트")
    SseEmitter connectionEvent(
        @PathVariable Long customerId) throws IOException;
}
