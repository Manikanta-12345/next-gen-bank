package com.nextgen.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.nextgen.views.Views;
import lombok.*;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegistrationDto {
    @JsonView({Views.UI.class})
    private String id;
    @JsonView({Views.UI.class, Views.InternalApi.class})
    private Map<String, String> attributes;
    @JsonView({Views.UI.class, Views.InternalApi.class})
    private List<CredentialDTO> credentials;
    @JsonView({Views.UI.class, Views.InternalApi.class})
    private String username;
    @JsonView({Views.UI.class, Views.InternalApi.class})
    private String firstName;
    @JsonView({Views.UI.class, Views.InternalApi.class})
    private String lastName;
    @JsonView({Views.UI.class, Views.InternalApi.class})
    private String email;
    @JsonView({Views.UI.class, Views.InternalApi.class})
    private boolean emailVerified;
    @JsonView({Views.UI.class, Views.InternalApi.class})
    private boolean enabled;
    @JsonView({Views.UI.class})
    private KycStatus kycStatus;
}
