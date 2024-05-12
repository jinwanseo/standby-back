package com.standbytogetherbackend.sse.service;

import com.standbytogetherbackend.customer.entity.Customer;
import com.standbytogetherbackend.customer.repository.CustomerRepository;
import java.io.IOException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@Service
@RequiredArgsConstructor
public class SSEService {

    @Value("${spring.sse.expire}")
    private Long DEFAULT_TIMEOUT;

    private final CustomerRepository customerRepository;

    // TODO : Emitter State.. 향후 Redis 로 교체 예정
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    // Emitter Event 연결
    public SseEmitter connectionEvent(Long customerId) throws IOException {
        // 고객 정보 조회
        Optional<Customer> byCustomerId = this.customerRepository.findById(customerId);
        if (byCustomerId.isEmpty()) { // 고객 정보가 없을 경우
            throw new IllegalArgumentException("고객 정보가 없습니다.");
        }
        Customer customer = byCustomerId.get();

        // Emitter 구분자 생성
        String emitterId =
            UUID.randomUUID() + "_" + customer.getId() + "_" + customer.getMarket().getId();

        // 구분자를 유저 아이디로 함
        SseEmitter sseEmitter = emitters.computeIfAbsent(emitterId,
            k -> new SseEmitter(DEFAULT_TIMEOUT));

        // 완료시 Emitter 삭제
        sseEmitter.onCompletion(() -> {
            emitters.remove(emitterId);
        });
        // Timeout 시 Emitter 삭제
        sseEmitter.onTimeout(() -> {
            emitters.remove(emitterId);
        });
        // 에러시 삭제
        sseEmitter.onError((e) -> {
            emitters.remove(emitterId);
        });

        this.sendEvent("called", "연결되었습니다.", customer.getId(), customer.getMarket().getId());

        return sseEmitter;
    }

    // Emitter Event 전송
    public void sendEvent(String eventName, Object data, Long customerId, Long marketId)
        throws IOException {

        List<SseEmitter> emitList = new ArrayList<>();
        for (String key : emitters.keySet()) {
            // [UUID, customerId, marketId]
            String[] customKeyList = key.split("_");
            String customerKey = customKeyList[1];
            String marketKey = customKeyList[2];

            // 클라이언트 이벤트인 경우
            if (eventName.equals("customer")) {
                // 유저 아이디와 마켓 아이디가 일치하는 경우 클라이언트 이벤트 목록에 추가
                if (customerId.equals(Long.parseLong(customerKey)) &&
                    marketId.equals(Long.parseLong(marketKey))) {
                    emitList.add(emitters.get(key));
                }
            }
            // 마켓 이벤트 인 경우
            else if (eventName.equals("market")) {
                // 마켓 아이디가 일치하는 경우 마켓 이벤트 목록에 추가
                if (marketId.equals(Long.parseLong(marketKey))) {
                    emitList.add(emitters.get(key));
                }
            }
        }

        // 이벤트 발생 알림 정보 전송!
        for (SseEmitter sseEmitter : emitList) {
            sseEmitter.send(SseEmitter
                .event()
                .name(eventName)
                .data(data)
            );
        }
    }


    public void removeEmitter(Long customerId, Long marketId) {
        for (String key : emitters.keySet()) {
            String[] customKeyList = key.split("_");
            String customerIdStr = customKeyList[1];
            String marketIdStr = customKeyList[2];

            if (customerId.equals(Long.parseLong(customerIdStr)) &&
                marketId.equals(Long.parseLong(marketIdStr))) {
                emitters.remove(key);
            }
        }
    }
    
}
