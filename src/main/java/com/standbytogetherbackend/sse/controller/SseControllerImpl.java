package com.standbytogetherbackend.sse.controller;

import com.standbytogetherbackend.sse.service.SseService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SseControllerImpl implements SseController {

    private final SseService sseService;

    @Override
    @GetMapping(value = "/sse/{customerId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter connectionEvent(
        @PathVariable Long customerId) throws IOException {

        return this.sseService.connectionEvent(customerId);
    }

}
