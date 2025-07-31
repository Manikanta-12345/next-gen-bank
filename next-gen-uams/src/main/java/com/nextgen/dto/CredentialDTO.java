package com.nextgen.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CredentialDTO {
    private boolean temporary;
    private String type;
    private String value;



}
