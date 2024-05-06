package com.standbytogetherbackend.customer.entity;

import com.standbytogetherbackend.customer.dto.CustomerOutput;
import com.standbytogetherbackend.market.entity.Market;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@Entity
@EqualsAndHashCode(of = "id")
@SequenceGenerator(allocationSize = 5, name = "CUSTOMER_SEQ_GEN", sequenceName = "CUSTOMER_SEQ")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "CUSTOMER_SEQ_GEN")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String phone;

    @ManyToOne
    @JoinColumn(name = "market_id")
    private Market market;

    @Enumerated(EnumType.STRING)
    private CustomerStatus status = CustomerStatus.WAITING;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public CustomerOutput toOutput() {
        CustomerOutput output = new CustomerOutput();
        output.setId(this.id);
        output.setName(this.name);
        output.setPhone(this.phone);
        output.setStatus(this.status);
        output.setMarket(this.market.toOutput());
        output.setCreatedAt(this.createdAt);
        output.setUpdatedAt(this.updatedAt);
        return output;
    }
}
