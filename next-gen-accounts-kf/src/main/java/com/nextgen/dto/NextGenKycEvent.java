package com.nextgen.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class NextGenKycEvent {

    private String customerId;
    private String aadharNumber;
    private String panNumber;
    private String kycStatus;
}
