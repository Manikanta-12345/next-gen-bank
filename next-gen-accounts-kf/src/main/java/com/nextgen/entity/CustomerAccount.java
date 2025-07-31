package com.nextgen.entity;

import com.nextgen.constant.AccountType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "next_gen_customer_account")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CustomerAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id", nullable = false)
    private String customerId;

    @Column(name = "aadhar_number", nullable = false)
    private String aadharNumber;

    @Column(name = "pan_number", nullable = false)
    private String panNumber;

    @Column(name = "kyc_status", nullable = false)
    private String kycStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false)
    private AccountType accountType;

    @Column(name = "is_active", nullable = false)
    private String isActive;

    @CreationTimestamp
    @Column(name = "created_date", updatable = false, nullable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;
}
