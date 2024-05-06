package com.standbytogetherbackend.market.entity;

import com.standbytogetherbackend.customer.entity.Customer;
import com.standbytogetherbackend.market.dto.MarketOutput;
import com.standbytogetherbackend.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderBy;
import jakarta.persistence.SequenceGenerator;
import java.time.LocalDateTime;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@Entity
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@SequenceGenerator(allocationSize = 5, name = "MARKET_SEQ_GEN", sequenceName = "MARKET_SEQ")
public class Market {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MARKET_SEQ_GEN")
    private Long id;

    private String name;
    private String address;
    private String phone;
    private String description;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "market")
    @OrderBy("id DESC")
    private List<Customer> customers;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Market(String name, String address, String phone, String description) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.description = description;
    }

    public MarketOutput toOutput() {
        MarketOutput output = new MarketOutput();
        output.setId(this.id);
        output.setName(this.name);
        output.setAddress(this.address);
        output.setPhone(this.phone);
        output.setDescription(this.description);
        output.setMember(this.member.toOutput());
        return output;
    }
}
