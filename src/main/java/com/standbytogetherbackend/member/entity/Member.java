package com.standbytogetherbackend.member.entity;

import com.standbytogetherbackend.market.entity.Market;
import com.standbytogetherbackend.member.dto.MemberOutput;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import java.time.LocalDateTime;
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
@SequenceGenerator(allocationSize = 5, name = "MEMBER_SEQ_GEN", sequenceName = "MEMBER_SEQ")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "MEMBER_SEQ_GEN")
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberRole role;

    @OneToOne(mappedBy = "member")
    private Market market;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Member(String username, String password, String phone, MemberRole role) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.role = role;
    }

    public MemberOutput toOutput() {
        MemberOutput output = new MemberOutput();
        output.setId(this.id);
        output.setUsername(this.username);
        output.setPhone(this.phone);
        output.setRole(this.role);
        output.setCreatedAt(this.createdAt);
        output.setUpdatedAt(this.updatedAt);
        return output;
    }
}
