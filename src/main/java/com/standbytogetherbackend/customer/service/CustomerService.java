package com.standbytogetherbackend.customer.service;

import com.standbytogetherbackend.customer.dto.CreateCustomerInput;
import com.standbytogetherbackend.customer.entity.Customer;
import com.standbytogetherbackend.customer.repository.CustomerRepository;
import com.standbytogetherbackend.market.entity.Market;
import com.standbytogetherbackend.market.repository.MarketRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final MarketRepository marketRepository;

    // 대기열 등록
    public Customer createCustomer(CreateCustomerInput createCustomerInput) {
        // 마켓 조회
        Market market = this.getMarketById(createCustomerInput.getMarketId());
        // 연락처 조회
        String phone = createCustomerInput.getPhone();
        // 기존 등록된 연락처 정보 있을시
        Boolean existsByPhone = this.customerRepository.existsByPhone(phone);
        if (existsByPhone) {
            throw new IllegalArgumentException("이미 등록된 연락처입니다.");
        }

        // 대기열 등록
        return this.customerRepository.save(createCustomerInput.toEntity(market));
    }

    // 대기 번호 조회
    public Map<String, Integer> getWaitingNumber(Long marketId, Long customerId) {
        // 마켓 조회
        Market market = getMarketById(marketId);
        // 마켓 내의 대기 고객 리스트 조회
        List<Customer> customers = market.getCustomers();

        // 고객 조회
        Customer customer = getCustomerById(customerId);

        // 고객의 대기 번호 조회 (index + 1)
        return Map.of("waiting", customers.indexOf(customer) + 1, "total",
            customers.size());
    }

    private Market getMarketById(Long marketId) {
        Optional<Market> marketById = this.marketRepository.findById(marketId);
        if (marketById.isEmpty()) {
            throw new IllegalArgumentException("해당 매장 정보가 존재하지 않습니다.");
        }
        return marketById.get();
    }

    public Customer getCustomerById(Long customerId) {
        Optional<Customer> customerById = this.customerRepository.findById(customerId);
        if (customerById.isEmpty()) {
            throw new IllegalArgumentException("해당 고객 정보가 존재하지 않습니다.");
        }
        return customerById.get();
    }

    public Customer deleteCustomer(Long customerId) {
        Customer customerById = this.getCustomerById(customerId);
        this.customerRepository.delete(customerById);
        return customerById;
    }

    public List<Customer> getCustomerList(Long marketId) {
        Market marketById = this.getMarketById(marketId);
        return marketById.getCustomers();
    }
}
